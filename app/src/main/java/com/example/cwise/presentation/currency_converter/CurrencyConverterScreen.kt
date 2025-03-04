package com.example.cwise.presentation.currency_converter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cwise.R
import com.example.cwise.core.utils.Constants
import com.example.cwise.presentation.components.CWiseCurrencyBottomSheet
import com.example.cwise.presentation.components.CWiseCurrencyDropDown
import com.example.cwise.presentation.components.CWiseCurrencyInputField
import com.example.cwise.presentation.components.CWiseScaffold
import com.example.cwise.presentation.components.CWiseToolbar
import com.example.cwise.ui.theme.CWiseTheme
import com.example.cwise.ui.theme.ConversionRateIcon
import com.example.cwise.ui.theme.SanMono

@Composable
fun CurrencyConverterScreenRoot(
    viewModel: CurrencyConverterViewModel = hiltViewModel()
){
    CurrencyConverterScreen(
      state = viewModel.state,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterScreen(
    state: CurrencyConverterState,
    onAction: (CurrencyConversionAction) -> Unit
) {

    var showBottomSheet by remember { mutableStateOf(false) }

    CWiseScaffold(
        topAppBar = {
            CWiseToolbar()
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = SanMono,
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    append(stringResource(id = R.string.currency_calculator))
                    pushStringAnnotation(
                        tag = "clickable_text",
                        annotation = stringResource(id = R.string.dot)
                    )
                    withStyle(
                        style = SpanStyle(
                            fontSize = 45.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = SanMono
                        )
                    ) {
                        append(stringResource(id = R.string.dot))
                    }
                }
            }
            Spacer(modifier = Modifier.height(120.dp))
            ClickableText(
                text = annotatedString,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                onClick = { offset ->
                    annotatedString.getStringAnnotations(
                        tag = "clickable_text",
                        start = offset,
                        end = offset
                    )
                }
            )
            Spacer(modifier = Modifier.height(38.dp))
            CWiseCurrencyInputField(
                state = state.baseTextField,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                currency = Constants.BASE_CURRENCY
            )
            Spacer(modifier = Modifier.height(12.dp))
            CWiseCurrencyInputField(
                state = TextFieldState(state.convertedAmount.toString()),
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                currency = state.selectedTargetCurrency,
                enabled = false
            )

            Spacer(modifier = Modifier.height(28.dp))

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                CWiseCurrencyDropDown(
                    currencyCode = Constants.BASE_CURRENCY,
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = ConversionRateIcon,
                    modifier = Modifier
                        .size(28.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = stringResource(R.string.menu_icon),
                )
                Spacer(modifier = Modifier.width(12.dp))
                CWiseCurrencyDropDown(
                    currencyCode = state.selectedTargetCurrency,
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                        showBottomSheet = true
                    }
                )
            }
        }

        if(showBottomSheet) {
            CWiseCurrencyBottomSheet(
                onDismiss = {
                    showBottomSheet = false
                },
                currencies = state.currencies,
                onCurrencyClick = {
                    onAction(CurrencyConversionAction.OnCurrencyClick(it))
                    showBottomSheet = false
                }
            )
        }
    }
}


@Preview
@Composable
fun CurrencyConverterScreenPreview() {
    CWiseTheme {
        CurrencyConverterScreen(
            state = CurrencyConverterState(),
            onAction = {

            }
        )
    }
}