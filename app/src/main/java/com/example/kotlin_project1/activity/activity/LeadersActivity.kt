package com.example.kotlin_project1.activity.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.solver.widgets.Snapshot
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_project1.R
import com.example.kotlin_project1.activity.activity.data.LeaderData
import com.example.kotlin_project1.activity.activity.adapter.LeadersAdapter
import com.example.kotlin_project1.activity.activity.data.UserData
import com.example.kotlin_project1.activity.activity.helper.AvatarHelp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.leaders_activity.*

class LeadersActivity : AppCompatActivity() {


    lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leaders_activity)
        db = FirebaseFirestore.getInstance()





    getlistofusers {      fun generateLeadersData(): MutableList<LeaderData> {


    val leaders = mutableListOf(
        LeaderData(getResourceId(it[0].first), it[0].second, it[0].third),
        LeaderData(getResourceId(it[1].first), it[1].second, it[1].third),
        LeaderData(getResourceId(it[2].first), it[2].second, it[2].third),
        LeaderData(getResourceId(it[3].first), it[3].second, it[3].third),
        LeaderData(getResourceId(it[4].first), it[4].second, it[4].third),
        LeaderData(getResourceId(it[5].first), it[5].second, it[5].third)
    )




    return leaders.toMutableList()
}
        val mLayoutManager = LinearLayoutManager(this)
        with(leadersRV) {
            layoutManager = mLayoutManager
            adapter = LeadersAdapter(generateLeadersData(), this@LeadersActivity)
        }
    }

    }



    private fun getResourceId(id: String?) = when (id) {
        "avatar1" -> R.mipmap.ic_first_avatar
        "avatar2" -> R.mipmap.ic_second_avatar
        "avatar3" -> R.mipmap.ic_third_avatar
        "avatar4" -> R.mipmap.ic_fourth_avatar
        "avatar5" -> R.mipmap.ic_fifth_avatar
        "avatar6" -> R.mipmap.ic_sixth_avatar
        else -> R.mipmap.ic_first_avatar
    }









    fun getlistofusers(myCallback: (List<Triple<String?,String?,Long?>>) -> Unit){

        var i=0
        val colRef = db.collection("users")
        colRef
            .addSnapshotListener{ _ , e ->
                if (e != null) {
                    return@addSnapshotListener
                }
            }

        colRef
            .orderBy("highScore", Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var list = mutableListOf<Triple<String?, String?, Long?>>()
                    for (document in task.result!!.iterator()) {
                        if (i == 6) {
                            break

                        }
                        val doc = document.toObject(UserData::class.java)
                        list.add(Triple(doc.avatar, doc.nickName, doc.highScore))
                        Log.d("abcd", list.toString())

                        i += 1
                    }
                    myCallback(list)
                }
            }

    }
}