package com.adityap.flashy_createflashcards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adityap.flashy_createflashcards.adapters.CustomPagerAdapter
import com.adityap.flashy_createflashcards.models.DeckModel
import kotlinx.android.synthetic.main.activity_play_card.*

class PlayCardActivity : AppCompatActivity() {
    lateinit var mFlashcardDatabaseHelper: FlashcardDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_card)
        mFlashcardDatabaseHelper = FlashcardDatabaseHelper(this)
        val bundle: Bundle? = intent.extras
        val deckModelId: Int? = bundle?.getInt("Deck")
        val listOfCards = mFlashcardDatabaseHelper.readFlashcards(deckModelId)
        viewPager.adapter = CustomPagerAdapter(this, listOfCards)
    }
}