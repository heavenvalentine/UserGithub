package com.dicoding.usergithub.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.usergithub.data.response.User
import com.dicoding.usergithub.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowerUser = MutableLiveData<List<User>>()
    val listFollowerUser: LiveData<List<User>> = _listFollowerUser

    private val _isFailure = MutableLiveData<String>()
    val isFailure : LiveData<String> = _isFailure

    fun findUserFollower(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object: Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    if(response.body() == null)
                    {
                        Log.d("Failure", "no data")
                    }
                    else
                    {
                        Log.d("Success", "data found")
                    }
                    _listFollowerUser.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                Log.d("Failure", t.message ?: "Unknown error")
                _isFailure.value = "Data loading error."
            }
        })
    }
}
