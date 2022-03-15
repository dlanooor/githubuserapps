package com.example.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.api.ApiConfig
import com.example.githubuserapp.pojo.GithubResponse
import com.example.githubuserapp.pojo.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _usersData = MutableLiveData<ArrayList<ItemsItem>>()
    val usersData : LiveData<ArrayList<ItemsItem>> = _usersData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun findUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _usersData.value = response.body()?.items as ArrayList<ItemsItem>?
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}