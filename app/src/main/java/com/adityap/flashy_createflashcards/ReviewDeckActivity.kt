package com.adityap.flashy_createflashcards

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.adityap.flashy_createflashcards.adapters.CardListAdapter
import com.adityap.flashy_createflashcards.models.DeckModel
import com.adityap.flashy_createflashcards.models.FlashcardModel
import kotlinx.android.synthetic.main.activity_review_deck.*
import kotlinx.android.synthetic.main.dialog_add_card.*
import kotlinx.android.synthetic.main.dialog_add_card.buttonAddCard

class ReviewDeckActivity : AppCompatActivity() {

    lateinit var mFlashcardDatabaseHelper: FlashcardDatabaseHelper

    var mCardModelList: List<FlashcardModel>? = null
    var mCardListAdapter: CardListAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_deck)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        mFlashcardDatabaseHelper = FlashcardDatabaseHelper(this)

        var bundle: Bundle = intent.extras
        val deckModel: DeckModel = bundle.getParcelable("Deck")

        textViewDeckName.setText(deckModel.deckName)
        textViewDeckDescription.setText(deckModel.deckDescription)
        buttonAddCard.setOnClickListener(View.OnClickListener {
            val mBuilder = AlertDialog.Builder(this)
            val mView = layoutInflater.inflate(R.layout.dialog_add_card, null)

            mBuilder.setView(mView)
            val dialog = mBuilder.create()
            buttonAddCard.setOnClickListener {
                Log.i("click", "Click Successful")
                val cardWord = addCardWordEditText.text.toString()
                val cardDefinition = addCardDefinitionEditText.text.toString()
                val deckIdForCard = deckModel.id

                mFlashcardDatabaseHelper.addFlashcard(FlashcardModel(deckIdForCard, cardWord, cardDefinition))
                val toast = Toast.makeText(applicationContext, "Card Entered", Toast.LENGTH_SHORT)
                toast.show()
                mCardModelList = mFlashcardDatabaseHelper.readFlashcards(deckIdForCard)
                mCardListAdapter!!.setCardModels(mCardModelList!!)
                mCardListAdapter!!.notifyDataSetChanged()
                Log.i("flashcard", "flashcard sent to db")
                dialog.dismiss()
                Log.i("finish", "function finished")
            }

            cancelAddCard?.setOnClickListener { dialog.dismiss() }
            dialog.show()
        })

        //the next few lines are about creating a listview to view the cards in the deck
        mCardModelList = mFlashcardDatabaseHelper.readFlashcards(deckModel.id)
        mCardListAdapter = CardListAdapter(this, mCardModelList!!)
        listViewCards.setAdapter(mCardListAdapter)

        playDeckButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, PlayCardActivity::class.java)
            intent.putExtra("Deck", deckModel)
            startActivity(intent)
        })
    }
}