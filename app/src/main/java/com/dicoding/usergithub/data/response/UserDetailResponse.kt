package com.dicoding.usergithub.data.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(

@field:SerializedName("following_url")
val followingUrl: String,

@field:SerializedName("following")
val following: Int,

@field:SerializedName("login")
val login: String,

@field:SerializedName("followers_url")
val followersUrl: String,

@field:SerializedName("followers")
val followers: Int,

@field:SerializedName("name")
val name: String,

@field:SerializedName("avatar_url")
val avatarUrl: String,

@field:SerializedName("id")
val id: Int,
)
