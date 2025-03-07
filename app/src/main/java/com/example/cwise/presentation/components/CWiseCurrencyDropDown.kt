package com.example.cwise.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cwise.R
import com.example.cwise.ui.theme.CWiseT
import com.example.cwise.ui.theme.CWiseTheme
import com.example.cwise.ui.theme.DropDownIcon

@Composable
fun CWiseCurrencyDropDown(
    currencyCode: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .border(
                width = 1.dp,
                color = CWiseT,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(vertical = 12.dp)
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.Center
    ) {
        CircularEmoji(emoji = currencyCode, size = 20.dp, textSize = 23.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = currencyCode,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = DropDownIcon,
            modifier = Modifier
                .size(20.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            contentDescription = stringResource(R.string.menu_icon),
        )
    }
}



@Preview
@Composable
fun CWiseCurrencyDropDownPreview(modifier: Modifier = Modifier) {
    CWiseTheme {
        CWiseCurrencyDropDown(
            currencyCode = "EUR",
            onClick = {

            }
        )
    }
}