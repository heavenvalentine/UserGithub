package com.dicoding.usergithub.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.usergithub.adapter.FavUserAdapter
import com.dicoding.usergithub.databinding.ActivityFavoriteBinding
import com.dicoding.usergithub.ui.model.FavoriteViewModel
import com.dicoding.usergithub.ui.model.ViewModelFactory

class FavoriteActivity: AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favViewModel: FavoriteViewModel by viewModels{ ViewModelFactory.getInstance(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favViewModel.getAllFavUser().observe(this){ listOfFavUser ->
            binding.rvUserListFavorite.apply {
                layoutManager = LinearLayoutManager( this@FavoriteActivity)
                adapter = FavUserAdapter(listOfFavUser)
            }

            val noFavoriteUsersTextView = binding.tvNoFavUser
            if (listOfFavUser.isEmpty()) {
                noFavoriteUsersTextView.visibility = View.VISIBLE
            } else {
                noFavoriteUsersTextView.visibility = View.GONE
            }
        }
    }
}