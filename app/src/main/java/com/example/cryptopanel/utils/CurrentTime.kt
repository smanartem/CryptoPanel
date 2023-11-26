package com.example.cryptopanel.utils

import java.text.SimpleDateFormat
import java.util.Locale

private val timeFormatter = SimpleDateFormat("HH.mm.ss dd.MM.yy", Locale.GERMANY)
fun getCurrentTime(): String {
    return timeFormatter.format(System.currentTimeMillis())
}