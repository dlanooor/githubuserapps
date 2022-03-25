package com.example.githubuserapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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
        setContentView(binding.root)
        supportActionBar?.title = "Favourite User"

        //viewModel
        favouriteUserViewModel = obtainViewModel(this@FavouriteUserActivity)
        favouriteUserViewModel.getFavouriteUsers().observe(this) { favouriteUserList ->
            if (favouriteUserList != null) {
                adapter.setListFavouriteUser(favouriteUserList)
            }
        }


        adapter = FavouriteUserAdapter()
        binding.apply {
            rvFavouriteUsers.layoutManager = GridLayoutManager(this@FavouriteUserActivity, 2)
            rvFavouriteUsers.setHasFixedSize(true)
            rvFavouriteUsers.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : FavouriteUserAdapter.OnItemClickCallback {
            override fun onItemClicked(favouriteUserEntity: FavouriteUserEntity) {
                val intentToDetail = Intent(this@FavouriteUserActivity, DetailUserActivity::class.java)
                intentToDetail.putExtra("DATA", favouriteUserEntity.username.toString())
                startActivity(intentToDetail)
            }
        })
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