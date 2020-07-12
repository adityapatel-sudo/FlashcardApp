package com.adityap.flashy_createflashcards

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.adityap.flashy_createflashcards.models.DeckModel
import kotlinx.android.synthetic.main.activity_create_deck.*

class CreateDeckActivity : AppCompatActivity() {
    var mFlashcardDatabaseHelper: FlashcardDatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_deck)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setSupportActionBar(toolbar)
        mFlashcardDatabaseHelper = FlashcardDatabaseHelper(this)
        read_edittext_button.setOnClickListener(View.OnClickListener { //The following adds name and description of deck into the deck table in db
            val editTextDeckName = editTextDeckName.getText().toString()
            val editTextDeckDescription = editTextDescription.getText().toString()
            mFlashcardDatabaseHelper!!.addDeck(DeckModel(deckName = editTextDeckName, deckDescription = editTextDeckDescription))
            val result = Intent()
            setResult(Activity.RESULT_OK, result)
            finish()
        })
    }
}
