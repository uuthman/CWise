package com.example.cwise.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cwise.R
import com.example.cwise.presentation.model.CurrencyUi
import com.example.cwise.ui.theme.CWiseTheme
import com.example.cwise.ui.theme.CloseIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CWiseCurrencyBottomSheet(
    onDismiss: () -> Unit,
    currencies: List<CurrencyUi>,
    onCurrencyClick: (String) -> Unit
) {

    val state = rememberModalBottomSheetState(
       skipPartiallyExpanded = true
    )

    var searchFieldState by remember {
        mutableStateOf(TextFieldState(""))
    }

    val filteredCurrencies = remember(searchFieldState.text,currencies) {
        if (searchFieldState.text.isEmpty()) {
            currencies
        } else {
            currencies.filter { currency ->
                currency.currencyCode.contains(searchFieldState.text, ignoreCase = true) ||
                        currency.currencyName.contains(searchFieldState.text, ignoreCase = true)
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = state,
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        contentColor = MaterialTheme.colorScheme.surface ,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = BottomSheetDefaults.HiddenShape,
        dragHandle = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
                    .background(MaterialTheme.colorScheme.surface),
                horizontalAlignment = Alignment.Start
            ) {
                Icon(
                    imageVector = CloseIcon,
                    modifier = Modifier
                        .size(24.dp),
                    contentDescription = stringResource(R.string.menu_icon),
                )
                Text(
                    fontWeight = FontWeight.Bold,
                    text = "Select Currency",
                    fontSize = 26.sp,
                    color = MaterialTheme.colorScheme.tertiary,
                )
                Spacer(modifier = Modifier.height(10.dp))
                CWiseSearchInputField(
                    state = searchFieldState,
                    hint = "Search for a currency / country"
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxSize(),

            ) {
            items(
                items = filteredCurrencies
            ) {
                CWiseCurrencyItem(
                    currencyUi = it,
                    onClick = {
                        onCurrencyClick.invoke(it.currencyCode)
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun CWiseCurrencyBottomSheetPreview(modifier: Modifier = Modifier) {
    CWiseTheme {
        CWiseCurrencyBottomSheet(
            onDismiss = {

            },
            currencies = listOf(
                CurrencyUi(
                    "NGN",
                    "Nigerian Naira"
                ),
                CurrencyUi(
                    "NGN",
                    "Nigerian Naira"
                ),
                CurrencyUi(
                    "NGN",
                    "Nigerian Naira"
                )
            ),
            onCurrencyClick = {

            }
        )
    }
}