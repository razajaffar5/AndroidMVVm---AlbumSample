package com.raza.albumviewer.data.album

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.raza.albumviewer.data.network.AlbumDetailResponse

/**
 * The Data Access Object for the Album class.
 */
@Dao
interface AlbumDao {

    @Query("SELECT * FROM album")
    fun getSavedAlbums(): LiveData<List<AlbumDetailResponse.AlbumItem>>

    @Query("SELECT * FROM album WHERE album.id = :id")
    fun loadAlbumDetailsById(id: Int): LiveData<AlbumDetailResponse.AlbumItem>

    @Query("SELECT * FROM album WHERE album.name == :album AND album.artist == :artist")
    fun loadAlbumDetailsByName(album: String, artist: String): LiveData<AlbumDetailResponse.AlbumItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<AlbumDetailResponse.AlbumItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(album: AlbumDetailResponse.AlbumItem) : Long

    @Delete
    suspend fun deleteItem(album: AlbumDetailResponse.AlbumItem)

}