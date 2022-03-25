package com.example.githubuserapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.*
import com.example.githubuserapp.data.local.entity.FavouriteUserEntity
import com.example.githubuserapp.ui.adapter.SectionsPagerAdapter
import com.example.githubuserapp.databinding.ActivityDetailUserBinding
import com.example.githubuserapp.data.remote.pojo.GithubDetailResponse
import com.example.githubuserapp.data.remote.pojo.ItemsItem
import com.example.githubuserapp.ui.viewmodel.DetailViewModel
import com.example.githubuserapp.ui.viewmodel.ViewModelRoomFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private var _activityDetailUserBinding: ActivityDetailUserBinding? = null
    private val binding get() = _activityDetailUserBinding
    private lateinit var detailViewModel : DetailViewModel

    private var isFavourite = false
    private var favouriteUser: FavouriteUserEntity? = null

    private lateinit var username: String
    private lateinit var avatarUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "User Detail"

        val data = intent.getParcelableExtra<ItemsItem>(DATA)
        Log.d("Detail Data", data?.login.toString())

        // sectionPager
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = data?.login.toString().trim()
        val viewPager: ViewPager2 = binding!!.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding?.tabs!!
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        favouriteUser = intent.getParcelableExtra(EXTRA_USER)
        isFavourite = true


        // viewModel
        detailViewModel = obtainViewModel(this@DetailUserActivity)
        detailViewModel.findUserDetail(data?.login.toString().trim())
        detailViewModel.userDetail.observe(this) { userDetail ->
            setUserDetail(userDetail)
            username = userDetail.login.toString()
            favouriteUser = FavouriteUserEntity(username)
            avatarUrl = userDetail.avatarUrl.toString()
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        // addFavouriteUser
        binding?.ibFavouriteUsersNo?.visibility = View.VISIBLE
        binding?.ibFavouriteUsersYes?.visibility = View.INVISIBLE

        binding?.ibFavouriteUsersNo?.setOnClickListener {
            binding?.ibFavouriteUsersNo?.visibility = View.INVISIBLE
            binding?.ibFavouriteUsersYes?.visibility = View.VISIBLE

            favouriteUser.let { favouriteUser ->
                favouriteUser?.username = username
                favouriteUser?.avatarUrl = avatarUrl
                favouriteUser?.isFavourite = true
            }
            detailViewModel.insert(favouriteUser as FavouriteUserEntity)
            Toast.makeText(this, "Loved", Toast.LENGTH_SHORT). show()
        }

        binding?.ibFavouriteUsersYes?.setOnClickListener {
            binding?.ibFavouriteUsersYes?.visibility = View.INVISIBLE
            binding?.ibFavouriteUsersNo?.visibility = View.VISIBLE
            detailViewModel.delete(favouriteUser as FavouriteUserEntity)
            Toast.makeText(this, "Unloved", Toast.LENGTH_SHORT). show()
        }
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

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailUserBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelRoomFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }

    private fun setUserDetail(userDetail: GithubDetailResponse?) {
        binding?.apply {
            userDetail?.apply {
                Glide.with(this@DetailUserActivity).load(userDetail.avatarUrl)
                    .into(binding?.imItemPhotoDetail!!)

                tvItemNameDetail.text = userDetail.name
                tvItemUsernameDetail.text = userDetail.login

                if (userDetail.company.isNullOrBlank()) {
                    tvItemCompanyDetail.visibility = View.GONE
                } else {
                    tvItemCompanyDetail.text = userDetail.company
                }

                if (userDetail.location.isNullOrBlank()) {
                    tvItemLocationDetail.visibility = View.GONE
                } else {
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
            binding?.progressBarDetail?.visibility = View.VISIBLE
        else
            binding?.progressBarDetail!!.visibility = View.GONE
    }

    companion object {
        private const val DATA = "DATA"
        const val EXTRA_USER = "extra_user"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }
}