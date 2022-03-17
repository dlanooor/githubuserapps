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
import com.example.githubuserapp.pojo.GithubDetailFollowingFollowersResponseItem
import com.example.githubuserapp.viewmodel.UserFollowingFollowersViewModel

class FollowingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(USERNAME)

        val userFollowingFollowersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[UserFollowingFollowersViewModel::class.java]
        userFollowingFollowersViewModel.findUserFollowing(username.toString())
        userFollowingFollowersViewModel.userFollowing.observe(viewLifecycleOwner) { userFollowing ->
            showFollowingRecyclerList(userFollowing)
        }
        userFollowingFollowersViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showFollowingRecyclerList(userFollowing: List<GithubDetailFollowingFollowersResponseItem>) {
        val rvUserDetailFollowing =
            requireView().findViewById(R.id.rv_user_detail_following) as RecyclerView
        rvUserDetailFollowing.setHasFixedSize(true)
        rvUserDetailFollowing.layoutManager = GridLayoutManager(context, 1)

        val usersFollowing = ArrayList<GithubDetailFollowingFollowersResponseItem>()
        usersFollowing.addAll(userFollowing)

        val listFollowingAdapter = ListFollowingFollowersAdapter(context as Context, usersFollowing)
        rvUserDetailFollowing.adapter = listFollowingAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        val progressBar =
            requireView().findViewById(R.id.progressBarFollowing) as ProgressBar
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        }
        else {
            progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val USERNAME = "username"
    }
}