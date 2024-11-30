package com.unilasalle.td.services.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.unilasalle.td.services.database.controllers.QuotesController
import com.unilasalle.td.services.database.controllers.UsersController
import com.unilasalle.td.services.database.entities.Quotes
import com.unilasalle.td.services.database.entities.Users

/**
 * App database
 *
 * This class is responsible for managing the database
 */
@Database(entities = [Users::class, Quotes::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersController(): UsersController
    abstract fun quotesController(): QuotesController

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}