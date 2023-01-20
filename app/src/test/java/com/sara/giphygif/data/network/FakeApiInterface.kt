package com.sara.giphygif.data.network

import com.sara.giphygif.domain.model.GifData
import com.sara.giphygif.domain.model.Meta
import com.sara.giphygif.domain.model.Pagination

class FakeApiInterface: ApiInterface{

     var getGifDataCall  = { url:String ->

         GifData(emptyList(),Meta("a","b",1), Pagination(1,2,3))

     }

    override suspend fun getGifData(url: String): GifData {

        return getGifDataCall(url)

    }


}