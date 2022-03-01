package com.example.githubuserapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView

class DetailUser : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        supportActionBar!!.title = "User Detail"

        val data = intent.getParcelableExtra<User>("DATA")
        Log.d("Detail Data", data?.name.toString())

        val ivPhotoDetail: ImageView = findViewById(R.id.im_item_photo_detail)
        val tvNameDetail: TextView = findViewById(R.id.tv_item_name_detail)
        val tvUsernameDetail: TextView = findViewById(R.id.tv_item_username_detail)
        val tvCompanyDetail: TextView = findViewById(R.id.tv_item_company_detail)
        val tvLocationDetail: TextView = findViewById(R.id.tv_item_location_detail)
        val tvRepositoryDetail: TextView = findViewById(R.id.tv_item_repository_detail)
        val tvFollowersDetail: TextView = findViewById(R.id.tv_item_followers_detail)
        val tvFollowingDetail: TextView = findViewById(R.id.tv_item_following_detail)

        tvNameDetail.text = data?.name.toString().trim()
        tvUsernameDetail.text = data?.username.toString().trim()
        tvCompanyDetail.text = data?.company.toString().trim()
        tvLocationDetail.text = data?.location.toString().trim()
        tvRepositoryDetail.text = data?.repository.toString().trim()
        tvFollowersDetail.text = data?.followers.toString().trim()
        tvFollowingDetail.text = data?.following.toString().trim()

        println(data?.followers)

        ivPhotoDetail.setImageResource(data?.photo!!)
    }
}