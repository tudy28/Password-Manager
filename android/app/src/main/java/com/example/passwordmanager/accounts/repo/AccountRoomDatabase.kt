package com.example.passwordmanager.accounts.repo

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.passwordmanager.accounts.domain.Account
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities=[Account::class],version=1,exportSchema=false)
abstract class AccountRoomDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao

}