package com.dicoding.usergithub.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.usergithub.R

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    companion object {
        const val EXTRA_USERNAME: String = "extra_username"
    }
}