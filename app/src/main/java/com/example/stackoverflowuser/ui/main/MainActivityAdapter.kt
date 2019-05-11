package com.example.stackoverflowuser.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.stackoverflowuser.R
import com.example.stackoverflowuser.model.User
import com.squareup.picasso.Picasso

class MainActivityAdapter(var users: List<User>) : RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users.get(position)
        Picasso.with(holder.ivAvatar.context).load(user.profileImage).into(holder.ivAvatar)
        holder.tvName.text = user.displayName
        holder.tvReputation.text = user.reputation.toString()
        holder.tvLocation.text = user.location
        holder.tvLastAccessDate.text = user.lastAccessDate.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar = itemView.findViewById(R.id.item_user_iv_avatar) as ImageView
        val ivBookMark = itemView.findViewById(R.id.item_user_iv_bookmark) as ImageView
        val tvName = itemView.findViewById(R.id.item_user_tv_name) as TextView
        val tvReputation = itemView.findViewById(R.id.item_user_tv_reputation) as TextView
        val tvLocation = itemView.findViewById(R.id.item_user_tv_location) as TextView
        val tvLastAccessDate = itemView.findViewById(R.id.item_user_tv_last_access_date) as TextView

    }

//    private fun getUrlFromResult(movieResult: MovieResult): String {
//        val stringBuilder = StringBuilder(Urls.IMAGE_BASE_URL)
//        stringBuilder.append(Urls.IMAGE_SIZE_HD).append(movieResult.posterPath)
//        return stringBuilder.toString()
//    }
}