package com.example.githubuserapp.ui.fragment

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
import com.example.githubuserapp.ui.adapter.ListFollowingFollowersAdapter
import com.example.githubuserapp.data.remote.pojo.GithubDetailFollowingFollowersResponseItem
import com.example.githubuserapp.databinding.FragmentFollowersBinding
import com.example.githubuserapp.databinding.FragmentFollowingBinding
import com.example.githubuserapp.ui.viewmodel.UserFollowingFollowersViewModel

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return binding?.root
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
        val usersFollowing = ArrayList<GithubDetailFollowingFollowersResponseItem>()
        usersFollowing.addAll(userFollowing)

        val listFollowingAdapter = ListFollowingFollowersAdapter(context as Context, usersFollowing)

        binding?.rvUserDetailFollowing?.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 1)
            adapter = listFollowingAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarFollowing?.apply {
            if (isLoading) {
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }
    }

    companion object {
        private const val USERNAME = "username"
    }
}