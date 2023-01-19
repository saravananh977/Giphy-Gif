package com.sara.giphygif.data.repository


import com.sara.giphygif.data.entities.GifEntity
import com.sara.giphygif.data.localdb.AppDb
import com.sara.giphygif.data.network.ApiInterface
import com.sara.giphygif.domain.ResponseState
import com.sara.giphygif.domain.model.GifData
import com.sara.giphygif.utils.Constants
import com.sara.giphygif.utils.UrlUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class TrendingSearchRepositoryImpl @Inject constructor(
    val apiInterface: ApiInterface,
    val urlUtils: UrlUtils, val appDao: AppDb
) : TrendingSearchRepository {

    override suspend fun getTrendingDataFromServer(): Flow<ResponseState<GifData>> = flow {

        emit(ResponseState.LOADING())

        try {
            val response = apiInterface.getGifData(
                urlUtils.getTrendingApiUrl(
                    Constants.gifyApiKey,
                    "250"
                )
            )

            emit(ResponseState.SUCCESS(response))

        } catch (e: java.lang.Exception) {
            emit(ResponseState.FAILURE("Error" + e.localizedMessage))
        }


    }

    override suspend fun searchGifFromServer(searchQuery: String): Flow<ResponseState<GifData>> =
        flow {

            emit(ResponseState.LOADING())

            try {
                val response = apiInterface.getGifData(
                    urlUtils.getSearchApiUrl(
                        Constants.gifyApiKey, searchQuery,
                        "250"
                    )
                )

                emit(ResponseState.SUCCESS(response))

            } catch (e: java.lang.Exception) {
                emit(ResponseState.FAILURE("Error" + e.localizedMessage))
            }

        }

    override suspend fun insertGifIntoDb(gifEntity: GifEntity) {
        appDao.appDao().insertGif(gifEntity)
    }

    override suspend fun fetchAllFavouriteGifFromDb(): List<GifEntity> {
        return appDao.appDao().fetchAllGifFromDb()
    }

    override suspend fun removeGifFromDb(gifEntity: GifEntity) {
        appDao.appDao().removeGifFromDb(gifEntity)
    }

}