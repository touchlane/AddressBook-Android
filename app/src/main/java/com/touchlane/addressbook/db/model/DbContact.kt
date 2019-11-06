package com.touchlane.addressbook.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Contact", indices = [Index(value = ["email"], name = "uk_email", unique = true)])
data class DbContact(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo val firstName: String,
    @ColumnInfo val lastName: String,
    @ColumnInfo val email: String,
    @ColumnInfo val phone: String,
    @ColumnInfo val address: String
)