package com.example.kotlin_project1.activity.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.example.kotlin_project1.R
import com.example.kotlin_project1.activity.activity.data.UserData
import com.example.kotlin_project1.databinding.ActivityGameBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class GameActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var auth : FirebaseAuth
    lateinit var db: FirebaseFirestore
    private lateinit var cards: List<LinearLayout>
    private lateinit var cardsEmojis: List<TextView>
    private lateinit var binding: ActivityGameBinding
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
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareCards()
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimeleft.text = "Time Left:" + millisUntilFinished / 1000
                seconds =  millisUntilFinished / 1000
            }

            override fun onFinish() {
                binding.tvTimeleft.text = "Time's Up!"
                seconds =  0
            }
        }.start()

    }

    override fun onClick(view: View?) {
        if (view is LinearLayout) {
            if (cardEmojiHolder is String && cardHolder is LinearLayout) {
                if (cardEmojiHolder == (view.children.first() as TextView).text.toString()) {
                    view.background = null
                    view.children.first().visibility = View.VISIBLE
                    binding.tvScore.text = (binding.tvScore.text.toString().toInt() + 1).toString()
                    if(binding.tvScore.text == "6") {
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
            binding.cardOne,
            binding.cardTwo,
            binding.cardThree,
            binding.cardFour,
            binding.cardFive,
            binding.cardSix,
            binding.cardSeven,
            binding.cardEight,
            binding.cardNine,
            binding.cardTen,
            binding.cardEleven,
            binding.cardTwelve
        )
        for (card in cards) {
            card.setOnClickListener(this)
        }

        cardsEmojis = listOf(
            binding.cardEmojiOne,
            binding.cardEmojiTwo,
            binding.cardEmojiThree,
            binding.cardEmojiFour,
            binding.cardEmojiFive,
            binding.cardEmojiSix,
            binding.cardEmojiSeven,
            binding.cardEmojiEight,
            binding.cardEmojiNine,
            binding.cardEmojiTen,
            binding.cardEmojiEleven,
            binding.cardEmojiTwelve
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