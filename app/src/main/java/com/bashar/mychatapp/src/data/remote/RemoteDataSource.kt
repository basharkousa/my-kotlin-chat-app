package com.bashar.mychatapp.src.data.remote

import com.bashar.mychatapp.src.data.remote.api.AppApi
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val appApi: AppApi) {

//    suspend fun getArtistsRequest(artistName: String) = lastFmApi.getArtistsRequest(artistName)
//
//    suspend fun searchArtistRequest(artistName: String) = lastFmApi.getSearchArtistsRequest(artistName)
//
//    suspend fun getAlbumsArtistRequest(artist: Artist, page: Int, limit: Int) =
//        lastFmApi.getAlbumsArtistRequest(artist = artist.name, page = page, limit = limit)
//
//    suspend fun getAlbumDetailsRequest(album: String) =
//        lastFmApi.getAlbumDetailsRequest(album)
}