package com.touchlane.addressbook.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.touchlane.addressbook.db.model.DbContact
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface ContactDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun save(contact: DbContact): Single<Long>

    @Query("SELECT * from Contact")
    fun loadAll(): Observable<List<DbContact>>

    @Query("SELECT * from Contact WHERE firstName LIKE :query OR lastName LIKE :query")
    fun search(query: String): Observable<List<DbContact>>

    @Query("SELECT * FROM Contact WHERE id = :id")
    fun findById(id: Long): Single<DbContact>
}