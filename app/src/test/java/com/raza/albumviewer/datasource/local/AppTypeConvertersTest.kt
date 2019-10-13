package com.raza.albumviewer.datasource.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raza.albumviewer.datasource.localdatabase.AppTypeConverters
import com.raza.albumviewer.datasource.network.LastFMAPIService
import junit.framework.Assert
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppTypeConvertersTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: LastFMAPIService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LastFMAPIService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun imageToString() {
        runBlocking {
            enqueueResponse("response.json")
            val resultResponse = service.searchArtist().body()
            val image = resultResponse?.results?.artistmatches?.artist?.get(0)?.image?.get(0)

            assertNotNull(AppTypeConverters().imageToString(image))
        }
    }

    @Test
    fun stringToImage() {
        val response = "{\"size\":\"small\",\"#text\":\"https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png\"}"
        runBlocking {
            enqueueResponse("response.json")
            val resultResponse = service.searchArtist().body()
            val image = resultResponse?.results?.artistmatches?.artist?.get(0)?.image?.get(0)
            assertEquals(AppTypeConverters().stringToImage(response), image)
        }
    }


    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = LastFMAPIService.javaClass.classLoader?.getResourceAsStream("$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(
            source.readString(Charsets.UTF_8))
        )
    }
}