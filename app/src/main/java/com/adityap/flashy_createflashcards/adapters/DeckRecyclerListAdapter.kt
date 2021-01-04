package com.adityap.flashy_createflashcards.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.adityap.flashy_createflashcards.database.FlashcardDatabaseHelper
import com.adityap.flashy_createflashcards.R
import com.adityap.flashy_createflashcards.ReviewDeckActivity
import com.adityap.flashy_createflashcards.database.DatabaseHelper
import com.adityap.flashy_createflashcards.database.DatabaseHelperFactory
import com.adityap.flashy_createflashcards.models.DeckModel
import com.adityap.flashy_createflashcards.models.FlashcardModel
import com.google.android.material.snackbar.Snackbar

class DeckRecyclerListAdapter(private val mContext: Context, var deckModelList: MutableList<DeckModel>)
    :RecyclerView.Adapter<DeckRecyclerListAdapter.CardHolder>(){

    private var removedPosition: Int = 0
    lateinit var removedDeck : DeckModel
    lateinit var removedCards: List<FlashcardModel>
    var flashcardDatabaseHelper: DatabaseHelper = DatabaseHelperFactory.getDBHelper(mContext)

    class CardHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.deckcard_iten_view, parent, false) as CardView

        Log.d("TAG","hey look at this "+ deckModelList[0].deckName)
        return CardHolder(cardView)
    }

    override fun getItemCount(): Int {
        return deckModelList.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.run {
            val cardsCount = flashcardDatabaseHelper.readFlashcards(deckModelList[position].id).count()
            cardView.findViewById<ConstraintLayout>(R.id.constraint_layout).apply {
                setOnClickListener(View.OnClickListener {
                    val intent = Intent(mContext, ReviewDeckActivity::class.java)
                    intent.putExtra("Deck", deckModelList[position])
                    ContextCompat.startActivity(mContext, intent, Bundle())
                })
            }
            cardView.findViewById<TextView>(R.id.cardCount).apply {
                text = "$cardsCount Cards"
            }
            cardView.findViewById<TextView>(R.id.cardName).apply {
                text = deckModelList[position].deckName
            }
            cardView.findViewById<TextView>(R.id.cardDescription).apply {
                text = deckModelList[position].deckDescription
            }
        }
    }

    fun removeItem(cardHolder: CardHolder) {

        removedPosition = cardHolder.adapterPosition
        removedDeck = flashcardDatabaseHelper.readDeck()[removedPosition]
        removedCards = flashcardDatabaseHelper.readFlashcards(removedDeck.id)


        flashcardDatabaseHelper.deleteDeck(deckModelList[cardHolder.adapterPosition].id)
        deckModelList.clear()
        deckModelList.addAll(flashcardDatabaseHelper.readDeck())
        notifyItemRemoved(cardHolder.adapterPosition)

        Snackbar.make(cardHolder.itemView, "${removedDeck.deckName} " +
                "deleted with ${removedCards.size} " +
                "cards.", Snackbar.LENGTH_LONG).setAction("UNDO") {
            val newId = flashcardDatabaseHelper.addDeck(removedDeck)
            for (element in removedCards) {
                flashcardDatabaseHelper.addFlashcard(element, newId)
            }

            deckModelList.clear()
            deckModelList.addAll(flashcardDatabaseHelper.readDeck())
            notifyDataSetChanged()
        }.show()
    }


}
