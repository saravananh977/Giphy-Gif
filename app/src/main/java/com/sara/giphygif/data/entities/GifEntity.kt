package com.sara.giphygif.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GifEntity(
   @PrimaryKey val id: String,
    val title: String,
    val type: String,
    val url: String,
)