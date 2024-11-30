package com.unilasalle.td.services.database.controllers

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unilasalle.td.services.database.entities.Users

/**
 * Users controller
 *
 * This controller is responsible for managing users
 */
@Dao
interface UsersController {
    @Query("SELECT * FROM Users")
    suspend fun getAll(): List<Users>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: Users)

    @Delete
    suspend fun delete(users: Users)
}