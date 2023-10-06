package com.dicoding.usergithub.ui.favorite.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.usergithub.data.local.entity.FavoriteUser
import com.dicoding.usergithub.data.local.room.FavUserDao
import com.dicoding.usergithub.data.local.room.FavUserDatabase
import com.dicoding.usergithub.ui.favorite.executors.AppExecutors

class FavUserRepository(application: Application) {
    private val mFavUserDao: FavUserDao
    private val appExecutors = AppExecutors.getInstance()

    init {
        val dbFavUser = FavUserDatabase.getDatabase(application)
        mFavUserDao = dbFavUser.FavUserDao()
    }

    fun delete(favoriteUser: FavoriteUser) {
        appExecutors.diskIO.execute {
            mFavUserDao.delete(favoriteUser)
        }
    }

    fun insert(favoriteUser: FavoriteUser) {
        appExecutors.diskIO.execute {
            mFavUserDao.insert(favoriteUser)
        }
    }

    fun getUserFavUsername(username: String): LiveData<FavoriteUser?> = mFavUserDao.getUserFavUsername(username)

    fun getAllFavUser(): LiveData<List<FavoriteUser>> = mFavUserDao.getAllFavUser()
}
