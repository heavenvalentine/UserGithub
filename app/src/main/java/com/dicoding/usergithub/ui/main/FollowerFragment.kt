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
import com.dicoding.usergithub.databinding.FragmentFollowerBinding
import com.dicoding.usergithub.ui.model.FollowerViewModel

class FollowerFragment : Fragment() {
    private lateinit var followerViewModel: FollowerViewModel
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private var username:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        followerViewModel = ViewModelProvider(this)[FollowerViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(ARG_USERNAME)
        followerViewModel.findUserFollower(username)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUserListFollower.layoutManager = layoutManager


        followerViewModel.listFollowerUser.observe(viewLifecycleOwner) { user ->
            binding.rvUserListFollower.adapter = rvUserList(user)
        }

        followerViewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            showLoading(isLoading)
        }

        followerViewModel.isFailure.observe(viewLifecycleOwner){ error ->
            error?.let{
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater,container, false)
        return binding.root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFollower.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun rvUserList(user: List<User>?): UserAdapter {
        val listOfUser = user.orEmpty()
        if (listOfUser.isEmpty()) {
            binding.tvNoFollower.visibility = View.VISIBLE
            binding.rvUserListFollower.visibility = View.GONE
        } else {
            binding.tvNoFollower.visibility = View.GONE
            binding.rvUserListFollower.visibility = View.VISIBLE
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

