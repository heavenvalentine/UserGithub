package com.dicoding.usergithub.data.network.response

import com.google.gson.annotations.SerializedName

data class UserGithubResponse(
    @field:SerializedName("total_count")
    val totalCount: Int?,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean?,

    @field:SerializedName("items")
    val items: List<User?>?
    )
