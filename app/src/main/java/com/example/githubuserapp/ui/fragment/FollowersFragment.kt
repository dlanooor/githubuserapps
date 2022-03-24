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
import com.example.githubuserapp.ui.viewmodel.UserFollowingFollowersViewModel

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding?.root
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
        val usersFollowers = ArrayList<GithubDetailFollowingFollowersResponseItem>()
        usersFollowers.addAll(userFollowers)

        val listFollowersAdapter = ListFollowingFollowersAdapter(context as Context, usersFollowers)

        binding?.rvUserDetailFollowers?.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 1)
            adapter = listFollowersAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarFollowers?.apply {
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