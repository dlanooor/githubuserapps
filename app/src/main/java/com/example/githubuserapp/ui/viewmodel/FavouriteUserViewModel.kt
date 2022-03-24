package com.example.githubuserapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.local.FavouriteUserRepository
import com.example.githubuserapp.data.local.entity.FavouriteUserEntity
import com.example.githubuserapp.data.remote.pojo.ItemsItem

class FavouriteUserViewModel(application: Application) : ViewModel() {
    private val mFavouriteUserRepository: FavouriteUserRepository = FavouriteUserRepository(application)

    fun insert(favouriteUser: FavouriteUserEntity) {
        mFavouriteUserRepository.insert(favouriteUser)
    }

    fun update(favouriteUser: FavouriteUserEntity) {
        mFavouriteUserRepository.update(favouriteUser)
    }

    fun delete(favouriteUser: FavouriteUserEntity) {
        mFavouriteUserRepository.delete(favouriteUser)
    }

    fun getFavouriteUsers(): LiveData<List<FavouriteUserEntity>> = mFavouriteUserRepository.getFavouriteUsers()
}