package com.example.stackoverflowuser.ui.main

import android.content.res.ColorStateList
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import com.example.stackoverflowuser.R
import com.example.stackoverflowuser.constants.AppUntil
import com.example.stackoverflowuser.widget.CircleTransform
import com.example.stackoverflowuser.model.User
import com.squareup.picasso.Picasso

class MainActivityAdapter(private var users: MutableList<User>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {
    var onAdapterListener: OnAdapterListener? = null
    private lateinit var backupList: MutableList<User>
    var bookmarksShowed = false

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
        if (user.lastAccessDate != null) {
            holder.tvLastAccessDate.text =
                AppUntil.getRelativeDateTime(user.lastAccessDate!! * AppUntil.SECOND_MILLIS, false)
        }
        setUpStateBookMark(holder.ivBookMark, user)

        holder.clRoot.setOnClickListener {
            onAdapterListener?.onItemClick(user)
        }

        holder.ivBookMark.setOnClickListener {
            user.isBookmarked = !user.isBookmarked
            setUpStateBookMark(holder.ivBookMark, user)
            onAdapterListener?.onBookmarkClick(user)
            if (user.isBookmarked) {
                scaleAnimation(holder.ivBookMark)
            }
        }

    }

    fun addMoreUsers(moreUsers: MutableList<User>) {
        users.addAll(moreUsers)
        notifyItemRangeInserted(users.size - moreUsers.size, users.size)
    }

    fun showBookmarks() {
        backupList = users.toMutableList()
        users.retainAll { user -> user.isBookmarked }
        notifyDataSetChanged()
        bookmarksShowed = true
    }

    fun back2AllUsers() {
        users = backupList
        notifyDataSetChanged()
        bookmarksShowed = false
    }


    private fun setUpStateBookMark(imageView: ImageView, user: User) {
        ImageViewCompat.setImageTintList(
            imageView,
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    imageView.context,
                    if (user.isBookmarked) R.color.yellow else R.color.grey
                )
            )
        )
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

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val ivAvatar = itemView.findViewById(R.id.item_user_iv_avatar) as ImageView
        val ivBookMark = itemView.findViewById(R.id.item_user_iv_bookmark) as ImageView
        val tvName = itemView.findViewById(R.id.item_user_tv_name) as TextView
        val tvReputation = itemView.findViewById(R.id.item_user_tv_reputation) as TextView
        val tvLocation = itemView.findViewById(R.id.item_user_tv_location) as TextView
        val tvLastAccessDate = itemView.findViewById(R.id.item_user_tv_last_access_date) as TextView
        val clRoot = itemView.findViewById(R.id.item_user_cl_root) as ConstraintLayout

    }

    interface OnAdapterListener {
        fun onItemClick(user: User)
        fun onBookmarkClick(user: User)
    }
}

