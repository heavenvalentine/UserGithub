package com.dicoding.usergithub.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.usergithub.adapter.UserAdapter
import com.dicoding.usergithub.data.response.User
import com.dicoding.usergithub.databinding.ActivityMainBinding
import com.dicoding.usergithub.ui.model.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserList.layoutManager = layoutManager

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        mainViewModel.listUser.observe(this) { user ->
            binding.rvUserList.adapter = rvUserList(user)
        }
        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

    }

    private fun rvUserList(user: List<User>?): UserAdapter {
        val listOfUser = ArrayList<User>()
        user?.let{
            listOfUser.addAll(it)
        }
        return UserAdapter(listOfUser)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}