package com.example.githubuserapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.*
import com.example.githubuserapp.adapter.SectionsPagerAdapter
import com.example.githubuserapp.databinding.ActivityDetailUserBinding
import com.example.githubuserapp.pojo.GithubDetailResponse
import com.example.githubuserapp.pojo.ItemsItem
import com.example.githubuserapp.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        private const val DATA = "DATA"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "User Detail"

        val data = intent.getParcelableExtra<ItemsItem>(DATA)
        Log.d("Detail Data", data?.login.toString())

        detailViewModel.findUserDetail(data?.login.toString().trim())
        detailViewModel.userDetail.observe(this) { userDetail ->
            setUserDetail(userDetail)
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = data?.login.toString().trim()
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        menu.findItem(R.id.search_username).isVisible = false
        return true
    }

    private fun setUserDetail(userDetail: GithubDetailResponse?) {
        Glide.with(this@DetailUserActivity).load(userDetail?.avatarUrl)
            .into(binding.imItemPhotoDetail)
        binding.tvItemNameDetail.text = userDetail?.name
        binding.tvItemUsernameDetail.text = userDetail?.login

        if (userDetail?.company.isNullOrBlank())
            binding.tvItemCompanyDetail.visibility = View.GONE
        else
            binding.tvItemCompanyDetail.text = userDetail?.company

        if (userDetail?.location.isNullOrBlank())
            binding.tvItemLocationDetail.visibility = View.GONE
        else
            binding.tvItemLocationDetail.text = userDetail?.location

        binding.tvItemRepositoryDetail.text = userDetail?.publicRepos.toString()
        binding.tvItemFollowersDetail.text = userDetail?.followers.toString()
        binding.tvItemFollowingDetail.text = userDetail?.following.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading)
            binding.progressBarDetail.visibility = View.VISIBLE
        else
            binding.progressBarDetail.visibility = View.GONE
    }
}