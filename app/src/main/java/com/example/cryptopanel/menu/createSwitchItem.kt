package com.example.cryptopanel.menu

import android.content.SharedPreferences
import android.view.Menu
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.example.cryptopanel.R

fun createSwitchItem(menu: Menu, preferences: SharedPreferences) {
    val switchItem = menu.findItem(R.id.theme_switch)
    val switch = switchItem.actionView as SwitchCompat
    switch.setText(R.string.switch_text)
    val mode = preferences.getInt("mode", 0)

    if (mode == 0) {
        setLightMode()
    } else {
        setNightMode()
        switch.isChecked = true
    }

    switch.setOnCheckedChangeListener { _, isChecked ->
        if (isChecked) {
            setNightMode()
            preferences.edit().putInt("mode", 1).apply()
        } else {
            setLightMode()
            preferences.edit().putInt("mode", 0).apply()
        }
    }
}

private fun setNightMode() {
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
}

private fun setLightMode() {
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
}