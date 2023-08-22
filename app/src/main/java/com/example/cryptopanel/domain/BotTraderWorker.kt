package com.example.cryptopanel.domain

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class BotTraderWorker(context: Context, workParams: WorkerParameters): Worker(context, workParams) {
    //TODO: inject fields here

    override fun doWork(): Result {
        //TODO: add some work here

        return Result.success()
    }

}