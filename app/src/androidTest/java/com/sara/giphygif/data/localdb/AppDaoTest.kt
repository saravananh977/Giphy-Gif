package com.sara.giphygif.data.localdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.sara.giphygif.data.entities.GifEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: AppDao
    private lateinit var database: AppDb

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDb::class.java
        ).build()
        dao = database.appDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertGifTest() = runBlocking{

        val gif = GifEntity("1","title1","gif","https://gif.com")

        dao.insertGif(gif)

        val allgif = dao.fetchAllGifFromDb()
        Truth.assertThat(allgif).isEqualTo(gif)
    }


    @Test
    fun removeGifFromDbTest() = runBlocking {

        val gif = GifEntity("1","title1","gif","https://gif.com")

        dao.insertGif(gif)
        dao.removeGifFromDb(gif)
        val gifResult = dao.fetchAllGifFromDb()
        Truth.assertThat(gifResult).isEmpty()
    }
}