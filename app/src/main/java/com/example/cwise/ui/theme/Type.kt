package com.example.cwise.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.cwise.R

val SanMono = FontFamily(
    Font(
        resId = R.font.sans_mono_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.sans_mono_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.sans_mono_semibold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.sans_mono_bold,
        weight = FontWeight.Bold
    ),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = SanMono,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        color = CWiseGrey
    ),
    bodyMedium = TextStyle(
        fontFamily = SanMono,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = SanMono,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = SanMono,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = SanMono,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        color = CWiseWhite
    ),
)