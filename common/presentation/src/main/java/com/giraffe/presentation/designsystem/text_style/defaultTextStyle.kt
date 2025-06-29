package com.giraffe.presentation.designsystem.text_style

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.giraffe.presentation.R

val manropeFontFamily = FontFamily(
    Font(R.font.manrope, FontWeight.Normal),
    Font(R.font.manrope_medium, FontWeight.Medium),
    Font(R.font.manrope_semibold, FontWeight.SemiBold),
    Font(R.font.manrope_bold, FontWeight.Bold)
)

val defaultTextStyle = CineVerseTextStyle(
    display = TextStyle(
        fontFamily = manropeFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp
    ),
    title = TitleTextStyle(
        xl = TextStyle(
            fontFamily = manropeFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        ),
        lg = TextStyle(
            fontFamily = manropeFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        ),
        md = TextStyle(
            fontFamily = manropeFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        ),
        sm = TextStyle(
            fontFamily = manropeFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
    ),
    body = SizedTextStyle(
        large = WeightedTextStyle(
            regular = TextStyle(fontFamily = manropeFontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp),
            medium = TextStyle(fontFamily = manropeFontFamily, fontWeight = FontWeight.Medium, fontSize = 16.sp),
            semiBold = TextStyle(fontFamily = manropeFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        ),
        medium = WeightedTextStyle(
            regular = TextStyle(fontFamily = manropeFontFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp),
            medium = TextStyle(fontFamily = manropeFontFamily, fontWeight = FontWeight.Medium, fontSize = 14.sp),
            semiBold = TextStyle(fontFamily = manropeFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        ),
        small = WeightedTextStyle(
            regular = TextStyle(fontFamily = manropeFontFamily, fontWeight = FontWeight.Normal, fontSize = 12.sp),
            medium = TextStyle(fontFamily = manropeFontFamily, fontWeight = FontWeight.Medium, fontSize = 12.sp),
            semiBold = TextStyle(fontFamily = manropeFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
        )
    ),
    label = WeightedTextStyle(
        regular = TextStyle(fontFamily = manropeFontFamily, fontWeight = FontWeight.Normal, fontSize = 12.sp),
        medium = TextStyle(fontFamily = manropeFontFamily, fontWeight = FontWeight.Medium, fontSize = 12.sp),
        semiBold = TextStyle(fontFamily = manropeFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
    )
)