package com.example.cwise.presentation.currency_converter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cwise.core.utils.ConnectivityManagerNetworkMonitor
import com.example.cwise.core.utils.Constants
import com.example.cwise.core.utils.textAsFlow
import com.example.cwise.domain.repository.CurrencyRepository
import com.example.cwise.domain.repository.RateRepository
import com.example.cwise.presentation.mapper.toCurrencyUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@Suppress("OPT_IN_USAGE")
@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val rateRepository: RateRepository,
    private val networkMonitor: ConnectivityManagerNetworkMonitor

): ViewModel() {

    var state by mutableStateOf(CurrencyConverterState())
        private set

    private val isOnline =
        networkMonitor.isOnline.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = true,
        )


    init {
        observeNetworkConnectivity()

        state.baseTextField.textAsFlow().debounce(300L).onEach {
            val amount = it.toString()
            if(amount.isEmpty()) {
                state = state.copy(convertedAmount = 0.0)
            } else {
                convertCurrency(state.selectedTargetCurrency,it.toString().toBigDecimal())
            }

        }.launchIn(viewModelScope)

       currencyRepository.getCurrencies().onEach {
           if (it != null) {
               state = state.copy(currencies = it.toCurrencyUi())
           }
       }.launchIn(viewModelScope)

       rateRepository.getRates().onEach {
           state = state.copy(exchangeRates =  it?.rates ?: emptyMap())
       }.launchIn(viewModelScope)


        viewModelScope.launch {
            fetchData()
        }


    }


    private fun observeNetworkConnectivity() {
        viewModelScope.launch {
            isOnline.collect {
              fetchData()
            }
        }
    }

    suspend fun fetchData() {
        rateRepository.fetchRates(Constants.BASE_CURRENCY).also {
            convertCurrency(state.selectedTargetCurrency,state.baseTextField.text.toString().toBigDecimal())
        }
        currencyRepository.fetchCurrencies()
    }


    fun onAction(action: CurrencyConversionAction) {
        when(action) {
            is CurrencyConversionAction.OnCurrencyClick -> {
                state = state.copy(selectedTargetCurrency = action.currencyCode)
                convertCurrency(action.currencyCode,state.baseTextField.text.toString().toBigDecimal())
            }

        }
    }


    private fun convertCurrency(
        targetCurrency: String,
        sourceAmount: BigDecimal,
    ) {
        if(!isOnline.value) return

        if(sourceAmount <= BigDecimal.ZERO) {
            state = state.copy(convertedAmount = 0.0)
            return
        }
        val rate = state.exchangeRates.getOrDefault(Constants.BASE_CURRENCY, 1.0)

        val equivalentConversion = sourceAmount.toDouble() / rate

        val targetRate = state.exchangeRates.getOrDefault(targetCurrency, 1.0)
        val convertedAmount = equivalentConversion * targetRate

        val roundedAmount = BigDecimal(convertedAmount).setScale(2, RoundingMode.HALF_UP)
        state = state.copy(convertedAmount = roundedAmount.toDouble())


    }
}
