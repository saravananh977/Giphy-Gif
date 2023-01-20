package com.sara.giphygif.presentation.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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
import com.sara.giphygif.presentation.MainViewModel
import com.sara.giphygif.utils.ErrorMessageComposable


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun FavouriteComposable(
    favouriteResponseState: State<List<GifEntity>>
) {

    val mainViewModel = hiltViewModel<MainViewModel>()

    val context = LocalContext.current


    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize(1f)) {

        val imageLoader = ImageLoader.Builder(context)
            .componentRegistry(fun ComponentRegistry.Builder.() {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder(context))
                } else {
                    add(GifDecoder())
                }
            }).placeholder(R.drawable.loader)
            .build()


        Column {

            if (favouriteResponseState.value.isEmpty()){

                ErrorMessageComposable(errorMessage = stringResource(id = R.string.no_favourite_present))
            }
            else{
                favouriteResponseState.value.let { gifList ->


                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(1f)
                    ) {

                        items(gifList.size) {

                            Card(modifier = Modifier
                                .padding(8.dp)
                                .border(
                                    1.dp,
                                    Color.LightGray,
                                    RoundedCornerShape(5.dp)
                                ), onClick = {


                            }) {

                                Image(
                                    painter = rememberImagePainter(
                                        imageLoader = imageLoader,
                                        data = gifList.get(it).url,
                                        builder = {
                                            size(400, 400)
                                        }
                                    ),
                                    contentDescription = null

                                )

                                Row(horizontalArrangement = Arrangement.End) {

                                    IconButton(onClick = {

                                        mainViewModel.removeFromFavourite(gifList.get(it))

                                    }) {

                                        Icon(
                                            painter =  painterResource(id = R.drawable.ic_favourite_selected) ,
                                            contentDescription = "",tint= Color.Magenta
                                        )

                                    }

                                }



                            }
                        }


                    }
                }

            }
        }

    }


}



