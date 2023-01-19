package com.sara.giphygif.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.sara.giphygif.data.entities.GifEntity
import com.sara.giphygif.domain.ResponseState
import com.sara.giphygif.domain.model.Data

@OptIn( ExperimentalFoundationApi::class)
@Composable
fun TabContent(
    pagerState: PagerState,
    trendingResponseState: State<ResponseState<List<Data>>>, favouriteResponseState: State<List<GifEntity>>

    ) {

    HorizontalPager(pageCount = 2, state = pagerState) { page ->

        when (page) {

            0 -> {

                TrendingComposable(trendingResponseState)

            }

            1 -> {

                FavouriteComposable(favouriteResponseState)

            }



        }


    }

}
