package com.giraffe.designsystem.composable

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giraffe.designsystem.composable.custom.Icon
import com.giraffe.designsystem.composable.custom.Text
import com.giraffe.designsystem.theme.CineVerseTheme
import com.giraffe.designsystem.theme.Theme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun Rating(
    value: Float,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.full))
            .background(Theme.color.background.card)
            .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = value.toLocalizedRating(),
            style = Theme.textStyle.label.md.medium,
            color = Theme.color.shade.primary
        )
        Icon(
            painter = painterResource(Theme.icons.dueTone.star),
            contentDescription = null,
            tint = Theme.color.additional.primary.yellow,
            modifier = Modifier.size(16.dp)
        )
    }
}

private fun String.toArabicDigits(): String {
    val arabicDigits = listOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
    return this.map { char ->
        if (char.isDigit()) arabicDigits[char.digitToInt()]
        else if (char == '٫') '.'
        else char
    }.joinToString("")
}

fun Float.toLocalizedRating(): String {
    val locale = AppCompatDelegate.getApplicationLocales().get(0) ?: Locale.getDefault()
    val numberFormat = NumberFormat.getNumberInstance(locale).apply {
        maximumFractionDigits = 1
        minimumFractionDigits = 1
    }
    val formatted = numberFormat.format(this)
    return if (locale.language == "ar") formatted.toArabicDigits() else formatted
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CineVerseTheme(isDarkTheme = true) {
        Rating(
            value = 7.5f,
        )
    }
}