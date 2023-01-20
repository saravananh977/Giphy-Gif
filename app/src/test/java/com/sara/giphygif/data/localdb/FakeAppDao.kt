package com.sara.giphygif.data.localdb

import com.sara.giphygif.data.entities.GifEntity

class FakeAppDao: AppDao{

    val gifList= mutableListOf<GifEntity>()

    override suspend fun insertGif(gifEntity: GifEntity) {
        gifList.add(gifEntity)
    }

    override suspend fun fetchAllGifFromDb(): List<GifEntity> {
       return gifList
    }

    override suspend fun removeGifFromDb(gifEntity: GifEntity) {
        gifList.remove(gifEntity)
    }

}