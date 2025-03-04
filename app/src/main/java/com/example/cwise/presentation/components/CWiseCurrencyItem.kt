package com.example.cwise.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cwise.presentation.model.CurrencyUi
import com.example.cwise.presentation.utils.CurrencyUtil
import com.example.cwise.ui.theme.CWiseTheme


@Composable
fun CWiseCurrencyItem(
    currencyUi: CurrencyUi,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(bottom = 10.dp)
            .clickable {
                onClick.invoke()
            }


    ) {
        CircularEmoji(
            emoji = currencyUi.currencyCode,

        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = currencyUi.currencyCode,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.tertiary,
                )

            Text(
                text = currencyUi.currencyName,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
    }
}

@Composable
fun CircularEmoji(
    emoji: String,
    size: Dp = 48.dp,
    textSize: TextUnit = 36.sp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.White)
    ) {
        Text(
            text = CurrencyUtil.getFlagEmoji(emoji),
            fontSize = textSize,
            textAlign = TextAlign.Center
        )
    }
}


@Preview
@Composable
fun CWiseCurrencyItemPreview(modifier: Modifier = Modifier) {
    CWiseTheme {
        CWiseCurrencyItem(
            currencyUi = CurrencyUi(
                currencyCode = "NGN",
                currencyName = "Nigerian Naira",
            ),
            onClick = {

            }
        )
    }
}