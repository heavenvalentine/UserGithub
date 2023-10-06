package com.dicoding.usergithub.ui.favorite.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.usergithub.data.local.entity.FavoriteUser
import com.dicoding.usergithub.data.local.room.FavUserDao
import com.dicoding.usergithub.data.local.room.FavUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavUserRepository(application: Application) {
    private val mFavUserDao: FavUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val dbFavUser = FavUserDatabase.getDatabase(application)
        mFavUserDao = dbFavUser.FavUserDao()
    }

    fun getUserFavUsername(username: String): LiveData<FavoriteUser?> = mFavUserDao.getUserFavUsername(username)

    fun getAllFavUser(): LiveData<List<FavoriteUser>> = mFavUserDao.getAllFavUser()

    fun delete(favoriteUser: FavoriteUser){
        executorService.execute{mFavUserDao.delete(favoriteUser)}
    }

    fun insert(favoriteUser: FavoriteUser){
        executorService.execute{mFavUserDao.insert(favoriteUser)}
    }

}