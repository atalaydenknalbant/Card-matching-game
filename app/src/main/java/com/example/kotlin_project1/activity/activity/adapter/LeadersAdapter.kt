package com.example.kotlin_project1.activity.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_project1.activity.activity.data.LeaderData
import com.example.kotlin_project1.activity.activity.view.LeaderView
import com.example.kotlin_project1.databinding.NewLeaderRowLayoutBinding

class LeadersAdapter(
    private val leaders: MutableList<LeaderData>,
    private val context: Context
) : RecyclerView.Adapter<LeadersAdapter.LeadersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeadersViewHolder {
        val binding = NewLeaderRowLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return LeadersViewHolder(binding)
    }

    override fun getItemCount() = leaders.size

    override fun onBindViewHolder(holder: LeadersViewHolder, position: Int) {
        val leaderView = LeaderView(context, leaders[position])
        holder.binding.newLeaderRowMainLL.addView(leaderView)
//        with(leaders[position]) {
//            holder.nickNameTV.text = name
//            holder.pointTV.text = point.toString()
//            holder.avatarIV.setImageResource(resourceId)
//        }
    }

    class LeadersViewHolder(val binding: NewLeaderRowLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}
