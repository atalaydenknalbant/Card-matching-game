package com.example.kotlin_project1.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.kotlin_project1.R
import com.example.kotlin_project1.activity.activity.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.example.kotlin_project1.activity.activity.helper.AvatarHelp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity() {
    lateinit var tv1:TextView
    lateinit var tv:TextView
    lateinit var db: FirebaseFirestore
    lateinit var auth : FirebaseAuth
    lateinit var logoutBtn : Button
    lateinit var playBtn : Button
    lateinit var leaderboardBtn : Button
    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv1 = findViewById(R.id.MainScores)
        tv  = findViewById(R.id.textView)
        auth = FirebaseAuth.getInstance()
        logoutBtn = findViewById(R.id.MainLogoutBtn)
        playBtn = findViewById(R.id.MainPlayBtn)
        leaderboardBtn = findViewById(R.id.MainLeaderboardBtn)
        imageView = findViewById(R.id.Mainimageview)
        db = FirebaseFirestore.getInstance()








        logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }
        leaderboardBtn.setOnClickListener{
            val intent = Intent(this, LeadersActivity::class.java)
            startActivity(intent)
            finish()
        }
        playBtn.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }




    }

    override fun onStart() {
        super.onStart()

        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser == null){

            val intent = Intent(applicationContext , LoginActivity::class.java)
            startActivity(intent)
            finish()

        }else{
                Toast.makeText(applicationContext , "Returned to Main Menu" , Toast.LENGTH_SHORT).show()
            val userid = auth.currentUser!!.uid
            val colRef = db.collection("users")
            val docRef = db.collection("users").document(userid)
            colRef.addSnapshotListener{ _ , e ->
                if (e != null) {
                    return@addSnapshotListener
                }
            }
            docRef.addSnapshotListener { _ , e ->
                if (e != null) {
                    return@addSnapshotListener
                }
            }


            docRef.get().addOnSuccessListener { documentSnapshot ->
                var user = documentSnapshot.toObject(UserData::class.java)

                val avatar = user!!.avatar
                val nickname = user.nickName
                var score = user.score
                var highscore = user.highScore
                val avatarHelp:AvatarHelp = AvatarHelp()
                avatarHelp.changeavatar(avatar = avatar ,imageview = imageView )
                tv.setText(nickname.toString())
                tv1.setText("HighScore: "+highscore.toString()+"  Score: "+score.toString())

            }
        }
    }







}
