package com.dicoding.usergithub.ui.favorite.executors

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AppExecutors private constructor() {
    val diskIO: ExecutorService = Executors.newSingleThreadExecutor()

    companion object {
        private var instance: AppExecutors? = null

        fun getInstance(): AppExecutors {
            if (instance == null) {
                synchronized(AppExecutors::class.java) {
                    instance = AppExecutors()
                }
            }
            return instance!!
        }
    }
}
