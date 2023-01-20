package com.sara.giphygif.di

import android.app.Application
import androidx.room.Room
import com.sara.giphygif.data.localdb.AppDb
import com.sara.giphygif.data.network.ApiInterface
import com.sara.giphygif.data.repository.GifRepository
import com.sara.giphygif.data.repository.GifRepositoryImpl
import com.sara.giphygif.utils.Constants
import com.sara.giphygif.utils.UrlUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGifRepository(
        apiInterface: ApiInterface,
        urlUtils: UrlUtils,appDb: AppDb
    ): GifRepository {
        return GifRepositoryImpl(apiInterface, urlUtils,appDb)
    }

    @Provides
    @Singleton
    fun provideApiInterface(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }


    @Provides
    @Singleton
    fun provideGifDb(app: Application): AppDb {
        return Room.databaseBuilder(app, AppDb::class.java, Constants.DATABASE_NAME).build()
    }

    @Provides
    fun appDao(appDatabase: AppDb)= appDatabase.appDao()


}