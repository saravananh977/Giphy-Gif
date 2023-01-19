package com.sara.giphygif.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sara.giphygif.data.entities.GifEntity
import com.sara.giphygif.data.repository.TrendingSearchRepository
import com.sara.giphygif.domain.ResponseState
import com.sara.giphygif.domain.model.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: TrendingSearchRepository) : ViewModel() {


    var searchQuery = mutableStateOf("")

    val trendindResponseState = MutableSharedFlow<ResponseState<List<Data>>>()

    val favouriteResponseState = MutableSharedFlow<List<GifEntity>>()


    private var searchJob: Job? = null


//    init {
//        fetchTrendingGif()
//    }

    fun fetchTrendingGif() {

        viewModelScope.launch {

            withContext(Dispatchers.IO) {

                val remoteWordInfos = repository.getTrendingDataFromServer()


                remoteWordInfos.onEach {

                    when (it) {

                        is ResponseState.SUCCESS -> {

                            trendindResponseState.emit(ResponseState.SUCCESS(it.list?.data!!))

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

    }


    fun addToFavourite(gifEntity: GifEntity){

        viewModelScope.launch {

            withContext(Dispatchers.IO){

                repository.insertGifIntoDb(gifEntity)

                favouriteResponseState.emit( repository.fetchAllFavouriteGifFromDb())

            }

        }


    }


    fun removeFromFavourite(gifEntity: GifEntity){

        viewModelScope.launch {

            withContext(Dispatchers.IO){

                repository.removeGifFromDb(gifEntity)

                favouriteResponseState.emit( repository.fetchAllFavouriteGifFromDb())

            }

        }


    }


    fun fetchAllFavouriteGifsFromDb(){

        viewModelScope.launch {

            withContext(Dispatchers.IO){

                favouriteResponseState.emit( repository.fetchAllFavouriteGifFromDb())

            }

        }

    }


    fun searchGif(query: String) {

        searchQuery.value = query

        searchJob?.cancel()

        searchJob = viewModelScope.launch {

            delay(500L)

            withContext(Dispatchers.IO) {

                val remoteWordInfos = repository.searchGifFromServer(query)


                remoteWordInfos.onEach {

                    when (it) {

                        is ResponseState.SUCCESS -> {

                            trendindResponseState.emit(ResponseState.SUCCESS(it.list?.data!!))

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

    }


    fun clearSearch() {
        searchQuery.value = ""
        fetchTrendingGif()
    }

}