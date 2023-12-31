package com.dicoding.usergithub.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.usergithub.R
import com.dicoding.usergithub.data.network.response.User
import com.dicoding.usergithub.databinding.ItemUserBinding
import com.dicoding.usergithub.ui.main.DetailActivity

class UserAdapter(private val listOfUser: List<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.apply {
                tvItemUsername.text = user.login

                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .error(R.drawable.icon_person)
                    .into(ivUser)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listOfUser[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = listOfUser.size
}
