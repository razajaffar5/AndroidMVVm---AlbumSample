package com.raza.albumviewer.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raza.albumviewer.datasource.network.LastFMAPIService
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class LastFmAPIServiceTest {
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
    fun requestSearchArtistResponse() {
        runBlocking {
            enqueueResponse("response.json")
            val resultResponse = service.searchArtist().body()

            val request = mockWebServer.takeRequest()
            assertNotNull(resultResponse)
            assertThat(request.path, `is`("/2.0/?format=json"))
        }
    }

    @Test
    fun searchInitialPageResponse() {
        runBlocking {
            enqueueResponse("response.json")
            val resultResponse = service.searchArtist().body()
            val artistItems = resultResponse!!.results.artistmatches.artist

            assertThat(resultResponse.results.opensearchItemsPerPage.toInt(), `is`(30))
            assertThat(artistItems.size, `is`(30))
        }
    }

    @Test
    fun artistPaginationTest() {
        runBlocking {
            enqueueResponse("response.json")
            val resultResponse = service.searchArtist().body()

            val totalResults = resultResponse?.results?.opensearchTotalResults?.toInt()
            val itemsList = resultResponse?.results?.artistmatches?.artist?.size
            val result = totalResults!! > itemsList!!

            assertThat(result, `is`(true))
        }
    }


    @Test
    fun getArtistItem() {
        runBlocking {
            enqueueResponse("response.json")
            val resultResponse = service.searchArtist().body()
            val artistItems = resultResponse?.results?.artistmatches?.artist

            val item = artistItems?.get(0)
            item?.let { artist ->
                assertThat(artist.mbid, `is`("bfcc6d75-a6a5-4bc6-8282-47aec8531818"))
                assertThat(artist.name, `is`("Cher"))
                assertThat(artist.url, `is`("https://www.last.fm/music/Cher"))
                assertThat(artist.listeners, `is`("1139655"))
            }
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
