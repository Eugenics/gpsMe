package com.eugenics.gpslive.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.eugenics.gpslive.R

val blenderProFontFamily = FontFamily(
    Font(R.font.blenderpro_book, FontWeight.Normal),
    Font(R.font.blenderpro_bold, FontWeight.Bold)
)

val Typography = Typography(
    labelLarge = TextStyle(
        fontFamily = blenderProFontFamily,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.10000000149011612.sp,
        lineHeight = 20.sp,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = blenderProFontFamily,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.5.sp,
        lineHeight = 16.sp,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = blenderProFontFamily,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.5.sp,
        lineHeight = 16.sp,
        fontSize = 11.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = blenderProFontFamily,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.5.sp,
        lineHeight = 24.sp,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = blenderProFontFamily,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.25.sp,
        lineHeight = 20.sp,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = blenderProFontFamily,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.4000000059604645.sp,
        lineHeight = 16.sp,
        fontSize = 12.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = blenderProFontFamily,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 40.sp,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = blenderProFontFamily,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 36.sp,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = blenderProFontFamily,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 32.sp,
        fontSize = 24.sp
    ),
    displayLarge = TextStyle(
        fontFamily = blenderProFontFamily,
        fontWeight = FontWeight.W400,
        letterSpacing = (-0.25).sp,
        lineHeight = 64.sp,
        fontSize = 57.sp
    ),
    displayMedium = TextStyle(
        fontFamily = blenderProFontFamily,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 52.sp,
        fontSize = 45.sp
    ),
    displaySmall = TextStyle(
        fontFamily = blenderProFontFamily,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 44.sp,
        fontSize = 36.sp
    ),
    titleLarge = TextStyle(
        fontFamily = blenderProFontFamily,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.sp,
        lineHeight = 28.sp,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = blenderProFontFamily,
        fontWeight = FontWeight.W500,
        letterSpacing = 0.15.sp,
        lineHeight = 24.sp,
        fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = blenderProFontFamily,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.10.sp,
        lineHeight = 20.sp,
        fontSize = 14.sp
    ),
)