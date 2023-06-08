package com.bashar.mychatapp.src.data

import com.bashar.mychatapp.src.data.local.LocalDataSource
import com.bashar.mychatapp.src.data.models.Chat
import com.bashar.mychatapp.src.data.models.User
import com.bashar.mychatapp.src.data.models.Message
import com.bashar.mychatapp.src.data.models.base.BaseApiResponse
import com.bashar.mychatapp.src.data.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource
) : BaseApiResponse() {

    suspend fun doAllTransActions() = withContext(Dispatchers.IO){
        localDataSource.doAllTransActions()
    }

    fun getAllUsers() = localDataSource.getAllUsers().flowOn(Dispatchers.IO)
    fun getAllChats(userId: Int) = localDataSource.getAllChats(userId).flowOn(Dispatchers.IO)

    fun getMessages(chatId: Int) = localDataSource.getMessages(chatId).flowOn(Dispatchers.IO)

    suspend fun insertMessage(message: Message) = withContext(Dispatchers.IO) {
        localDataSource.insertMessage(message)
    }


    fun getUserUserByEmail(email: String): Flow<User?> = flow{
        localDataSource.getUserByEmail(email).collect{
            if(it != null){
                emit(it.toUser())
            }else{
                emit(null)
            }
        }
    }.flowOn(Dispatchers.IO)


//    suspend fun getArtists(artistName: String): Flow<NetworkResult<ArtistsResponse>> {
//        return flow<NetworkResult<ArtistsResponse>> {
//            emit(NetworkResult.Loading())
//            emit(safeApiCall { remoteDataSource.getArtistsRequest(artistName) })
//        }.flowOn(Dispatchers.IO)
//    }
//
//    suspend fun getSearchArtists(artistName: String): Flow<NetworkResult<ArtistSearchResponse>> {
//        return flow {
//            emit(NetworkResult.Loading())
//            emit(safeApiCall { remoteDataSource.searchArtistRequest(artistName) })
//        }.flowOn(Dispatchers.IO)
//    }
//
//    suspend fun getAlbumsArtist(
//        page: Int,
//        limit: Int,
//        artist: Artist,
//    ): Flow<NetworkResult<AlbumsArtistResponse>> {
//        return flow {
//            emit(NetworkResult.Loading())
//            emit(safeApiCall { remoteDataSource.getAlbumsArtistRequest(artist, page, limit) })
//        }.flowOn(Dispatchers.IO)
//    }
//
//    suspend fun getAlbumDetails(
//        album: String
//    ): Flow<NetworkResult<AlbumDetailsResponse>> {
//        return flow {
//            emit(NetworkResult.Loading())
//            emit(safeApiCall { remoteDataSource.getAlbumDetailsRequest(album) })
//        }.flowOn(Dispatchers.IO)
//    }
//
//     fun getAllLocalAlbums() = localDataSource.getLocalAlbums().flowOn(Dispatchers.IO)
//     fun getAllLocalAlbumsFlow(): Flow<List<AlbumEntity>> {
//
//        return localDataSource.getLocalAlbums().flowOn(Dispatchers.IO)
//    }
//
//    fun isAlbumExist(album: Album): Flow<AlbumEntity>{
//        return localDataSource.getAlbumById(album)
//    }
//
//
//
//    suspend fun insertAlbum(album: Album) = withContext(Dispatchers.IO) {
//        localDataSource.insertAlbum(album)
//    }
//
//    suspend fun deleteAlbum(album: Album) =
//        withContext(Dispatchers.IO) { localDataSource.deleteAlbum(album) }
//
//    suspend fun deleteAllAlbums() = withContext(Dispatchers.IO) { localDataSource.deleteAll() }


}


