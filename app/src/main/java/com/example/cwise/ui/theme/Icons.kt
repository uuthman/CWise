package com.example.cwise.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.cwise.R

val MenuIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.menu)

val ConversionRateIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.left_right)

val DropDownIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.arrow_down)


val CloseIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.close)