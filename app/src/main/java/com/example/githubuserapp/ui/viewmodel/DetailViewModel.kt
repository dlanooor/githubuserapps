package com.example.githubuserapp.ui.viewmodel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.githubuserapp.data.local.FavouriteUserRepository
import com.example.githubuserapp.data.local.entity.FavouriteUserEntity
import com.example.githubuserapp.data.remote.api.ApiConfig
import com.example.githubuserapp.data.remote.pojo.GithubDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): ViewModel() {
    private val _userDetail = MutableLiveData<GithubDetailResponse>()
    val userDetail: LiveData<GithubDetailResponse> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

//    private val mFavouriteUserRepository: FavouriteUserRepository = FavouriteUserRepository(application)

//    fun insert(favouriteUser: FavouriteUserEntity) {
//        mFavouriteUserRepository.insert(favouriteUser)
//    }
//
//    fun update(favouriteUser: FavouriteUserEntity) {
//        mFavouriteUserRepository.update(favouriteUser)
//    }
//
//    fun delete(favouriteUser: FavouriteUserEntity) {
//        mFavouriteUserRepository.delete(favouriteUser)
//    }

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