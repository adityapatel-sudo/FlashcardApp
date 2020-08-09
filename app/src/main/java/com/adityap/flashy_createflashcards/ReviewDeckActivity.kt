package com.adityap.flashy_createflashcards

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.adityap.flashy_createflashcards.adapters.CardListAdapter
import com.adityap.flashy_createflashcards.dialogs.AddCardDialog
import com.adityap.flashy_createflashcards.models.DeckModel
import com.adityap.flashy_createflashcards.models.FlashcardModel
import kotlinx.android.synthetic.main.activity_review_deck.*

class ReviewDeckActivity : AppCompatActivity() {
    companion object{
        const val TAG = "ReviewDeckActivity"
    }
    lateinit var mFlashcardDatabaseHelper: FlashcardDatabaseHelper

    lateinit var mCardModelList:  MutableList<FlashcardModel>
    lateinit var mCardListAdapter: CardListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_deck)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var bundle: Bundle = intent.extras
        val deckModel: DeckModel = bundle.getParcelable("Deck")


        mCardModelList = mutableListOf()
        mFlashcardDatabaseHelper = FlashcardDatabaseHelper(this)
        mCardModelList.addAll(mFlashcardDatabaseHelper.readFlashcards(deckModel.id))

        mCardListAdapter = CardListAdapter(this, mCardModelList)

        textViewDeckName.text = deckModel.deckName
        textViewDeckDescription.text = deckModel.deckDescription

        listViewCards.adapter = mCardListAdapter

        buttonAddCard.setOnClickListener(View.OnClickListener {
            showAddCardDialog(deckModel.id)

        })
        playDeckButton.setOnClickListener(View.OnClickListener {
            startPlayDeckActivity(deckModel.id)
        })
    }

    private fun showAddCardDialog(deckId: Int) {
        val dialog = AddCardDialog.getInstance(deckId)
        dialog.listener = object : AddCardDialog.Listener{
            override fun onSave() {
                mCardModelList.clear()
                mCardModelList.addAll(mFlashcardDatabaseHelper.readFlashcards(deckId))
                mCardListAdapter.notifyDataSetChanged()
            }

        }
        dialog.show(supportFragmentManager, TAG)
    }
    private fun startPlayDeckActivity(deckId: Int){
        val intent = Intent(this, PlayCardActivity::class.java)
        intent.putExtra("Deck", deckId)
        startActivity(intent)
    }
}
