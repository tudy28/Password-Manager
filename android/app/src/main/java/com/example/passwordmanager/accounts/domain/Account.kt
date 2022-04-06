package com.example.passwordmanager.accounts.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "accounts_table")
data class Account(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "account_id") val id: Long,
    @ColumnInfo(name="app_name") var application_name : String,
    @ColumnInfo(name="username") var username : String,
    @ColumnInfo(name="password") var password : String,
    @ColumnInfo(name = "user_id") var user_id: Long,
    @ColumnInfo(name = "offline_operation") var offline_operation: Boolean
    )
