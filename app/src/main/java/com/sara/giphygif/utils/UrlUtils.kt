package com.sara.giphygif.utils

import javax.inject.Inject

class UrlUtils @Inject constructor() {

    fun getTrendingApiUrl(apiKey: String,limit: String,rating: String="g"): String{
        return "/v1/gifs/trending?api_key=${apiKey}&limit=${limit}&rating=${rating}"
    }

    fun getSearchApiUrl(apiKey: String,searchQuesy:String,limit: String,rating: String="g"): String{
        return "/v1/gifs/search?api_key=${apiKey}&q=${searchQuesy}&limit=${limit}&offset=0&rating=${rating}&lang=en"
    }

}