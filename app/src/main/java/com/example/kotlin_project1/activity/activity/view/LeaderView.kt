package com.example.kotlin_project1.activity.activity.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.kotlin_project1.activity.activity.data.LeaderData
import com.example.kotlin_project1.databinding.LeaderViewBinding

class LeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding = LeaderViewBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context, leaderData: LeaderData) : this(context) {
        fillLeader(leaderData)
    }

    private fun fillLeader(leaderData: LeaderData) {
        binding.leaderViewNickNameTV.text = leaderData.name
        binding.leaderViewPointTV.text = leaderData.point.toString()
        binding.leaderViewAvatarIV.setImageResource(leaderData.resourceId)
    }
}