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

            if (!this::trendingResponseState.isInitialized) {
                trendingResponseState =
                    mainViewModel.trendindResponseState.collectAsState(initial = ResponseState.LOADING())
            }

            val favouriteState = mainViewModel.favouriteResponseState.collectAsState(emptyList())

            val pagerState = rememberPagerState(initialPage = 0)

            val coroutineScope = rememberCoroutineScope()

            if (pagerState.currentPage != 0) {
                mainViewModel.fetchAllFavouriteGifsFromDb()
            }

            mainViewModel.fetchTrendingGif()


            GiphyTheme {

                Column() {

                    TabHeaderComposable(pagerState, coroutineScope, listOf("Trending", "Favourite"))

                    TabContent(pagerState, trendingResponseState, favouriteState)

                }

            }

        }
    }
}