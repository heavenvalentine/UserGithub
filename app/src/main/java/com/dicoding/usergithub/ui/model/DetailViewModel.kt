package com.dicoding.usergithub.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.usergithub.data.response.UserDetailResponse
import com.dicoding.usergithub.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _detailUser = MutableLiveData<UserDetailResponse>()
    val detailUser: LiveData<UserDetailResponse> = _detailUser

    private val _isFailure = MutableLiveData<String>()
    val isFailure : LiveData<String> = _isFailure

    fun findUser(username: String) {
        val client = ApiConfig.getApiService().getUserDetails(username)
        client.enqueue(object: Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                if(response.isSuccessful) {
                    _detailUser.value = response.body()
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                Log.d("Failure", t.message ?: "Unknown error")
                _isFailure.value = "Data loading error."
            }
        })
    }
}