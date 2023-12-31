package com.dicoding.usergithub.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.usergithub.adapter.FavUserAdapter
import com.dicoding.usergithub.databinding.ActivityFavoriteBinding
import com.dicoding.usergithub.ui.model.FavoriteViewModel

class FavoriteActivity: AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favViewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favViewModel.getAllFavUser().observe(this) { listOfFavUser ->
            binding.apply {
                rvUserListFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
                rvUserListFavorite.adapter = FavUserAdapter(listOfFavUser)
            }

            val noFavoriteUsersTextView = binding.tvNoFavUser
            noFavoriteUsersTextView.visibility = if (listOfFavUser.isEmpty()) View.VISIBLE else View.GONE
        }
    }
}