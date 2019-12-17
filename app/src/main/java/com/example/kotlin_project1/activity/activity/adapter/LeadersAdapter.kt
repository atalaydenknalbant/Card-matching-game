package com.example.kotlin_project1.activity.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_project1.R
import com.example.kotlin_project1.activity.activity.data.LeaderData

import kot.bau.com.bauapp.view.LeaderView
import kotlinx.android.synthetic.main.new_leader_row_layout.view.*

class LeadersAdapter(
    private val leaders: MutableList<LeaderData>,
    private val context: Context
) : RecyclerView.Adapter<LeadersAdapter.LeadersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeadersViewHolder {
        return LeadersViewHolder(LayoutInflater.from(context).inflate(R.layout.new_leader_row_layout, parent, false))
    }

    override fun getItemCount() = leaders.size

    override fun onBindViewHolder(holder: LeadersViewHolder, position: Int) {
        val leaderView = LeaderView(context, leaders[position])
//        (holder.itemView as LinearLayout).addView(leaderView)
        holder.mainLL.addView(leaderView)
//        with(leaders[position]) {
//            holder.nickNameTV.text = name
//            holder.pointTV.text = point.toString()
//            holder.avatarIV.setImageResource(resourceId)
//        }
    }

    class LeadersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mainLL = view.newLeaderRowMainLL
//        val avatarIV = view.leadersRowIV
//        val nickNameTV = view.leadersRowNickNameTV
//        val pointTV = view.leadersRowPointTV
    }
}
