package com.giraffe.designsystem.text_style

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.giraffe.designsystem.R

internal val manropeFontFamily = FontFamily(
    Font(R.font.manrope, FontWeight.Normal),
    Font(R.font.manrope_medium, FontWeight.Medium),
    Font(R.font.manrope_semibold, FontWeight.SemiBold),
    Font(R.font.manrope_bold, FontWeight.Bold)
)

val defaultTextStyle = CineVerseTextStyle(
    display = Display(
        xl = TextStyle(
            fontFamily = manropeFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
        )
    ),
    title = Title(
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
    body = Body(
        lg = Weight(
            regular = TextStyle(
                fontFamily = manropeFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            medium = TextStyle(
                fontFamily = manropeFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            ),
            semiBold = TextStyle(
                fontFamily = manropeFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        ),
        md = Weight(
            regular = TextStyle(
                fontFamily = manropeFontFamily,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp,
                fontSize = 14.sp
            ),
            medium = TextStyle(
                fontFamily = manropeFontFamily,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp,
                fontSize = 14.sp
            ),
            semiBold = TextStyle(
                fontFamily = manropeFontFamily,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.sp,
                fontSize = 14.sp
            )
        ),
        sm = Weight(
            regular = TextStyle(
                fontFamily = manropeFontFamily,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp,
                fontSize = 12.sp
            ),
            medium = TextStyle(
                fontFamily = manropeFontFamily,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp,
                fontSize = 12.sp
            ),
            semiBold = TextStyle(
                fontFamily = manropeFontFamily,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.sp,
                fontSize = 12.sp
            )
        )
    ),
    label = Label(
        md = Weight(
            regular = TextStyle(
                fontFamily = manropeFontFamily,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp,
                fontSize = 12.sp
            ),
            medium = TextStyle(
                fontFamily = manropeFontFamily,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.sp,
                fontSize = 12.sp
            ),
            semiBold = TextStyle(
                fontFamily = manropeFontFamily,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.sp,
                fontSize = 12.sp
            )
        )
    )
)