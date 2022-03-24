package com.example.githubuserapp.other

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuserapp.data.local.entity.FavouriteUserEntity

class FavouriteUserDiffCallback(private val mOldFavouriteUserList: List<FavouriteUserEntity>, private val mNewFavouriteUserList: List<FavouriteUserEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavouriteUserList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavouriteUserList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavouriteUserList[oldItemPosition].username == mNewFavouriteUserList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavouriteUser = mOldFavouriteUserList[oldItemPosition]
        val newFavouriteUser = mNewFavouriteUserList[newItemPosition]
        return oldFavouriteUser.isFavourite == newFavouriteUser.isFavourite && oldFavouriteUser.username == newFavouriteUser.username && oldFavouriteUser.avatarUrl == newFavouriteUser.username
    }
}