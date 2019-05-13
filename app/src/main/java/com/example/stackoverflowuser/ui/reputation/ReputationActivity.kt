package com.example.stackoverflowuser.ui.reputation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.example.stackoverflowuser.R
import com.example.stackoverflowuser.constants.Constants
import com.example.stackoverflowuser.model.Reputation
import kotlinx.android.synthetic.main.activity_reputation.*


class ReputationActivity : AppCompatActivity(), ReputationActivityView {
    private var presenter = ReputationActivityPresenter()
    private var isLoadingMore = false
    private var currentPage = 1
    private lateinit var userId: String
    private var pastVisiblesItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reputation)
        supportActionBar?.title = "Reputation History"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userId = intent.getStringExtra(Constants.USER_ID)

        presenter.attach(this)
        presenter.getUserReputations(userId, currentPage)
    }

    override fun showLoading() {
        if (isLoadingMore) {
            reputation_progressBar_bottom.visibility = View.VISIBLE
        } else {
            reputation_progressBar.visibility = View.VISIBLE;
        }
    }

    override fun hideLoading() {
        if (isLoadingMore) {
            reputation_progressBar_bottom.visibility = View.GONE
            isLoadingMore = false
        } else {
            reputation_progressBar.visibility = View.GONE
        }
    }

    override fun onError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    override fun updateReputationAdapter(reputations: MutableList<Reputation>) {
        if (reputation_recyclerView.adapter is ReputationActivityAdapter) {
            val adapter = reputation_recyclerView.adapter as ReputationActivityAdapter
            adapter.addMoreReputation(reputations)
        } else {
            val adapter = ReputationActivityAdapter(reputations)
            linearLayoutManager = LinearLayoutManager(this)
            reputation_recyclerView.layoutManager = linearLayoutManager
            reputation_recyclerView.adapter = adapter
            reputation_recyclerView.addOnScrollListener(onScrollListener)
        }
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!isLoadingMore && dy > 0) {
                visibleItemCount = linearLayoutManager.childCount
                totalItemCount = linearLayoutManager.itemCount
                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 5) {
                    isLoadingMore = true
                    presenter.getUserReputations(userId, currentPage++)
                }

            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
