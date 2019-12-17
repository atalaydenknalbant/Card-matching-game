package com.example.kotlin_project1.activity.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_project1.R
import com.example.kotlin_project1.activity.activity.data.UserData
import com.example.kotlin_project1.activity.activity.helper.AvatarHelp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    lateinit var db:FirebaseFirestore
    lateinit var registerBtn : Button
    lateinit var registerEmail : EditText
    lateinit var registerPassword : EditText
    lateinit var registerLoginBtn : Button
    lateinit var progressbar : ProgressDialog
    lateinit var registernickName : EditText
    lateinit var auth: FirebaseAuth
    lateinit var imageview:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        imageview = findViewById(R.id.imageviewpp)
        registerBtn = findViewById(R.id.RegisterBtn)
        registerLoginBtn = findViewById(R.id.RegisterLoginBtn)
        registernickName = findViewById(R.id.RegisterNickname)
        registerEmail = findViewById(R.id.RegisterEmail)
        registerPassword = findViewById(R.id.RegisterPassword)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        progressbar = ProgressDialog(this)
        registerEmail.setText(intent.getStringExtra("email"))
        registerPassword.setText(intent.getStringExtra("pw"))
        registernickName.setText(intent.getStringExtra("nickname"))
        val avatar:String? = intent.getStringExtra("avatar") ?:"avatar1"
        val avatarhelp:AvatarHelp = AvatarHelp()
        avatarhelp.changeavatar(avatar,imageview)





        registerLoginBtn.setOnClickListener {

            val loginIntent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(loginIntent)

        }

        imageview.setOnClickListener{
            val email = registerEmail.text.toString().trim()
            val password = registerPassword.text.toString().trim()
            val nickname = registernickName.text.toString().trim()
            val intent = Intent(this, SelectavatarActivity::class.java)
            val bundle = Bundle()
            bundle.putString("email",email)
            bundle.putString("pw",password)
            bundle.putString("nickname",nickname)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()

        }

        registerBtn.setOnClickListener {
            val email = registerEmail.text.toString().trim()
            val password = registerPassword.text.toString().trim()
            val nickname = registernickName.text.toString().trim()
            if (TextUtils.isEmpty(email)) {

                registerEmail.error = "Enter Email"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {

                registerPassword.error = "Enter Password"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(nickname)) {

                registernickName.error = "Enter Nickname"
                return@setOnClickListener
            }

            createUser(email, password, nickname, avatar)

        }

    }

    fun createUser( email: String, password: String, nickname:String, avatar:String?) {

        progressbar.setMessage("Please wait..")
        progressbar.show()


        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {


                    val userid = auth.currentUser!!.uid
                    var user = hashMapOf(
                        "email" to email,
                        "pw" to password,
                        "nickName" to nickname,
                        "score" to 0,
                        "highScore" to 0,
                        "avatar" to avatar
                    )

                    db.collection("users")
                        .document(userid)
                        .set(user)
                            .addOnSuccessListener { documentReference ->
                            Toast.makeText(this, "Signed Up", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to Sign Up", Toast.LENGTH_SHORT).show()
                        }



                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    progressbar.dismiss()

                } else {

                    Toast.makeText(this, "Authentication failed.${task.exception}", Toast.LENGTH_SHORT).show()
                    progressbar.dismiss()

                }
            }
    }

}


