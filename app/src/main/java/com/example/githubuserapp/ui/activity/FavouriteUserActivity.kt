package com.example.githubuserapp.ui.activity

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.data.local.entity.FavouriteUserEntity
import com.example.githubuserapp.data.remote.pojo.ItemsItem
import com.example.githubuserapp.databinding.ActivityFavouriteUserBinding
import com.example.githubuserapp.ui.adapter.ListUserAdapter
import com.example.githubuserapp.ui.viewmodel.FavouriteUserViewModel
import com.example.githubuserapp.ui.viewmodel.ViewModelRoomFactory

class FavouriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteUserBinding
    private var isFavourite = false
    private var favouriteUser: FavouriteUserEntity? = null

    private lateinit var favouriteUserViewModel: FavouriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Favourite User"
        favouriteUserViewModel = obtainViewModel(this@FavouriteUserActivity)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        menu.findItem(R.id.search_username).isVisible = false
        menu.findItem(R.id.menu_favourite).isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> {
                val i = Intent(this, ConfigurationActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavouriteUserViewModel {
        val factory = ViewModelRoomFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavouriteUserViewModel::class.java)
    }

    private fun showRecyclerList(userData: List<ItemsItem>) {
        binding.apply {
            rvFavouriteUsers.setHasFixedSize(true)

            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                rvFavouriteUsers.layoutManager = GridLayoutManager(this@FavouriteUserActivity, 4)
            else
                rvFavouriteUsers.layoutManager = GridLayoutManager(this@FavouriteUserActivity, 2)

            val usersData = ArrayList<ItemsItem>()
            usersData.addAll(userData)
            val listUserAdapter = ListUserAdapter(usersData)
            rvFavouriteUsers.adapter = listUserAdapter

            listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ItemsItem) {
                    val intentToDetail =
                        Intent(this@FavouriteUserActivity, DetailUserActivity::class.java)
                    intentToDetail.putExtra("DATA", data)
                    startActivity(intentToDetail)
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFavourite.apply {
            if (isLoading) {
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }
    }
}