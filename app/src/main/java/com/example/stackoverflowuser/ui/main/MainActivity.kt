package com.example.stackoverflowuser.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.example.stackoverflowuser.model.User
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainActivityView {

    private var presenter = MainActivityPresenter()
    private var isLoadingMore = false
    private var pastVisiblesItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var currentPage = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.stackoverflowuser.R.layout.activity_main)

        presenter.attach(this)

        presenter.getUsers(currentPage)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }


    override fun updateUserAdapter(users: MutableList<User>) {
        if (recyclerView.adapter is MainActivityAdapter) {
            val movieSearchAdapter = recyclerView.adapter as MainActivityAdapter
            movieSearchAdapter.users.addAll(users)
            movieSearchAdapter.notifyDataSetChanged()
        } else {
            linearLayoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.addOnScrollListener(onScrollListener)
            recyclerView.adapter = MainActivityAdapter(users)
        }
    }

    override fun showLoading() {
        if (isLoadingMore) {
            progressBar_bottom.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.VISIBLE;
        }
    }

    override fun hideLoading() {
        if (isLoadingMore) {
            progressBar_bottom.visibility = View.GONE
            isLoadingMore = false
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun onError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!isLoadingMore && dy > 0) {
                visibleItemCount = linearLayoutManager.childCount
                totalItemCount = linearLayoutManager.itemCount
                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 10) {
                    isLoadingMore = true
                    presenter.getUsers(currentPage++)
                }

            }
        }
    }

}
