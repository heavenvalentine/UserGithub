package com.dicoding.usergithub.data.retrofit

import com.dicoding.usergithub.data.response.User
import com.dicoding.usergithub.data.response.UserDetailResponse
import com.dicoding.usergithub.data.response.UserGithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Path("id") id: String
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