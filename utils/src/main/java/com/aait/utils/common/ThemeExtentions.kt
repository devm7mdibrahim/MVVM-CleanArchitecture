package com.aait.utils.common

import androidx.appcompat.app.AppCompatDelegate

object ThemeHelper {
    fun applyTheme(theme: ThemeMode) {
        when (theme) {
            ThemeMode.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            ThemeMode.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            ThemeMode.BATTERY_SAVER -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            ThemeMode.DEFAULT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    enum class ThemeMode { LIGHT, DARK, BATTERY_SAVER, DEFAULT }
}