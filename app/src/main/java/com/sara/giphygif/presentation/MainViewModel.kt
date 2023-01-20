package com.sara.giphygif.presentation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sara.giphygif.R
import com.sara.giphygif.data.entities.GifEntity
import com.sara.giphygif.data.repository.GifRepository
import com.sara.giphygif.domain.ResponseState
import com.sara.giphygif.domain.model.Data
import com.sara.giphygif.utils.isConnectedToInternet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: GifRepository) : ViewModel() {


    var searchQuery = mutableStateOf("")

    val trendindResponseState = MutableStateFlow<ResponseState<List<Data>>>(ResponseState.START())

    val favouriteResponseState = MutableStateFlow<List<GifEntity>>(emptyList())

    var trendingListDataClone = ArrayList<Data>()

    lateinit var favouriteFromDb: List<GifEntity>

    private var searchJob: Job? = null

    val coroutineExceptionHandler: CoroutineExceptionHandler


    init {

        coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->

            viewModelScope.launch {

            }
        }

    }

    fun fetchTrendingGif(context: Context) {

        viewModelScope.launch(coroutineExceptionHandler) {

            if (isConnectedToInternet(context)) {


                withContext(Dispatchers.IO) {

                    val remoteWordInfos = repository.getTrendingDataFromServer()


                    remoteWordInfos.onEach {

                        when (it) {

                            is ResponseState.SUCCESS -> {

                                trendingListDataClone.clear()

                                val trendingListData = (it.list?.data as ArrayList<Data>?)!!

                                favouriteFromDb = repository.fetchAllFavouriteGifFromDb()

                                if (favouriteFromDb.isNotEmpty()) {

                                    trendingListData.forEach { data ->

                                        var isFavourite = false

                                        run breaking@{
                                            favouriteFromDb.forEach {

                                                if (data.id == it.id) {

                                                    isFavourite = true

                                                    return@breaking

                                                }
                                            }
                                        }


                                        if (isFavourite) {
                                            data.isFavourite = true
                                        }

                                        trendingListDataClone.add(data)

                                    }
                                } else {
                                    trendingListDataClone.addAll(trendingListData)
                                }


                                trendindResponseState.emit(
                                    ResponseState.SUCCESS(
                                        trendingListDataClone
                                    )
                                )

                            }

                            is ResponseState.LOADING -> {

                                trendindResponseState.emit(ResponseState.LOADING())

                            }

                            is ResponseState.FAILURE -> {

                                trendindResponseState.emit(ResponseState.FAILURE(message = it.message!!))

                            }

                            is ResponseState.START -> {


                            }
                        }

                    }.launchIn(this)


                }
            } else {

                trendindResponseState.emit(ResponseState.FAILURE(context.getString(R.string.no_internet)))

            }

        }


    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun addToFavourite(gifEntity: GifEntity) {

        viewModelScope.launch(coroutineExceptionHandler) {

            withContext(Dispatchers.IO) {

                repository.insertGifIntoDb(gifEntity)

                favouriteResponseState.emit(repository.fetchAllFavouriteGifFromDb())

                val data = trendingListDataClone.find {
                    it.id == gifEntity.id
                }

                data?.isFavourite = true

                trendindResponseState.emit(ResponseState.SUCCESS(trendingListDataClone))

            }

        }


    }


    fun removeFromFavourite(gifEntity: GifEntity) {

        viewModelScope.launch(coroutineExceptionHandler) {

            withContext(Dispatchers.IO) {

                repository.removeGifFromDb(gifEntity)

                favouriteResponseState.emit(repository.fetchAllFavouriteGifFromDb())

                val data = trendingListDataClone.find {
                    it.id == gifEntity.id
                }

                data?.isFavourite = false

                trendindResponseState.emit(ResponseState.SUCCESS(trendingListDataClone))


            }

        }


    }


    fun fetchAllFavouriteGifsFromDb() {

        viewModelScope.launch(coroutineExceptionHandler) {

            withContext(Dispatchers.IO) {

                favouriteResponseState.emit(repository.fetchAllFavouriteGifFromDb())

            }

        }

    }


    fun searchGif(query: String, context: Context) {

        searchJob?.cancel()

        searchQuery.value = query

        if (isConnectedToInternet(context)) {

            searchJob = viewModelScope.launch(coroutineExceptionHandler) {

                delay(500L)

                withContext(Dispatchers.IO) {

                    val remoteWordInfos = repository.searchGifFromServer(query)


                    remoteWordInfos.onEach {

                        when (it) {

                            is ResponseState.SUCCESS -> {


                                trendingListDataClone.clear()

                                val trendingListData = (it.list?.data as ArrayList<Data>?)!!

                                favouriteFromDb = repository.fetchAllFavouriteGifFromDb()

                                if (favouriteFromDb.isNotEmpty()) {

                                    trendingListData.forEach { data ->

                                        var isFavourite = false

                                        run breaking@{
                                            favouriteFromDb.forEach {

                                                if (data.id == it.id) {

                                                    isFavourite = true

                                                    return@breaking

                                                }
                                            }
                                        }


                                        if (isFavourite) {
                                            data.isFavourite = true
                                        }

                                        trendingListDataClone.add(data)

                                    }
                                } else {
                                    trendingListDataClone.addAll(trendingListData)
                                }


                                trendindResponseState.emit(
                                    ResponseState.SUCCESS(
                                        trendingListDataClone
                                    )
                                )

                            }

                            is ResponseState.LOADING -> {

                                trendindResponseState.emit(ResponseState.LOADING())

                            }

                            is ResponseState.FAILURE -> {

                                trendindResponseState.emit(ResponseState.FAILURE(message = it.message!!))

                            }

                            is ResponseState.START -> {


                            }
                        }

                    }.launchIn(this)


                }
            }

        } else {
            viewModelScope.launch {
                trendindResponseState.emit(ResponseState.FAILURE(context.getString(R.string.no_internet)))
            }

        }

    }


    fun clearSearch(context: Context) {
        searchQuery.value = ""
        searchJob?.cancel()
        fetchTrendingGif(context)
    }

}