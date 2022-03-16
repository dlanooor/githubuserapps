package com.example.githubuserapp.api

import com.example.githubuserapp.pojo.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q") username: String
    ): Call<GithubResponse>

    @GET("users/{username}")
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