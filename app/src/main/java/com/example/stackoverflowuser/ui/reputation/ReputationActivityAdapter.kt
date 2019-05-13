package com.example.stackoverflowuser.ui.reputation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.stackoverflowuser.R
import com.example.stackoverflowuser.constants.AppUntil
import com.example.stackoverflowuser.model.Reputation

class ReputationActivityAdapter(private var reputations: MutableList<Reputation>) :
    RecyclerView.Adapter<ReputationActivityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reputation, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reputations.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reputation = reputations.get(position)
        holder.tvReputation.text = reputation.reputationHistoryType
        holder.tvPostId.text = reputation.postId.toString()
        holder.tvChange.text = reputation.reputationChange.toString()
        if (reputation.creationDate != null) {
            holder.tvCreated.text = AppUntil.formatDate(reputation.creationDate!! * 1000)
        }

    }

    fun addMoreReputation(moreReputations: MutableList<Reputation>) {
        reputations.addAll(moreReputations)
        notifyItemRangeInserted(reputations.size - moreReputations.size, reputations.size)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvReputation = itemView.findViewById(R.id.item_reputation_tv_reputation) as TextView
        val tvCreated = itemView.findViewById(R.id.item_reputation_tv_created) as TextView
        val tvChange = itemView.findViewById(R.id.item_reputation_tv_change) as TextView
        val tvPostId = itemView.findViewById(R.id.item_reputation_tv_post_id) as TextView


    }
}

