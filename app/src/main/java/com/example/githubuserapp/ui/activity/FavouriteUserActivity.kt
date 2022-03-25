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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.data.local.entity.FavouriteUserEntity
import com.example.githubuserapp.data.remote.pojo.ItemsItem
import com.example.githubuserapp.databinding.ActivityFavouriteUserBinding
import com.example.githubuserapp.ui.adapter.FavouriteUserAdapter
import com.example.githubuserapp.ui.adapter.ListUserAdapter
import com.example.githubuserapp.ui.viewmodel.FavouriteUserViewModel
import com.example.githubuserapp.ui.viewmodel.ViewModelRoomFactory

class FavouriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteUserBinding
    private lateinit var favouriteUserViewModel: FavouriteUserViewModel

    private lateinit var adapter: FavouriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.title = "Favourite User"

        //viewModel
        favouriteUserViewModel = obtainViewModel(this@FavouriteUserActivity)
        favouriteUserViewModel.getFavouriteUsers().observe(this, { favouriteUserList ->
            if (favouriteUserList != null) {
                println("not null")
                adapter.setListFavouriteUser(favouriteUserList)
                println()
            }
            else {
                println("null")
            }
        })

        adapter = FavouriteUserAdapter()
        binding.apply {
            rvFavouriteUsers.layoutManager = LinearLayoutManager(this@FavouriteUserActivity)
            rvFavouriteUsers.setHasFixedSize(true)
            rvFavouriteUsers.adapter = adapter
        }
        println(adapter.itemCount)

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
}