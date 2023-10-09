package com.dicoding.usergithub.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.usergithub.R
import com.dicoding.usergithub.data.local.entity.FavoriteUser
import com.dicoding.usergithub.databinding.ItemUserBinding
import com.dicoding.usergithub.ui.main.DetailActivity

class FavUserAdapter(private val listAllUser: List<FavoriteUser>): RecyclerView.Adapter<FavUserAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listUser = listAllUser[position]
        holder.apply {
            binding.apply {
                tvItemUsername.text = listUser.username

                Glide.with(itemView.context)
                    .load(listUser.avatarUrl)
                    .error(R.drawable.icon_person)
                    .into(ivUser)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USERNAME, listUser.username)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int = listAllUser.size

}