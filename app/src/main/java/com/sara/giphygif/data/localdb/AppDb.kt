package com.sara.giphygif.data.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sara.giphygif.data.entities.GifEntity


@Database(entities = [GifEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun appDao(): AppDao

}