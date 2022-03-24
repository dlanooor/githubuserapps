package com.example.githubuserapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapp.data.local.entity.FavouriteUserEntity
import com.example.githubuserapp.databinding.ItemRowUserBinding

class FavouriteUserAdapter : ListAdapter<FavouriteUserEntity, FavouriteUserAdapter.FavouriteUserViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteUserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteUserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class FavouriteUserViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(user: FavouriteUserEntity) {
            binding.apply {
                tvItemUsername.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .into(imItemPhoto)
                // intent belom diimplementasikan
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<FavouriteUserEntity> =
            object : DiffUtil.ItemCallback<FavouriteUserEntity>() {
                override fun areItemsTheSame(
                    oldItem: FavouriteUserEntity,
                    newItem: FavouriteUserEntity
                ): Boolean {
                    return oldItem.username == newItem.username
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: FavouriteUserEntity,
                    newItem: FavouriteUserEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}