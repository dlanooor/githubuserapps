package com.example.githubuserapp.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.githubuserapp.api.ApiConfig
import com.example.githubuserapp.other.SettingPreferences
import com.example.githubuserapp.pojo.GithubDetailResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _userDetail = MutableLiveData<GithubDetailResponse>()
    val userDetail: LiveData<GithubDetailResponse> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<GithubDetailResponse> {
            override fun onResponse(
                call: Call<GithubDetailResponse>,
                response: Response<GithubDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                }
                else {
                    Log.e(ContentValues.TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure : ${t.message}")
            }
        })
    }

}