package com.adityap.flashy_createflashcards.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.adityap.flashy_createflashcards.FlashcardDatabaseHelper
import com.adityap.flashy_createflashcards.R
import com.adityap.flashy_createflashcards.models.FlashcardModel

class RecycleViewDeckCardListAdapter(private val mContext: Context, var flashcardModelList: MutableList<FlashcardModel>, var deckid : Int) : RecyclerView.Adapter<RecycleViewDeckCardListAdapter.CardHolder>() {
    lateinit var mFlashcardDatabaseHelper: FlashcardDatabaseHelper


    class CardHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewDeckCardListAdapter.CardHolder {
        val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_cardlist_item_view,parent,false) as CardView
        return CardHolder(cardView)

    }

    override fun getItemCount(): Int {
        return flashcardModelList.size
    }

    override fun onBindViewHolder(holder: RecycleViewDeckCardListAdapter.CardHolder, position: Int) {
        holder.run {
            cardView.findViewById<TextView>(R.id.cardName).apply {
                text = flashcardModelList[position].word
            }
            cardView.findViewById<TextView>(R.id.cardDescription).apply {
                text = flashcardModelList[position].definition
            }
        }
    }
    fun removeItem(viewHolder: CardHolder){
        mFlashcardDatabaseHelper = FlashcardDatabaseHelper(mContext)
        mFlashcardDatabaseHelper.deleteFlashcard(flashcardModelList[viewHolder.adapterPosition].id)

        flashcardModelList.clear()
        flashcardModelList.addAll(mFlashcardDatabaseHelper.readFlashcards(deckid))
        notifyDataSetChanged()
    }



}