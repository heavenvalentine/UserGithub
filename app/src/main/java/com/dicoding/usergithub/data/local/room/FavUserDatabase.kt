package com.dicoding.usergithub.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.usergithub.data.local.entity.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 1)

abstract class FavUserDatabase: RoomDatabase(){
    abstract fun FavUserDao(): FavUserDao

    companion object{
        @Volatile
        private var INSTANCE: FavUserDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavUserDatabase{
            if (INSTANCE == null){
                synchronized(FavUserDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavUserDatabase::class.java, "dbFavUser")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE as FavUserDatabase
        }
    }


}