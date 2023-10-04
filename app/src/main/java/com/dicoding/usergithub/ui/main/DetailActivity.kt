package com.dicoding.usergithub.ui.main

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.usergithub.data.response.UserDetailResponse
import com.dicoding.usergithub.databinding.ActivityDetailBinding
import com.dicoding.usergithub.ui.model.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)

        if(username!=null){
            detailViewModel.findUser(username)
        }

        detailViewModel.isFailure.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        detailViewModel.detailUser.observe(this) { user ->
            setUserData(user)
        }

    }

    private fun setUserData(user: UserDetailResponse) {
        binding.apply {
            tvName.text = user.name
            tvUsername.text = user.login
            tvFollowersTotal.text = user.followers.toString()
            tvFollowingTotal.text = user.following.toString()

            ivProfile.loadImage(user.avatarUrl)
        }
    }

private fun ImageView.loadImage(avatarUrl: String) {
    Glide.with(this.context)
        .load(avatarUrl)
        .into(this)
}


    companion object {
        const val EXTRA_USERNAME: String = "extra_username"
    }
}
