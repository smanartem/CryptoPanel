package com.example.cryptopanel.adapters

import com.robinhood.spark.SparkAdapter

//незаюзаный класс
class SparklineAdapter(private val yData: ArrayList<Double>) : SparkAdapter() {

    override fun getCount(): Int {
        return yData.size
    }

    override fun getItem(index: Int): Any {
        return yData[index]
    }

    override fun getY(index: Int): Float {
        return yData[index].toFloat()
    }

}