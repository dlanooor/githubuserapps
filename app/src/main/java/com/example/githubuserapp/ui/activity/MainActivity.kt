package com.example.githubuserapp.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.githubuserapp.data.remote.pojo.ItemsItem
import com.example.githubuserapp.ui.adapter.ListUserAdapter
import com.example.githubuserapp.ui.viewmodel.MainViewModel
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.other.SettingPreferences
import com.example.githubuserapp.ui.viewmodel.ViewModelFactory

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User's Search"

        val pref = SettingPreferences.getInstance(dataStore)
        mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            MainViewModel::class.java
        )

        mainViewModel.usersData.observe(this) { usersData ->
            showRecyclerList(usersData)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_username).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_username)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                mainViewModel.findUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

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

    private fun showRecyclerList(userData: List<ItemsItem>) {
        binding.apply {
            rvUsers.setHasFixedSize(true)

            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                rvUsers.layoutManager = GridLayoutManager(this@MainActivity, 4)
            else
                rvUsers.layoutManager = GridLayoutManager(this@MainActivity, 2)

            val usersData = ArrayList<ItemsItem>()
            usersData.addAll(userData)
            val listUserAdapter = ListUserAdapter(usersData)
            rvUsers.adapter = listUserAdapter

            listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ItemsItem) {
                    val intentToDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
                    intentToDetail.putExtra("DATA", data)
                    startActivity(intentToDetail)
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.apply {
            if (isLoading) {
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }
    }
}