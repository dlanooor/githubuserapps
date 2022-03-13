package com.example.githubuserapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.bumptech.glide.Glide
import com.example.githubuserapp.databinding.ActivityDetailUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val data = intent.getParcelableExtra<ItemsItem>(DATA)
        Log.d("Detail Data", data?.login.toString())

        findUserDetail(data?.login.toString().trim())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    private fun findUserDetail(username : String) {
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<GithubDetailResponse> {
            override fun onResponse(
                call: Call<GithubDetailResponse>,
                response: Response<GithubDetailResponse>
            ) {
                if (response.isSuccessful) {
                    setUserDetail(response.body())
                }
                else
                    Log.e(TAG, "onFailure : ${response.message()}")
            }

            override fun onFailure(call: Call<GithubDetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    private fun setUserDetail(userDetail : GithubDetailResponse?) {
        Glide.with(this@DetailUserActivity).load(userDetail?.avatarUrl).into(binding.imItemPhotoDetail)
        binding.tvItemNameDetail.text = userDetail?.name
        binding.tvItemUsernameDetail.text = userDetail?.login
        binding.tvItemCompanyDetail.text = userDetail?.company
        binding.tvItemLocationDetail.text = userDetail?.location
        binding.tvItemRepositoryDetail.text = userDetail?.publicRepos.toString()
        binding.tvItemFollowersDetail.text = userDetail?.followers.toString()
        binding.tvItemFollowingDetail.text = userDetail?.following.toString()
    }
}