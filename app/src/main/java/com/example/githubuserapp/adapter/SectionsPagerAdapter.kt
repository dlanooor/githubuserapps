package com.example.githubuserapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserapp.fragment.HomeFragment

class SectionsPagerAdapter(activity: AppCompatActivity, username : String) : FragmentStateAdapter(activity) {
    private val username = username
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment = HomeFragment()
        fragment.arguments = Bundle().apply {
            putInt(HomeFragment.ARG_SECTION_NUMBER, position + 1)
            putString(HomeFragment.ARG_USER_USERNAME, username)
        }
        return fragment
    }

}