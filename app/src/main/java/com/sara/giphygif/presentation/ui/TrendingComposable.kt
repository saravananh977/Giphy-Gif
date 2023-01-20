@file:OptIn(ExperimentalMaterialApi::class)

package com.sara.giphygif.presentation.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ComponentRegistry
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.sara.giphygif.R
import com.sara.giphygif.data.entities.GifEntity
import com.sara.giphygif.domain.ResponseState
import com.sara.giphygif.domain.model.Data
import com.sara.giphygif.presentation.MainViewModel
import com.sara.giphygif.utils.ErrorMessageComposable
import com.sara.giphygif.utils.Loader

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun TrendingComposable(
    trendingResponseState: State<ResponseState<List<Data>>>
) {

    val mainViewModel = hiltViewModel<MainViewModel>()
    val context = LocalContext.current


    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(4.dp)
                )
                .fillMaxWidth(1f)
        ) {


            TextField(
                value = mainViewModel.searchQuery.value,
                onValueChange = {

                    if (it.isEmpty()) {
                        mainViewModel.clearSearch(context)
                    } else {
                        mainViewModel.searchGif(it,context)
                    }

                },
                modifier = Modifier
                    .background(
                        MaterialTheme.colors.surface
                    )
                    .align(Alignment.CenterVertically)
                    .weight(6f),

                colors = TextFieldDefaults.textFieldColors(textColor=MaterialTheme.colors.primary,
                    backgroundColor = MaterialTheme.colors.surface,
                    disabledLabelColor = MaterialTheme.colors.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ), placeholder = {
                    Text(text = stringResource(id = R.string.search_hint), color = MaterialTheme.colors.primary)
                }
            )


            AnimatedVisibility(
                visible = mainViewModel.searchQuery.value.isNotEmpty(), modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            mainViewModel.clearSearch(context)

                        },
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_clear),
                            contentDescription = "clear"
                        )
                    }

                }
            }


        }


        when (trendingResponseState.value) {


            is ResponseState.LOADING -> {
                Loader()
            }

            is ResponseState.SUCCESS -> {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(1f)
                ) {

                    trendingResponseState.value.list?.let {

                        val context = LocalContext.current

                        val imageLoader = ImageLoader.Builder(context)
                            .componentRegistry(fun ComponentRegistry.Builder.() {
                                if (Build.VERSION.SDK_INT >= 28) {
                                    add(ImageDecoderDecoder(context))
                                } else {
                                    add(GifDecoder())
                                }
                            }).placeholder(R.drawable.loader)
                            .build()

                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(1f),
                            content = {
                                items(items = it, itemContent = {

                                    Card(modifier = Modifier
                                        .padding(8.dp)
                                        .border(
                                            1.dp,
                                            Color.LightGray,
                                            RoundedCornerShape(5.dp)
                                        )
                                        .align(Alignment.CenterHorizontally), onClick = {


                                    }) {

                                        Box(
                                            contentAlignment = Alignment.TopEnd,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        ) {

                                            Image(
                                                painter = rememberImagePainter(
                                                    imageLoader = imageLoader,
                                                    data = it.images.original.url,
                                                    builder = {
                                                        size(500, 500)
                                                    }
                                                ),
                                                contentDescription = null
                                            )

                                            IconButton(onClick = {

                                                val gifEntity = GifEntity(
                                                    it.id,
                                                    it.title,
                                                    it.type,
                                                    it.images.original.url
                                                )

                                                if (it.isFavourite) {
                                                    mainViewModel.removeFromFavourite(gifEntity)

                                                } else {
                                                    mainViewModel.addToFavourite(
                                                        gifEntity
                                                    )
                                                }

                                            }) {

                                                Icon(
                                                    painter = if (it.isFavourite) painterResource(
                                                        id = R.drawable.ic_favourite_selected
                                                    ) else painterResource(
                                                        id = R.drawable.ic_favourite
                                                    ),
                                                    contentDescription = "", tint = Color.Magenta
                                                )

                                            }


                                        }


                                    }
                                })
                            })
                    }

                }

            }

            is ResponseState.FAILURE ->{

                trendingResponseState.value.message?.let { ErrorMessageComposable(errorMessage = it) }
            }

            else -> {

            }
        }
    }

}

