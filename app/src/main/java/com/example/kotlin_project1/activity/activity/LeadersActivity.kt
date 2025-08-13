package com.example.kotlin_project1.activity.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_project1.R
import com.example.kotlin_project1.activity.activity.adapter.LeadersAdapter
import com.example.kotlin_project1.activity.activity.data.LeaderData
import com.example.kotlin_project1.activity.activity.data.UserData
import com.example.kotlin_project1.databinding.LeadersActivityBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class LeadersActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var binding: LeadersActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LeadersActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = FirebaseFirestore.getInstance()

        getlistofusers { list ->
            val leaders = list.map { (avatar, name, score) ->
                LeaderData(getResourceId(avatar), name, score)
            }.toMutableList()
            val mLayoutManager = LinearLayoutManager(this)
            with(binding.leadersRV) {
                layoutManager = mLayoutManager
                adapter = LeadersAdapter(leaders, this@LeadersActivity)
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

    fun getlistofusers(myCallback: (List<Triple<String?, String?, Long?>>) -> Unit) {
        val colRef = db.collection("users")
        colRef.addSnapshotListener { _, e ->
            if (e != null) {
                return@addSnapshotListener
            }
        }

        colRef.orderBy("highScore", Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Triple<String?, String?, Long?>>()
                    var i = 0
                    for (document in task.result!!.iterator()) {
                        if (i == 6) break
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
