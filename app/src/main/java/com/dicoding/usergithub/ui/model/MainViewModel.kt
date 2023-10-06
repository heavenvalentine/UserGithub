package com.dicoding.usergithub.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.usergithub.data.network.response.User
import com.dicoding.usergithub.data.network.response.UserGithubResponse
import com.dicoding.usergithub.data.network.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUser = MutableLiveData<List<User>>()
    val listUser: LiveData<List<User>> = _listUser

    private val _isFailure = MutableLiveData<String>()
    val isFailure : LiveData<String> = _isFailure

    init {
        findUser(USERNAME)
    }

    internal fun findUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object: Callback<UserGithubResponse>{
            override fun onResponse(
                call: Call<UserGithubResponse>,
                response: Response<UserGithubResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _listUser.value = (response.body()?.items ?: emptyList()).filterNotNull()
                }
            }

            override fun onFailure(call: Call<UserGithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("Failure", t.message ?: "Unknown error")
                _isFailure.value = "Data loading error."
            }
        })
    }

    companion object{
        private const val USERNAME = "aespaldi"
    }

}