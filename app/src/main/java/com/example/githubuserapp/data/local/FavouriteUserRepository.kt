package com.example.githubuserapp.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserapp.data.local.entity.FavouriteUserEntity
import com.example.githubuserapp.data.local.room.FavouriteUserDao
import com.example.githubuserapp.data.local.room.FavouriteUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavouriteUserRepository(application: Application) {
    private val mFavouriteUserDao: FavouriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavouriteUserDatabase.getDatabase(application)
        mFavouriteUserDao = db.favouriteUserDao()
    }

    fun getFavouriteUsers(): LiveData<List<FavouriteUserEntity>> =
        mFavouriteUserDao.getFavouriteUser()

    fun insert(favouriteUser: FavouriteUserEntity) {
        executorService.execute { mFavouriteUserDao.insertFavouriteUser(favouriteUser) }
    }

    fun check(username: String) : Boolean = mFavouriteUserDao.checkFavourite(username)

    fun delete(favouriteUser: FavouriteUserEntity) {
        executorService.execute { mFavouriteUserDao.deleteFavouriteUser(favouriteUser) }
    }
}












