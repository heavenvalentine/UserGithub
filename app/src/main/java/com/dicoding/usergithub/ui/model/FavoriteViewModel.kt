package com.dicoding.usergithub.ui.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.usergithub.data.local.entity.FavoriteUser
import com.dicoding.usergithub.ui.favorite.repository.FavUserRepository

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private val repository: FavUserRepository = FavUserRepository(application)

    fun insertUserFav(favoriteUser: FavoriteUser) = repository.insert(favoriteUser)
    fun deleteUserFav(favoriteUser: FavoriteUser) = repository.delete(favoriteUser)

    fun getAllFavUser(): LiveData<List<FavoriteUser>> = repository.getAllFavUser()
    fun getUserClickedFavUsername(username: String): LiveData<FavoriteUser?> {
       return repository.getUserFavUsername(username)
    }





}