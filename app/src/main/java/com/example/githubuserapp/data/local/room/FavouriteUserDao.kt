package com.example.githubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuserapp.data.local.entity.FavouriteUserEntity

@Dao
interface FavouriteUserDao {
    @Query("SELECT * FROM favouriteuserentity ORDER BY username ASC")
    fun getFavouriteUser(): LiveData<List<FavouriteUserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavouriteUser(favouriteUser: FavouriteUserEntity)

    @Query("SELECT EXISTS(SELECT * FROM favouriteuserentity WHERE username = :userLogin)")
    fun checkFavourite(userLogin: String) : Boolean

    @Delete
    fun deleteFavouriteUser(favouriteUser: FavouriteUserEntity)
}