package com.example.githubuserapp.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavouriteUserEntity(
    @PrimaryKey
    @ColumnInfo(name = "username")
    val username: String? = null,

    @ColumnInfo(name = "avatarUrl")
    val avatarUrl: String? = null,

    @ColumnInfo(name = "isFavourite")
    var isFavourite: Boolean? = null
) : Parcelable