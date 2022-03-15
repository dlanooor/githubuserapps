package com.example.githubuserapp.fragment

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.ListFollowingAdapter
import com.example.githubuserapp.api.ApiConfig
import com.example.githubuserapp.databinding.FragmentHomeBinding
import com.example.githubuserapp.pojo.GithubDetailFollowingResponse
import com.example.githubuserapp.pojo.GithubDetailFollowingResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvLabel : TextView = view.findViewById(R.id.section_label)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(ARG_USER_USERNAME, "dlanooor")

        findUserFollowing(username as String)

        tvLabel.text = getString(R.string.content_tab_text, index)
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USER_USERNAME = "user_username"
    }

    private fun findUserFollowing(username : String) {
        val client = ApiConfig.getApiService().getUserDetailFollowing(username)
        client.enqueue(object : Callback<GithubDetailFollowingResponse> {
            override fun onResponse(
                call: Call<GithubDetailFollowingResponse>,
                response: Response<GithubDetailFollowingResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    showFollowingRecyclerList(responseBody?.githubDetailFollowingResponse as List<GithubDetailFollowingResponseItem>)
                }
                else
                    Log.e(ContentValues.TAG, "onFailure : ${response.message()}")
            }

            override fun onFailure(call: Call<GithubDetailFollowingResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure : ${t.message}")
            }
        })
    }

    private fun showFollowingRecyclerList(userFollowing : List<GithubDetailFollowingResponseItem>) {
        binding.rvUserFollowingFollowersDetail.setHasFixedSize(true)
        binding.rvUserFollowingFollowersDetail.layoutManager = GridLayoutManager(context, 1)

        val usersFollowing = ArrayList<GithubDetailFollowingResponseItem>()
        usersFollowing.addAll(userFollowing)

        val listFollowingAdapter = ListFollowingAdapter(context as Context, usersFollowing)
        binding.rvUserFollowingFollowersDetail.adapter = listFollowingAdapter
    }
}