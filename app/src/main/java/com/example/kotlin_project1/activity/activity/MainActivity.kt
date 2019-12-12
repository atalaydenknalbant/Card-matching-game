package com.example.kotlin_project1.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.kotlin_project1.R
import com.google.firebase.auth.FirebaseAuth
import com.example.kotlin_project1.activity.activity.helper.AvatarHelp
class MainActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var logoutBtn : Button
    lateinit var playBtn : Button
    lateinit var leaderboardBtn : Button
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        logoutBtn = findViewById(R.id.MainLogoutBtn)
        playBtn = findViewById(R.id.MainPlayBtn)
        leaderboardBtn = findViewById(R.id.MainLeaderboardBtn)
        imageView = findViewById(R.id.Mainimageview)
        val avatarHelp:AvatarHelp = AvatarHelp()
//        avatarHelp.changeavatar(avatar = avatar ,imageview = imageView )



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
        }
    }







}
