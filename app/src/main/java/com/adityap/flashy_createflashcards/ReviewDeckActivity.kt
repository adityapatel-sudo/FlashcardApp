package com.adityap.flashy_createflashcards

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adityap.flashy_createflashcards.adapters.CardListAdapter
import com.adityap.flashy_createflashcards.adapters.RecycleViewDeckCardListAdapter
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


    private lateinit var recyclerViewCards: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager



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

        textViewDeckName.text = deckModel.deckName
        textViewDeckDescription.text = deckModel.deckDescription

        viewManager = LinearLayoutManager(this)
        viewAdapter = RecycleViewDeckCardListAdapter(this,mCardModelList,deckModel.id)


        recyclerViewCards = findViewById<RecyclerView>(R.id.recycler_view_cards).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

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
                viewAdapter.notifyDataSetChanged()
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
