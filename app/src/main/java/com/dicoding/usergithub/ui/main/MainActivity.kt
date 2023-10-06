package com.dicoding.usergithub.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.usergithub.R
import com.dicoding.usergithub.adapter.UserAdapter
import com.dicoding.usergithub.data.response.User
import com.dicoding.usergithub.databinding.ActivityMainBinding
import com.dicoding.usergithub.ui.model.MainViewModel
import com.dicoding.usergithub.ui.theme.SettingPreferences
import com.dicoding.usergithub.ui.theme.ThemeActivity
import com.dicoding.usergithub.ui.theme.ThemeViewModel
import com.dicoding.usergithub.ui.theme.ThemeViewModelFactory
import com.dicoding.usergithub.ui.theme.dataStore

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

        mainViewModel.isFailure.observe(this){
            Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
        }

        binding.searchBar.setOnMenuItemClickListener{ itemMenu ->
            when(itemMenu.itemId) {
                R.id.favorite_menu -> {
                    val intent = Intent(this,FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.theme_menu -> {
                    val intent = Intent(this, ThemeActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> {
                    false
                }
            }
        }


        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
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