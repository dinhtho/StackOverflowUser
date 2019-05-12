package com.example.stackoverflowuser.ui.main

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v4.widget.ImageViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.stackoverflowuser.custom.CircleTransform
import com.example.stackoverflowuser.R
import com.example.stackoverflowuser.model.User
import com.squareup.picasso.Picasso
import kotlinx.coroutines.internal.sanitize

class MainActivityAdapter(var users: MutableList<User>) : RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users.get(position)
        Picasso.with(holder.ivAvatar.context).load(user.profileImage).transform(CircleTransform())
            .into(holder.ivAvatar)
        holder.tvName.text = user.displayName
        holder.tvReputation.text = user.reputation.toString()
        holder.tvLocation.text = user.location
        holder.tvLastAccessDate.text = user.lastAccessDate.toString()
        setUpStateBookMark(holder.ivBookMark, user)

        holder.rlRoot.setOnClickListener {
            user.isBookMarked = !user.isBookMarked
            setUpStateBookMark(holder.ivBookMark, user)
            if (user.isBookMarked) {
                scaleAnimation(holder.ivBookMark)
            }
        }

    }

    private fun setUpStateBookMark(imageView: ImageView, user: User) {
        if (user.isBookMarked) {
            ImageViewCompat.setImageTintList(
                imageView,
                ColorStateList.valueOf(ContextCompat.getColor(imageView.context, R.color.yellow))
            )
        } else {
            ImageViewCompat.setImageTintList(
                imageView,
                ColorStateList.valueOf(ContextCompat.getColor(imageView.context, R.color.grey))
            )
        }
    }

    private fun scaleAnimation(view: View) {
        val animation = ScaleAnimation(
            1f, 2f, 1f, 2f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        animation.fillAfter = true
        animation.duration = 500
        animation.repeatCount = 1
        animation.repeatMode = Animation.REVERSE
        view.startAnimation(animation)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar = itemView.findViewById(R.id.item_user_iv_avatar) as ImageView
        val ivBookMark = itemView.findViewById(R.id.item_user_iv_bookmark) as ImageView
        val tvName = itemView.findViewById(R.id.item_user_tv_name) as TextView
        val tvReputation = itemView.findViewById(R.id.item_user_tv_reputation) as TextView
        val tvLocation = itemView.findViewById(R.id.item_user_tv_location) as TextView
        val tvLastAccessDate = itemView.findViewById(R.id.item_user_tv_last_access_date) as TextView
        val rlRoot = itemView.findViewById(R.id.item_user_rl_root) as RelativeLayout

    }


}