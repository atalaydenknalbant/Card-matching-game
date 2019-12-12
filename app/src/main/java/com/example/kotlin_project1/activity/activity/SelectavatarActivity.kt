package com.example.kotlin_project1.activity.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_project1.R

class SelectavatarActivity : AppCompatActivity() {
    lateinit var avatar1:ImageButton
    lateinit var avatar2:ImageButton
    lateinit var avatar3:ImageButton
    lateinit var avatar4:ImageButton
    lateinit var avatar5:ImageButton
    lateinit var avatar6:ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectavatar)

        val email=intent.getStringExtra("email")
        val pw=intent.getStringExtra("pw")
        val nickname=intent.getStringExtra("nickname")

        avatar1 = findViewById(R.id.firstavatar)
        avatar1.setOnClickListener{
            val intent = Intent(this@SelectavatarActivity,RegisterActivity::class.java)
            val bundle = Bundle()
            bundle.run {
                putString("email",email)
                putString("pw",pw)
                putString("nickname",nickname)
                putString("avatar","avatar1")
                                                }
            intent.putExtras(bundle)
            startActivity(intent)
            finish()

    }
        avatar2 = findViewById(R.id.secondavatar)
        avatar2.setOnClickListener{
            val intent = Intent(this@SelectavatarActivity,RegisterActivity::class.java)
            val bundle = Bundle()
            bundle.run {
                putString("email",email)
                putString("pw",pw)
                putString("nickname",nickname)
                putString("avatar","avatar2")
            }
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
    }
        avatar3 = findViewById(R.id.thirdavatar)
        avatar3.setOnClickListener{
            val intent = Intent(this@SelectavatarActivity,RegisterActivity::class.java)
            val bundle = Bundle()
            bundle.run {
                putString("email",email)
                putString("pw",pw)
                putString("nickname",nickname)
                putString("avatar","avatar3")
            }
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
    }
        avatar4 = findViewById(R.id.fourthavatar)
        avatar4.setOnClickListener{
            val intent = Intent(this@SelectavatarActivity,RegisterActivity::class.java)
            val bundle = Bundle()
            bundle.run {
                putString("email",email)
                putString("pw",pw)
                putString("nickname",nickname)
                putString("avatar","avatar4")
            }
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
    }

        avatar5 = findViewById(R.id.fifthavatar)
        avatar5.setOnClickListener{
            val intent = Intent(this@SelectavatarActivity,RegisterActivity::class.java)
            val bundle = Bundle()
            bundle.run {
                putString("email",email)
                putString("pw",pw)
                putString("nickname",nickname)
                putString("avatar","avatar5")
            }
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
    }
        avatar6 = findViewById(R.id.sixthavatar)
        avatar6.setOnClickListener{
            val intent = Intent(this@SelectavatarActivity,RegisterActivity::class.java)
            val bundle = Bundle()
            bundle.run {
                putString("email",email)
                putString("pw",pw)
                putString("nickname",nickname)
                putString("avatar","avatar6")
            }
            intent.putExtras(bundle)
            startActivity(intent)
            finish()


    }

    }



}
