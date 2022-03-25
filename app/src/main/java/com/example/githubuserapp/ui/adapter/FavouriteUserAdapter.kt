package com.example.githubuserapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.local.entity.FavouriteUserEntity
import com.example.githubuserapp.databinding.ItemRowUserBinding

class FavouriteUserAdapter : RecyclerView.Adapter<FavouriteUserAdapter.FavouriteUserViewHolder>() {
    private val listFavouriteUser = ArrayList<FavouriteUserEntity>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setListFavouriteUser(listFavouriteUser: List<FavouriteUserEntity>) {
        this.listFavouriteUser.clear()
        this.listFavouriteUser.addAll(listFavouriteUser)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteUserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FavouriteUserViewHolder,
        position: Int
    ) {
        holder.bind(listFavouriteUser[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listFavouriteUser[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return listFavouriteUser.size
    }

    inner class FavouriteUserViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favouriteUserEntity: FavouriteUserEntity) {
            with(binding) {
                tvItemUsername.text = favouriteUserEntity.username
                Glide.with(itemView.context).load(favouriteUserEntity.avatarUrl).into(imItemPhoto)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(favouriteUserEntity: FavouriteUserEntity)
    }

}