package com.example.kotlin_project1.activity.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_project1.R
import com.example.kotlin_project1.activity.activity.data.LeaderData
import com.example.kotlin_project1.activity.activity.adapter.LeadersAdapter
import kotlinx.android.synthetic.main.leaders_activity.*

class LeadersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leaders_activity)

        val mLayoutManager = LinearLayoutManager(this)
        with(leadersRV) {
            layoutManager = mLayoutManager
            adapter = LeadersAdapter(generateLeadersData(), this@LeadersActivity)
        }
    }

    private fun generateLeadersData(): MutableList<LeaderData> {
        val leaders = mutableListOf(
            LeaderData(getResourceId((0..5).random()), "Ali", 5),
            LeaderData(getResourceId((0..5).random()), "Ali1"),
            LeaderData(getResourceId((0..5).random()), "Ali2"),
            LeaderData(getResourceId((0..5).random()), "Ali3", 15),
            LeaderData(getResourceId((0..5).random()), name = "Ali4", point = 7),
            LeaderData(getResourceId((0..5).random()), name = "Ali5", point = 1_000_000)
        )

        val leaderData = leaders[0]
        leaderData.run { point = 1_000 }
        leaderData.let { it.point = 1000 }

        val filteredLeaders = leaders
            .filter { it.point > 0 }
            .sortedByDescending { it.point }

        return filteredLeaders.toMutableList()
    }

    private fun getResourceId(id: Int) = when (id) {
        0 -> R.mipmap.ic_first_avatar
        1 -> R.mipmap.ic_second_avatar
        2 -> R.mipmap.ic_third_avatar
        3 -> R.mipmap.ic_fourth_avatar
        4 -> R.mipmap.ic_fifth_avatar
        5 -> R.mipmap.ic_sixth_avatar
        else -> R.mipmap.ic_first_avatar
    }
}