package com.example.stackoverflowuser.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import com.example.stackoverflowuser.R
import com.example.stackoverflowuser.model.User
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainActivityView, View.OnClickListener {
    private var presenter = MainActivityPresenter()
    private var isLoadingMore = false
    private var pastVisiblesItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var currentPage = 1
    private var adapter: MainActivityAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.stackoverflowuser.R.layout.activity_main)

        init()
        presenter.getUsers(currentPage)
    }

    private fun init() {
        presenter.attach(this)
        main_tv_all.setOnClickListener(this)
        main_tv_bookmark.setOnClickListener(this)

        selectAll(true)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    private fun selectAll(isSelected: Boolean) {
        main_tv_all.setBackgroundColor(resources.getColor(if (isSelected) R.color.red else R.color.white))
        main_tv_all.setTextColor(resources.getColor(if (isSelected) R.color.white else R.color.red))
        main_tv_bookmark.setBackgroundColor(resources.getColor(if (!isSelected) R.color.red else R.color.white))
        main_tv_bookmark.setTextColor(resources.getColor(if (!isSelected) R.color.white else R.color.red))
    }

    override fun updateUserAdapter(users: MutableList<User>) {
        if (main_recyclerView.adapter is MainActivityAdapter) {
            val movieSearchAdapter = main_recyclerView.adapter as MainActivityAdapter
            movieSearchAdapter.addMoreUsers(users)
        } else {
            adapter = MainActivityAdapter(users)
            adapter?.onAdapterListener = object : MainActivityAdapter.OnAdapterListener {
                override fun onItemClick(user: User) {
                    presenter.updateBookmark(user)
                }
            }
            linearLayoutManager = LinearLayoutManager(this)
            main_recyclerView.layoutManager = linearLayoutManager
            main_recyclerView.addOnScrollListener(onScrollListener)
            main_recyclerView.adapter = adapter
        }
    }

    override fun showLoading() {
        if (isLoadingMore) {
            main_progressBar_bottom.visibility = View.VISIBLE
        } else {
            main_progressBar.visibility = View.VISIBLE;
        }
    }

    override fun hideLoading() {
        if (isLoadingMore) {
            main_progressBar_bottom.visibility = View.GONE
            isLoadingMore = false
        } else {
            main_progressBar.visibility = View.GONE
        }
    }

    override fun onError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
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

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!isLoadingMore && dy > 0 && !adapter!!.bookmarksShowed) {
                visibleItemCount = linearLayoutManager.childCount
                totalItemCount = linearLayoutManager.itemCount
                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 5) {
                    isLoadingMore = true
                    presenter.getUsers(currentPage++)
                }

            }
        }
    }

}
