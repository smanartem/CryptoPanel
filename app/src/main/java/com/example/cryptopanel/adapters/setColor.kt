package com.example.cryptopanel.adapters

import android.graphics.Color

//почему это в папке с адаптерами если используется в фрагментах тоже?
fun setColor(x: Double): Int {
    return if (x > 0) {
        Color.GREEN
    } else {
        Color.RED
    }
}