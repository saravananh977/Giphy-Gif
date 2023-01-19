package com.sara.giphygif.data.repository

import com.sara.giphygif.data.entities.GifEntity
import com.sara.giphygif.domain.ResponseState
import com.sara.giphygif.domain.model.GifData
import kotlinx.coroutines.flow.Flow

interface TrendingSearchRepository {

   suspend fun getTrendingDataFromServer(): Flow<ResponseState<GifData>>

   suspend fun searchGifFromServer(searchQuery: String): Flow<ResponseState<GifData>>

   suspend fun insertGifIntoDb(gifEntity: GifEntity)

   suspend fun fetchAllFavouriteGifFromDb():List<GifEntity>

   suspend fun removeGifFromDb(gifEntity: GifEntity)



}