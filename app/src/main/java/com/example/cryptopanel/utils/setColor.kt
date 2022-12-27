package com.example.cryptopanel.utils

import android.graphics.Color

fun setColor(x: Double): Int {
    return if (x > 0) {
        Color.GREEN
    } else {
        Color.RED
    }
}