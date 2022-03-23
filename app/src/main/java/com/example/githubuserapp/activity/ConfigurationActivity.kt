package com.example.githubuserapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp.databinding.ActivityConfigurationBinding
import com.example.githubuserapp.other.SettingPreferences
import com.example.githubuserapp.viewmodel.ConfigurationViewModel
import com.example.githubuserapp.viewmodel.ViewModelFactory

class ConfigurationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfigurationBinding
    val pref = SettingPreferences.getInstance(dataStore)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "App Configuration"

        val switchTheme =  binding.switchTheme

        val configurationViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ConfigurationViewModel::class.java
        )

        configurationViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            configurationViewModel.saveThemeSetting(isChecked)
        }

    }
}