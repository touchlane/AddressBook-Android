package com.touchlane.addressbook.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.touchlane.addressbook.db.model.DbContact

@Dao
interface ContactDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun save(contact: DbContact): Long?

    @Query("SELECT * from Contact")
    fun loadAll(): List<DbContact>

    @Query("SELECT * from Contact WHERE firstName LIKE :query OR lastName LIKE :query")
    fun search(query: String): List<DbContact>

    @Query("SELECT * FROM Contact WHERE id = :id")
    fun findById(id: Long): DbContact?
}