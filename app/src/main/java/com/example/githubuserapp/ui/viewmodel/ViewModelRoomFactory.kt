package com.example.githubuserapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelRoomFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelRoomFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelRoomFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelRoomFactory::class.java) {
                    INSTANCE = ViewModelRoomFactory(application)
                }
            }
            return INSTANCE as ViewModelRoomFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(mApplication) as T
        }
        else if (modelClass.isAssignableFrom(FavouriteUserViewModel::class.java)) {
            return FavouriteUserViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}