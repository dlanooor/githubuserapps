package com.example.githubuserapp.data.remote.api

import com.example.githubuserapp.data.remote.pojo.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") username: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<GithubDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserDetailFollowers(
        @Path("username") username: String
    ): Call<List<GithubDetailFollowingFollowersResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserDetailFollowing(
        @Path("username") username: String
    ): Call<List<GithubDetailFollowingFollowersResponseItem>>
}