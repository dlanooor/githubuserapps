package com.example.githubuserapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.githubuserapp.data.remote.api.ApiConfig
import com.example.githubuserapp.other.SettingPreferences
import com.example.githubuserapp.data.remote.pojo.GithubResponse
import com.example.githubuserapp.data.remote.pojo.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {
    private val _usersData = MutableLiveData<List<ItemsItem>>()
    val usersData: LiveData<List<ItemsItem>> = _usersData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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
                    _usersData.value = response.body()?.items as List<ItemsItem>?
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

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}