package com.adityap.flashy_createflashcards

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.adityap.flashy_createflashcards.adapters.CardAdapter
import com.adityap.flashy_createflashcards.database.DatabaseHelper
import com.adityap.flashy_createflashcards.database.DatabaseHelperFactory
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import kotlinx.android.synthetic.main.activity_play_card.*

class PlayCardActivity : AppCompatActivity() {
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_card)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        databaseHelper = DatabaseHelperFactory.getDBHelper(this)
        val bundle: Bundle? = intent.extras
        val deckModelId: Int? = bundle?.getInt("Deck")
        val listOfCards = databaseHelper.readFlashcards(deckModelId)

        card_stack_view.layoutManager = CardStackLayoutManager(baseContext);
        card_stack_view.adapter = CardAdapter(listOfCards)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId === android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}
