package com.example.cryptopanel.adapters

//в отдельный файл для утилит в отдельном пакедже для утилит
//можно сделать экстеншеном для Double
//имя не совсем отражает функционал
//можно в 1-ну строку
fun doubleToString(price: Double): String {
    return if (price < 1) "%.6f".format(price) else "%.2f".format(price)
}