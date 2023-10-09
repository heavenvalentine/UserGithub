package com.dicoding.usergithub.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.usergithub.ui.main.FollowerFragment
import com.dicoding.usergithub.ui.main.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity, private val username: String?): FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> {
                fragment = FollowerFragment()
                username?.let {
                    (fragment as FollowerFragment).arguments = Bundle().apply {
                        putString(FollowerFragment.ARG_USERNAME, it)
                    }
                }
            }
            1 -> {
                fragment = FollowingFragment()
                username?.let {
                    fragment.arguments = Bundle().apply {
                        putString(FollowingFragment.ARG_USERNAME, it)
                    }
                }
            }
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int = 2

}