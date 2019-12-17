package com.example.kotlin_project1.activity.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_game.*
import kotlin.random.Random
import com.example.kotlin_project1.R
import com.example.kotlin_project1.activity.activity.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class GameActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var auth : FirebaseAuth
    lateinit var db: FirebaseFirestore
    private lateinit var cards: List<LinearLayout>
    private lateinit var cardsEmojis: List<TextView>
    var seconds:Long = 0
    var score:Long = 0
    private var isTwoCardsSet: Boolean = false
    private var isAllCardsSet: Boolean = false
    private var cardEmojiHolder: String? = null
    private var cardHolder: LinearLayout? = null
    private var emojis = mutableListOf(
        "📚",
        "💌",
        "🔒",
        "❤️",
        "⛔️",
        "♣️",
        "🛩",
        "🎮",
        "🚗",
        "⛱",
        "🏝",
        "⛰",
        "📷",
        "💣",
        "🔑",
        "🧸",
        "🎈"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        prepareCards()
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTimeleft.setText("Time Left:" + millisUntilFinished / 1000)
                seconds =  millisUntilFinished / 1000
            }

            override fun onFinish() {
                tvTimeleft.setText("Time's Up!")
                var seconds =  0
            }
        }.start()

    }

    override fun onClick(view: View?) {
        if (view is LinearLayout) {
            if (cardEmojiHolder is String && cardHolder is LinearLayout) {
                if (cardEmojiHolder == (view.children.first() as TextView).text.toString()) {
                    view.background = null
                    view.children.first().visibility = View.VISIBLE
                    tvScore.text = (tvScore.text.toString().toInt() + 1).toString()
                    if(tvScore.text == "6") {
                        score = 600 + (seconds * 3)
                        val userid = auth.currentUser!!.uid
                        val docRef = db.collection("users").document(userid)
                        docRef.get().addOnSuccessListener { documentSnapshot ->
                            var user = documentSnapshot.toObject(UserData::class.java)
                            var hs:String =user!!.highScore.toString()
                            if (score>hs.toLong()){
                                docRef.update("highScore",score)
                                docRef.update("score",score)

                            }
                            else
                            {
                                docRef.update("score",score)
                            }
                        }



                        val intent = Intent(applicationContext , MainActivity::class.java)

                        startActivity(intent)
                        finish()


                    }
                    view.isClickable = false
                    cardHolder?.let { it.isClickable = false }
                    cardHolder = null
                    cardEmojiHolder = null

                } else {
                    view.background = null
                    view.children.first().visibility = View.VISIBLE
                    Handler().postDelayed({
                        view.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
                        view.children.first().visibility = View.INVISIBLE
                        view.isClickable = true
                        cardHolder?.let {
                            it.setBackgroundColor(
                                ContextCompat.getColor(
                                    applicationContext,
                                    R.color.colorPrimary
                                )
                            )
                        }
                        cardHolder?.let { it.children.first().visibility = View.INVISIBLE }
                        cardHolder?.let { it.isClickable = true }
                        cardHolder = null
                        cardEmojiHolder = null
                    }, 500)
                }
            } else {
                if (view.background == null && view.children.first().visibility == View.VISIBLE) {
                    view.isClickable = true
                    view.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
                    view.children.first().visibility = View.INVISIBLE
                } else {
                    view.background = null
                    view.children.first().visibility = View.VISIBLE
                    view.isClickable = false
                    cardEmojiHolder = (view.children.first() as TextView).text.toString()
                    cardHolder = view
                }
            }
        }
    }


    private fun prepareCards() {
        cards = listOf(
            cardOne,
            cardTwo,
            cardThree,
            cardFour,
            cardFive,
            cardSix,
            cardSeven,
            cardEight,
            cardNine,
            cardTen,
            cardEleven,
            cardTwelve
        )
        for (card in cards) {
            card.setOnClickListener(this)
        }

        cardsEmojis = listOf(
            cardEmojiOne,
            cardEmojiTwo,
            cardEmojiThree,
            cardEmojiFour,
            cardEmojiFive,
            cardEmojiSix,
            cardEmojiSeven,
            cardEmojiEight,
            cardEmojiNine,
            cardEmojiTen,
            cardEmojiEleven,
            cardEmojiTwelve
        )
        while (!isAllCardsSet) {
            if (cardsEmojis.all { it.text.isNotEmpty() }) {
                isAllCardsSet = true
            } else {
                isTwoCardsSet = false
                while (!isTwoCardsSet) {
                    var cardsEmojisIndex = Random.nextInt(0, cardsEmojis.size)
                    val emojiIndex = Random.nextInt(0, emojis.size)
                    if (cardsEmojis[cardsEmojisIndex].text.isEmpty()) {
                        cardsEmojis[cardsEmojisIndex].text = emojis[emojiIndex]
                        while (!isTwoCardsSet) {
                            cardsEmojisIndex = Random.nextInt(0, cardsEmojis.size)
                            if (cardsEmojis[cardsEmojisIndex].text.isEmpty()) {
                                cardsEmojis[cardsEmojisIndex].text = emojis[emojiIndex]
                                emojis.removeAt(emojiIndex)
                                isTwoCardsSet = true
                            }
                        }
                    }
                }
            }
        }
    }
}