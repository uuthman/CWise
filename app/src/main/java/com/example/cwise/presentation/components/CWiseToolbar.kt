package com.example.cwise.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cwise.R
import com.example.cwise.ui.theme.CWiseTheme
import com.example.cwise.ui.theme.MenuIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CWiseToolbar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),

    ) {

    TopAppBar(
        title = {
            Icon(
                imageVector = MenuIcon,
                modifier = Modifier
                    .size(24.dp),
                contentDescription = stringResource(R.string.menu_icon),
            )
        },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        actions = {
            Text(
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.sign_up),
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(end = 16.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CWiseToolbarPreview() {
    CWiseTheme {
        CWiseToolbar()
    }
}