package com.example.stackoverflowuser.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.stackoverflowuser.R
import com.example.stackoverflowuser.base.adapter.BaseAdapter
import com.example.stackoverflowuser.base.adapter.BaseHolder
import com.example.stackoverflowuser.model.User
import com.example.stackoverflowuser.util.load
import com.example.stackoverflowuser.util.value
import kotlinx.android.synthetic.main.item_user.*
import java.util.zip.Inflater

class MainActivityAdapter(inflater: LayoutInflater) :
    BaseAdapter<User, BaseHolder<User>>(inflater) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<User> {
        return object : BaseHolder<User>(inflater.inflate(R.layout.item_user, parent, false)) {
            override fun bind(data: User, position: Int) {
                item_user_tv_name.text = data.displayName
                item_user_tv_reputation.text = data.reputation.toString()
                item_user_tv_location.text = data.location
                item_user_iv_avatar.load(data.profileImage.value())
            }
        }
    }
}

