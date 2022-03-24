package com.example.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuserapp.data.local.entity.FavouriteUserEntity

@Dao
interface FavouriteUserDao {
    @Query("SELECT * FROM favouriteUser WHERE isFavourite = 1")
    fun getFavouriteUser(): LiveData<List<FavouriteUserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavouriteUser(favouriteUser: List<FavouriteUserEntity>)

    @Update
    fun updateFavouriteUser(favouriteUser: FavouriteUserEntity)

    @Query("DELETE FROM favouriteUser WHERE isFavourite = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM favouriteUser WHERE username = :username AND isFavourite = 1)")
    fun isUserFavourited(username: String): Boolean
}