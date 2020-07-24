package com.adityap.flashy_createflashcards

import android.os.Bundle
import android.view.View
import android.widget.ListView
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

    lateinit var mCardModelList: List<FlashcardModel>
    lateinit var mCardListAdapter: CardListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_deck)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val cardListview = findViewById<ListView>(R.id.listViewCards)

        var bundle: Bundle = intent.extras
        val deckModel: DeckModel = bundle.getParcelable("Deck")

        mFlashcardDatabaseHelper = FlashcardDatabaseHelper(this)
        mCardModelList = mFlashcardDatabaseHelper.readFlashcards(deckModel.id)

        mCardListAdapter = CardListAdapter(this, mCardModelList)
        cardListview.adapter = mCardListAdapter

        textViewDeckName.text = deckModel.deckName
        textViewDeckDescription.text = deckModel.deckDescription
        buttonAddCard.setOnClickListener(View.OnClickListener {
            showAddCardDialog(deckModel.id)
        })
    }

    private fun showAddCardDialog(deckId: Int) {
        val dialog = AddCardDialog.getInstance(deckId)
        dialog.listener = object : AddCardDialog.Listener{
            override fun onSave() {
                mCardModelList = mFlashcardDatabaseHelper.readFlashcards(deckId)
                mCardListAdapter.notifyDataSetChanged()
            }

        }
        dialog.show(supportFragmentManager, TAG)

    }
}
