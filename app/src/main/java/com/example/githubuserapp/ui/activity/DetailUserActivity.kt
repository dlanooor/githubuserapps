package com.example.githubuserapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.*
import com.example.githubuserapp.ui.adapter.SectionsPagerAdapter
import com.example.githubuserapp.databinding.ActivityDetailUserBinding
import com.example.githubuserapp.data.remote.pojo.GithubDetailResponse
import com.example.githubuserapp.data.remote.pojo.ItemsItem
import com.example.githubuserapp.ui.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    private val detailViewModel by viewModels<DetailViewModel>()

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favourite -> {
                val i = Intent(this, FavouriteUserActivity::class.java)
                startActivity(i)
                return true
            }
            R.id.menu_settings -> {
                val i = Intent(this, ConfigurationActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }

    private fun setUserDetail(userDetail: GithubDetailResponse?) {
        binding.apply {
            userDetail?.apply {
                Glide.with(this@DetailUserActivity).load(userDetail.avatarUrl)
                    .into(binding.imItemPhotoDetail)

                tvItemNameDetail.text = userDetail.name
                tvItemUsernameDetail.text = userDetail.login

                if (userDetail.company.isNullOrBlank()) {
                    tvItemCompanyDetail.visibility = View.GONE
                }
                else {
                    tvItemCompanyDetail.text = userDetail.company
                }

                if (userDetail.location.isNullOrBlank()) {
                    tvItemLocationDetail.visibility = View.GONE
                }
                else {
                    tvItemLocationDetail.text = userDetail.location
                }

                tvItemRepositoryDetail.text = userDetail.publicRepos.toString()
                tvItemFollowersDetail.text = userDetail.followers.toString()
                tvItemFollowingDetail.text = userDetail.following.toString()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading)
            binding.progressBarDetail.visibility = View.VISIBLE
        else
            binding.progressBarDetail.visibility = View.GONE
    }

    companion object {
        private const val DATA = "DATA"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }
}