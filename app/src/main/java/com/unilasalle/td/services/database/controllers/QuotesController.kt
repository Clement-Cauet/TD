package com.unilasalle.td.services.database.controllers

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unilasalle.td.services.database.entities.Quotes

/**
 * Quotes controller
 *
 * This controller is responsible for managing quotes
 */
@Dao
interface QuotesController {
    @Query("SELECT * FROM Quotes")
    suspend fun getAll(): List<Quotes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quotes: Quotes)

    @Delete
    suspend fun delete(quotes: Quotes)
}