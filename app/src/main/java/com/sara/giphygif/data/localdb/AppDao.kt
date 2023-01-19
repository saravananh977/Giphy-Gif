package com.sara.giphygif.data.localdb

import androidx.room.*
import com.sara.giphygif.data.entities.GifEntity

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGif(gifEntity: GifEntity)

    @Query("Select * from GifEntity")
    suspend fun fetchAllGifFromDb(): List<GifEntity>

    @Delete
    suspend fun removeGifFromDb(gifEntity: GifEntity)


}