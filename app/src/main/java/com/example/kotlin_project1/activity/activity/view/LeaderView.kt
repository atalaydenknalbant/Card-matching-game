package com.example.kotlin_project1.activity.activity.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.kotlin_project1.R
import com.example.kotlin_project1.activity.activity.data.LeaderData
import kotlinx.android.synthetic.main.leader_view.view.*

class LeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    constructor(context: Context, leaderData: LeaderData) : this(context) {
        addView(view)
        fillLeader(leaderData)
    }

    private val view: View = LayoutInflater.from(context).inflate(R.layout.leader_view, this, false)

    private fun fillLeader(leaderData: LeaderData) {
        view.run {
            leaderData.let {
                leaderViewNickNameTV.text = it.name
                leaderViewPointTV.text = it.point.toString()
                leaderViewAvatarIV.setImageResource(it.resourceId)
            }
        }
    }
}