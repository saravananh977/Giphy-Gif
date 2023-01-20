package com.sara.giphygif.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import com.sara.giphygif.domain.ResponseState
import com.sara.giphygif.domain.model.Data
import com.sara.giphygif.presentation.theme.GiphyTheme
import com.sara.giphygif.presentation.ui.TabHeaderComposable
import com.sara.giphygif.presentation.ui.TabContent
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalFoundationApi::class)
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModels()

    lateinit var trendingResponseState: State<ResponseState<List<Data>>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val pagerState = rememberPagerState(initialPage = 0)

            val coroutineScope = rememberCoroutineScope()

            trendingResponseState =
                mainViewModel.trendindResponseState.collectAsState()


            val favouriteState =
                mainViewModel.favouriteResponseState.collectAsState()

            if (pagerState.currentPage != 0) {
                mainViewModel.fetchAllFavouriteGifsFromDb()
            }
            else{
                if (mainViewModel.searchQuery.value.isEmpty()) {
                    mainViewModel.fetchTrendingGif(this)
                } else {
                    mainViewModel.searchGif(mainViewModel.searchQuery.value,this)
                }
            }



            GiphyTheme {


                Column() {

                    TabHeaderComposable(pagerState, coroutineScope, listOf("Trending", "Favourite"))

                    TabContent(pagerState, trendingResponseState, favouriteState)

                }

            }

        }
    }
}