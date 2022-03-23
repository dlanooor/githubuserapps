package com.example.githubuserapp.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ListFollowingFollowersAdapter
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.pojo.GithubDetailFollowingFollowersResponseItem
import com.example.githubuserapp.viewmodel.UserFollowingFollowersViewModel

class FollowersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(USERNAME)
        val userFollowingFollowersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[UserFollowingFollowersViewModel::class.java]
        userFollowingFollowersViewModel.findUserFollowers(username.toString())
        userFollowingFollowersViewModel.userFollowers.observe(viewLifecycleOwner) { userFollowers ->
            showFollowingRecyclerList(userFollowers)
        }
        userFollowingFollowersViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showFollowingRecyclerList(userFollowers: List<GithubDetailFollowingFollowersResponseItem>) {
        val rvUserDetailFollowers =
            requireView().findViewById(R.id.rv_user_detail_followers) as RecyclerView
        rvUserDetailFollowers.setHasFixedSize(true)
        rvUserDetailFollowers.layoutManager = GridLayoutManager(context, 1)

        val usersFollowers = ArrayList<GithubDetailFollowingFollowersResponseItem>()
        usersFollowers.addAll(userFollowers)

        val listFollowersAdapter = ListFollowingFollowersAdapter(context as Context, usersFollowers)
        rvUserDetailFollowers.adapter = listFollowersAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        val progressBar =
            requireView().findViewById(R.id.progressBarFollowers) as ProgressBar
        if (isLoading)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }

    companion object {
        private const val USERNAME = "username"
    }
}