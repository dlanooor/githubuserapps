package com.example.githubuserapp.ui.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.remote.api.ApiConfig
import com.example.githubuserapp.data.remote.pojo.GithubDetailFollowingFollowersResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFollowingFollowersViewModel : ViewModel() {
    private val _userFollowing = MutableLiveData<List<GithubDetailFollowingFollowersResponseItem>>()
    val userFollowing: LiveData<List<GithubDetailFollowingFollowersResponseItem>> = _userFollowing

    private val _userFollowers = MutableLiveData<List<GithubDetailFollowingFollowersResponseItem>>()
    val userFollowers: LiveData<List<GithubDetailFollowingFollowersResponseItem>> = _userFollowers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findUserFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetailFollowing(username)
        client.enqueue(object : Callback<List<GithubDetailFollowingFollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<GithubDetailFollowingFollowersResponseItem>>,
                response: Response<List<GithubDetailFollowingFollowersResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userFollowing.value =
                        response.body() as List<GithubDetailFollowingFollowersResponseItem>
                } else
                    Log.e(ContentValues.TAG, "onFailure : ${response.message()}")
            }

            override fun onFailure(
                call: Call<List<GithubDetailFollowingFollowersResponseItem>>,
                t: Throwable
            ) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun findUserFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetailFollowers(username)
        client.enqueue(object : Callback<List<GithubDetailFollowingFollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<GithubDetailFollowingFollowersResponseItem>>,
                response: Response<List<GithubDetailFollowingFollowersResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userFollowers.value =
                        response.body() as List<GithubDetailFollowingFollowersResponseItem>
                } else
                    Log.e(ContentValues.TAG, "onFailure : ${response.message()}")
            }

            override fun onFailure(
                call: Call<List<GithubDetailFollowingFollowersResponseItem>>,
                t: Throwable
            ) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure : ${t.message}")
            }
        })
    }
}