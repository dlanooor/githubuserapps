package com.example.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.githubuserapp.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        private const val DATA = "DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "User Detail"

        val data = intent.getParcelableExtra<User>(DATA)
        Log.d("Detail Data", data?.name.toString())

        binding.tvItemNameDetail.text = data?.name.toString().trim()
        binding.tvItemUsernameDetail.text = data?.username.toString().trim()
        binding.tvItemCompanyDetail.text = data?.company.toString().trim()
        binding.tvItemLocationDetail.text = data?.location.toString().trim()
        binding.tvItemRepositoryDetail.text = data?.repository.toString().trim()
        binding.tvItemFollowersDetail.text = data?.followers.toString().trim()
        binding.tvItemFollowingDetail.text = data?.following.toString().trim()

        println(data?.followers)

        if (data != null) {
            binding.imItemPhotoDetail.setImageResource(data.photo)
        }
    }
}