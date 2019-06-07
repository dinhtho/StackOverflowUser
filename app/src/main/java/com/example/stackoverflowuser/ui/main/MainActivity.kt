package com.example.stackoverflowuser.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.stackoverflowuser.R
import com.example.stackoverflowuser.constants.Constants
import com.example.stackoverflowuser.model.Reputation
import com.example.stackoverflowuser.model.User
import com.example.stackoverflowuser.ui.reputation.ReputationActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var isLoadingMore = false
    private var pastVisiblesItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var currentPage = 1
    private var adapter: MainActivityAdapter? = null
    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.stackoverflowuser.R.layout.activity_main)

        init()

        setupRecycleView()

        setupLoading()

        setupShowingError()

        viewModel.getUsers(currentPage)

    }

    private fun setupRecycleView() {
        viewModel.users.observe(this, Observer { users ->
            if (main_recyclerView.adapter == null) {
                adapter = MainActivityAdapter(users)
                adapter?.onAdapterListener = object : MainActivityAdapter.OnAdapterListener {
                    override fun onItemClick(user: User) {
                        startActivity(Intent(this@MainActivity, ReputationActivity::class.java).apply {
                            putExtra(Constants.USER_ID, user.userId)
                        })
                    }

                    override fun onBookmarkClick(user: User) {
                        viewModel.updateBookmark(user)
                    }
                }
                linearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
                main_recyclerView.layoutManager = linearLayoutManager
                main_recyclerView.addOnScrollListener(onScrollListener)
                main_recyclerView.adapter = adapter
            } else {
                adapter?.addMoreUsers(users)
            }
        })
    }

    private fun setupLoading() {
        viewModel.loading.observe(this, Observer { isLoading ->
            if (isLoading) {
                if (isLoadingMore) {
                    main_progressBar_bottom.visibility = View.VISIBLE
                } else {
                    main_progressBar.visibility = View.VISIBLE;
                }
            } else {
                if (isLoadingMore) {
                    main_progressBar_bottom.visibility = View.GONE
                    isLoadingMore = false
                } else {
                    main_progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun setupShowingError() {
        viewModel.error.observe(this, Observer { throwable ->
            Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun init() {
        main_tv_all.setOnClickListener(this)
        main_tv_bookmark.setOnClickListener(this)
        selectAll(true)
        viewModel = ViewModelProviders.of(this).get<MainActivityViewModel>(MainActivityViewModel::class.java)

    }

    private fun selectAll(isSelected: Boolean) {
        main_tv_all.setBackgroundColor(resources.getColor(if (isSelected) R.color.red else R.color.white))
        main_tv_all.setTextColor(resources.getColor(if (isSelected) R.color.white else R.color.red))
        main_tv_bookmark.setBackgroundColor(resources.getColor(if (!isSelected) R.color.red else R.color.white))
        main_tv_bookmark.setTextColor(resources.getColor(if (!isSelected) R.color.white else R.color.red))
    }

    override fun onClick(v: View) {
        if (adapter != null) {
            if (v.id == main_tv_all.id) {
                selectAll(true)
                adapter!!.back2AllUsers()

            } else if (v.id == main_tv_bookmark.id) {
                selectAll(false)
                adapter!!.showBookmarks()
            }
        }

    }

    private val onScrollListener = object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!isLoadingMore && dy > 0 && !adapter!!.bookmarksShowed) {
                visibleItemCount = linearLayoutManager.childCount
                totalItemCount = linearLayoutManager.itemCount
                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 5) {
                    isLoadingMore = true
                    viewModel.getUsers(currentPage++)
                }

            }
        }
    }

}
