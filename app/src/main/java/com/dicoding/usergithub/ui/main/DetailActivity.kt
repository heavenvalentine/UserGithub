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
import com.dicoding.usergithub.data.response.UserDetailResponse
import com.dicoding.usergithub.databinding.ActivityDetailBinding
import com.dicoding.usergithub.ui.model.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        Log.d("DetailActivity", "Received username: $username")

        if(username!=null) {
            detailViewModel.findUser(username)
        }

        setupViewModelObservers()

        val sectionPagerAdapter = SectionPagerAdapter(this, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabsLayout: TabLayout = binding.follsTabLayout
        TabLayoutMediator(tabsLayout, viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun setupViewModelObservers() {
        detailViewModel.isFailure.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        detailViewModel.detailUser.observe(this) { user ->
            displayUserDetails(user)
        }
    }

    private fun displayUserDetails(user: UserDetailResponse) {
        with(binding) {
            tvName.text = user.name ?: "(No Name)"
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
