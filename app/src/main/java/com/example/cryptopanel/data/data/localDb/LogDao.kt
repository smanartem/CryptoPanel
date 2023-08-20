package com.example.cryptopanel.data.data.localDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LogDao {
    @Query("SELECT * FROM $TABLE_NAME")
    fun getLogTransactions(): List<LogTransaction>

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteLogTransactions()

    @Insert
    fun insertLogTransactions(log: LogTransaction)
}