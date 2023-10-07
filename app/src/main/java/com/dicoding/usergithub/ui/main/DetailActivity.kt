package com.dicoding.usergithub.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.usergithub.R
import com.dicoding.usergithub.adapter.SectionPagerAdapter
import com.dicoding.usergithub.data.local.entity.FavoriteUser
import com.dicoding.usergithub.data.network.response.UserDetailResponse
import com.dicoding.usergithub.databinding.ActivityDetailBinding
import com.dicoding.usergithub.ui.model.DetailViewModel
import com.dicoding.usergithub.ui.model.FavoriteViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private val favViewModel by viewModels<FavoriteViewModel>()
    private var isFav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        Log.d("DetailActivity", "Received username: $username")

        username?.let {
            detailViewModel.findUser(it)
            setupViewModelObservers(it)
        }

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = SectionPagerAdapter(this, username).apply {
            viewPager.adapter = this
        }

        val tabsLayout: TabLayout = binding.follsTabLayout
        TabLayoutMediator(tabsLayout, viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun setupViewModelObservers(username: String) {
        detailViewModel.isFailure.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        detailViewModel.detailUser.observe(this) { user ->
            displayUserDetails(user)

            favViewModel.getUserClickedFavUsername(username).observe(this) { clickedUser ->
                isFav = clickedUser != null
                updateFavoriteButtonState()
            }

            binding.favoriteButton.setOnClickListener {
                isFav = !isFav

                if (isFav) {
                    val userIsFavorite = FavoriteUser(
                        username = username,
                        avatarUrl = user.avatarUrl
                    )
                    favViewModel.insertUserFav(userIsFavorite)
                    Toast.makeText(this, "User Favorited", Toast.LENGTH_SHORT).show()
                } else {
                    favViewModel.getUserClickedFavUsername(username).observe(this) { item ->
                        if (item != null) {
                            favViewModel.deleteUserFav(item)
                            Toast.makeText(this, "Successfully Removed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                updateFavoriteButtonState()
            }
        }
    }

    private fun updateFavoriteButtonState() {
        if (isFav) {
            binding.favoriteButton.setImageResource(R.drawable.icon_full_fav)
        } else {
            binding.favoriteButton.setImageResource(R.drawable.icon_outline_fav)
        }
    }



    private fun displayUserDetails(user: UserDetailResponse) {
        binding.apply {
            tvName.text = user.name ?: "-"
            tvUsername.text = user.login
            tvFollowersTotal.text = user.followers.toString()
            tvFollowingTotal.text = user.following.toString()
            ivProfile.loadImage(user.avatarUrl)
        }


        binding.shareButton.setOnClickListener {
            val linkToGitHubUser = "https://github.com/${user.login}"
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, linkToGitHubUser)
            startActivity(Intent.createChooser(shareIntent, "Share GitHub User Profile"))
        }
    }

    private fun ImageView.loadImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .into(this)
    }

    companion object {
        const val EXTRA_USERNAME: String = "extra_username"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}
