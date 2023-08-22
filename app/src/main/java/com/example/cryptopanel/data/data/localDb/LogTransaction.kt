package com.example.cryptopanel.data.data.localDb

import androidx.room.ColumnInfo
import androidx.room.Entity
@Entity(tableName = TABLE_NAME)
data class LogTransaction(
    @ColumnInfo
    val time: String,
    @ColumnInfo
    val operator: String,
    @ColumnInfo
    val information: String
)