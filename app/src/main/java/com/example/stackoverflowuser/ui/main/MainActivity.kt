package com.example.stackoverflowuser.ui.main


import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackoverflowuser.R
import com.example.stackoverflowuser.base.BaseActivity
import com.example.stackoverflowuser.ui.fragment_animation.TestFragmentActivity
import com.example.stackoverflowuser.util.value
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainActivityViewModel>() {
    override val classViewModel: Class<MainActivityViewModel>
        get() = MainActivityViewModel::class.java


    override val layoutId: Int = R.layout.activity_main

    private var mAdapter: MainActivityAdapter? = null
    override fun init() {

        rvUsers.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            mAdapter = MainActivityAdapter(layoutInflater)
            adapter = mAdapter
        }

        tvNext.setOnClickListener {
            startActivity(Intent(this, TestFragmentActivity::class.java))
        }

        getViewModel().users().observe(this, Observer {
            mAdapter?.setDataSource(it.items.value())
        })

        getViewModel().getUsers(1)
    }

}
