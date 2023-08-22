package com.example.cryptopanel.di

import android.content.Context
import androidx.room.Room
import com.example.cryptopanel.data.data.localDb.LogTransactionsDatabase
import com.example.cryptopanel.data.data.localDb.TABLE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDbModule = module {
    single<LogTransactionsDatabase> { provideLocalDb(androidContext()) }
}

private fun provideLocalDb(context: Context): LogTransactionsDatabase {
    return Room.databaseBuilder(
        context,
        LogTransactionsDatabase::class.java,
        TABLE_NAME,
    ).fallbackToDestructiveMigration().build()
}