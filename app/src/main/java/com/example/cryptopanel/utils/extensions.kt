package com.example.cryptopanel.utils

fun Double.toFormat() = if (this < 1) "%.6f".format(this) else "%.2f".format(this)