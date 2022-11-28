package com.example.cryptopanel.menu

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.view.Menu
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.example.cryptopanel.MODE
import com.example.cryptopanel.R

fun createSwitchItem(menu: Menu, preferences: SharedPreferences) {
    val switchItem = menu.findItem(R.id.theme_switch)
    val switch = switchItem.actionView as SwitchCompat
    switch.setText(R.string.switch_text)
    val mode = preferences.getInt(MODE, MODE_PRIVATE)

    if (mode == 0) {
        setLightMode()
    } else {
        setNightMode()
        switch.isChecked = true
    }

    switch.setOnCheckedChangeListener { _, isChecked ->
        if (isChecked) {
            setNightMode()
            preferences.edit().putInt(MODE, 1).apply()
        } else {
            setLightMode()
            preferences.edit().putInt(MODE, 0).apply()
        }
    }
}

private fun setNightMode() {
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
}

private fun setLightMode() {
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
}