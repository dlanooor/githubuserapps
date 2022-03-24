package com.example.githubuserapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.remote.pojo.GithubDetailFollowingFollowersResponseItem

class ListFollowingFollowersAdapter(
    private val context: Context,
    private val listFollowingFollowers: ArrayList<GithubDetailFollowingFollowersResponseItem>
) : RecyclerView.Adapter<ListFollowingFollowersAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row_following_followers, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.tvUsername.text = listFollowingFollowers[position].login
        Glide.with(context).load(listFollowingFollowers[position].avatarUrl).into(holder.imgPhoto)
    }

    override fun getItemCount(): Int = listFollowingFollowers.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.im_item_photo_following_followers)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_item_username_following_followers)
    }
}