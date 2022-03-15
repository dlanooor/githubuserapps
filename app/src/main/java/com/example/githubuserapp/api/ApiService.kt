package com.example.githubuserapp.api

import com.example.githubuserapp.pojo.GithubDetailFollowersResponse
import com.example.githubuserapp.pojo.GithubDetailFollowingResponse
import com.example.githubuserapp.pojo.GithubDetailResponse
import com.example.githubuserapp.pojo.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") username: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<GithubDetailResponse>

    @GET("users/{username}/followers")
    fun getUserDetailFollowers(
        @Path("username") username: String
    ): Call<GithubDetailFollowersResponse>

    @GET("users/{username}/following")
    fun getUserDetailFollowing(
        @Path("username") username: String
    ): Call<GithubDetailFollowingResponse>
}