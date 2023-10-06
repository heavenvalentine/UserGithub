package com.dicoding.usergithub.data.network.retrofit

import com.dicoding.usergithub.data.network.response.User
import com.dicoding.usergithub.data.network.response.UserDetailResponse
import com.dicoding.usergithub.data.network.response.UserGithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") query: String
    ): Call<UserGithubResponse>

    @GET("users/{username}")
    fun getUserDetails(
        @Path("username") username: String?
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String?
    ): Call<List<User>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String?
    ): Call<List<User>>

}