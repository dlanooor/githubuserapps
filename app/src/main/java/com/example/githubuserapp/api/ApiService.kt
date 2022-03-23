package com.example.githubuserapp.api

import com.example.githubuserapp.pojo.*
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