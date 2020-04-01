package com.example.stackoverflowuser.ui.main


import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackoverflowuser.R
import com.example.stackoverflowuser.base.BaseActivity
import com.example.stackoverflowuser.util.value
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainActivityViewModel>() {

    override fun getClassViewModel(): Class<MainActivityViewModel> {
        return MainActivityViewModel::class.java
    }

    override val layoutId: Int = R.layout.activity_main

    private var mAdapter: MainActivityAdapter? = null
    override fun init() {

        rvUsers.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            mAdapter = MainActivityAdapter(layoutInflater)
            adapter = mAdapter
        }

        getViewModel().users().observe(this, Observer {
            mAdapter?.setDataSource(it.items.value())
        })

        getViewModel().getUsers(1)
    }

}
