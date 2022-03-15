package com.example.githubuserapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListFollowingAdapter(private val context: Context, private val listFollowing: ArrayList<GithubDetailFollowingResponseItem>)
    : RecyclerView.Adapter<ListFollowingAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_following_followers, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (_, _, _, _, username, _, _, _, _, _, avatarUrl, _, _, _, _, _, _, _) = listFollowing[position]
        holder.tvUsername.text = username
        Glide.with(context).load(avatarUrl).into(holder.imgPhoto)
    }

    override fun getItemCount(): Int = listFollowing.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.im_item_photo_following_followers)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_item_username_following_followers)
    }
}