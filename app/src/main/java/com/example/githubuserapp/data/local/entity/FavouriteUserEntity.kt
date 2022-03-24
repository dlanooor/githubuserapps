package com.example.githubuserapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favouriteUser")
class FavouriteUserEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name = "username")
    val username: String,

    @field:ColumnInfo(name = "avatarUrl")
    val avatarUrl: String,

    @field:ColumnInfo(name = "isFavourite")
    var isFavourite: Boolean
)