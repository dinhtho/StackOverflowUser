package com.example.stackoverflowuser.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.stackoverflowuser.R
import com.example.stackoverflowuser.model.User
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainActivityView {

    private var presenter = MainActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.attach(this)

        presenter.getUsers(1)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }


    override fun updateUserAdapter(users: List<User>) {
        if (recyclerView.adapter is MainActivityAdapter) {
            val movieSearchAdapter = recyclerView.adapter as MainActivityAdapter
            movieSearchAdapter.users = users
            movieSearchAdapter.notifyDataSetChanged()
        } else {
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = MainActivityAdapter(users)
        }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE;
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE;
    }

    override fun onError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

}
