package com.example.kotlin_project1.activity.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.kotlin_project1.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var loginbtn  : Button
    lateinit var loginRegisterBtn : Button
    lateinit var loginEmail : EditText
    lateinit var loginPassword : EditText
    lateinit var progressbar : ProgressDialog

    lateinit var auth  : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginbtn = findViewById(R.id.LoginBtn)
        loginRegisterBtn = findViewById(R.id.LoginRegisterBtn)
        loginEmail = findViewById(R.id.LoginEmail)

        loginPassword = findViewById(R.id.LoginPassword)
        progressbar = ProgressDialog(this)

        auth = FirebaseAuth.getInstance()

        loginbtn.setOnClickListener {
            val email = loginEmail.text.toString().trim()
            val password = loginPassword.text.toString().trim()

            if(TextUtils.isEmpty(email)){
                loginEmail.error = " Enter Email"
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(password)){
                loginPassword.error = " Enter Password"
                return@setOnClickListener
            }

            loginUser(email , password)
        }

        loginRegisterBtn.setOnClickListener {
            val registerActivity = Intent(applicationContext , RegisterActivity::class.java)
            startActivity(registerActivity)
            finish()
        }

    }

    private fun loginUser(email: String, password: String) {
        progressbar.setMessage("Please wait..")
        progressbar.show()

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        progressbar.dismiss()
                        val startIntent  = Intent(applicationContext , MainActivity::class.java)
                        startActivity(startIntent)
                        finish()
                    } else {

                        Toast.makeText(this, "Authentication failed.${task.exception}", Toast.LENGTH_SHORT).show()

                    }

                    progressbar.dismiss()
                }
    }
}
