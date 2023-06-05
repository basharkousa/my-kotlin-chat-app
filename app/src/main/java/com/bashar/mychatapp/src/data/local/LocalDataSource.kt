package com.bashar.mychatapp.src.data.local


import com.bashar.mychatapp.src.data.local.datasources.room.dao.TableDao
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSource @Inject constructor(private val albumDao: TableDao) {

//    fun getLocalAlbums(): Flow<List<AlbumEntity>> = albumDao.allAlbums
//
//    fun getAlbumById(album: Album): Flow<AlbumEntity> = albumDao.getAlbumById(album.mbid)
//
//    fun insertAlbum(album: Album) {
//        println("LocalDataSource ${album.name}")
////        albumDao.insertAlbum(
////            AlbumEntity(
////                mbid = "1",
////                name = "Bashar",
////                playcount = 1,
////                url = "oma",
////                image = "https:\\/\\/lastfm.freetls.fastly.net\\/i\\/u\\/34s\\/0db2be6f7dca7f6b97b2e96fa6ac2d54.jpg",
////                listeners = "100"
////            )
////        )
//
//        albumDao.insertAlbum(AlbumEntity.from(album))
//    }
//
//    fun updateAlbum(album: Album) = albumDao.updateAlbum(AlbumEntity.from(album))
//
//    fun deleteAlbum(album: Album) = albumDao.deleteAlbum(AlbumEntity.from(album))
//
//    fun deleteAll() = albumDao.deleteAll()
}