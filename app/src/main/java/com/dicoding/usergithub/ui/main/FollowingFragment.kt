package com.dicoding.usergithub.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.usergithub.adapter.UserAdapter
import com.dicoding.usergithub.data.network.response.User
import com.dicoding.usergithub.databinding.FragmentFollowingBinding
import com.dicoding.usergithub.ui.model.FollowingViewModel

class FollowingFragment : Fragment() {
    private lateinit var followingViewModel: FollowingViewModel
    private var username:String? = null

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        followingViewModel = ViewModelProvider(this)[FollowingViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(ARG_USERNAME)
        followingViewModel.findUserFollowing(username)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUserListFollowing.layoutManager = layoutManager


        followingViewModel.listFollowingUser.observe(viewLifecycleOwner) { user ->
            binding.rvUserListFollowing.adapter = rvUserList(user)
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            showLoading(isLoading)
        }

        followingViewModel.isFailure.observe(viewLifecycleOwner){ error ->
            error?.let{
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater,container, false)
        return binding.root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFollowing.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun rvUserList(user: List<User>?): UserAdapter {
        val listOfUser = user.orEmpty()
        if (listOfUser.isEmpty()) {
            binding.tvNoFollowing.visibility = View.VISIBLE
            binding.rvUserListFollowing.visibility = View.GONE
        } else {
            binding.tvNoFollowing.visibility = View.GONE
            binding.rvUserListFollowing.visibility = View.VISIBLE
        }
        return UserAdapter(listOfUser)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val  ARG_USERNAME = "arg_username"
    }



}

