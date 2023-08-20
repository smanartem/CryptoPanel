package com.example.cryptopanel.data.data.localDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val TABLE_NAME = "log_transactions"
@Database(entities = [LogTransaction::class], version = 1, exportSchema = false)
abstract class LogTransactionsDatabase() : RoomDatabase() {
    abstract fun getLogTransactionsDao(): LogDao

    companion object {
        fun getDatabase(context: Context): LogTransactionsDatabase =
            Room.databaseBuilder(
                context,
                LogTransactionsDatabase::class.java,
                TABLE_NAME,
            ).fallbackToDestructiveMigration().build()
    }
}