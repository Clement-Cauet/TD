package com.unilasalle.td.services.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class Quotes(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val author: String
)