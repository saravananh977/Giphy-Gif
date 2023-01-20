package com.sara.giphygif.data.network

import com.sara.giphygif.domain.model.GifData
import retrofit2.http.*

interface ApiInterface {

    @GET
    suspend fun getGifData(@Url url: String): GifData



}