package com.giraffe.profile.utils

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

object LanguageHelper {
    fun updateAppLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val localeList = LocaleListCompat.forLanguageTags(languageCode)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            AppCompatDelegate.setApplicationLocales(localeList)
        } else {
            @Suppress("DEPRECATION")
            AppCompatDelegate.setApplicationLocales(localeList)
        }
    }
}