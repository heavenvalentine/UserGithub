package com.dicoding.usergithub.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.usergithub.data.local.entity.FavoriteUser

@Dao
interface FavUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)
    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM tblUserFav")
    fun getAllFavUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM tblUserFav WHERE username = :username")
    fun getUserFavUsername(username: String): LiveData<FavoriteUser?>


}
