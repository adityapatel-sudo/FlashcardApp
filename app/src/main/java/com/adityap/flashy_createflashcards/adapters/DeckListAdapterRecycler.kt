package com.adityap.flashy_createflashcards.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.adityap.flashy_createflashcards.FlashcardDatabaseHelper
import com.adityap.flashy_createflashcards.R
import com.adityap.flashy_createflashcards.ReviewDeckActivity
import com.adityap.flashy_createflashcards.models.DeckModel

class DeckListAdapterRecycler(private val mContext: Context, var deckModelList: MutableList<DeckModel>)
    :RecyclerView.Adapter<DeckListAdapterRecycler.CardHolder>(){

    lateinit var mFlashcardDatabaseHelper: FlashcardDatabaseHelper

    class CardHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_cardlist_item_view,parent,false) as CardView

        Log.d("TAG","hey look at this "+ deckModelList[0].deckName)
        return CardHolder(cardView)
    }

    override fun getItemCount(): Int {
        return deckModelList.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.run {
            cardView.findViewById<ConstraintLayout>(R.id.constraint_layout).apply {
                setOnClickListener(View.OnClickListener {
                    val intent = Intent(mContext, ReviewDeckActivity::class.java)
                    intent.putExtra("Deck", deckModelList[position])
                    ContextCompat.startActivity(mContext,intent, Bundle())
                })
            }
            cardView.findViewById<TextView>(R.id.cardName).apply {
                text =deckModelList[position].deckName
            }
            cardView.findViewById<TextView>(R.id.cardDescription).apply {
                text = deckModelList[position].deckDescription
            }
        }
    }

    fun removeItem(cardHolder: CardHolder){
        mFlashcardDatabaseHelper = FlashcardDatabaseHelper(mContext)
        mFlashcardDatabaseHelper.deleteDeck(deckModelList[cardHolder.adapterPosition].id)
        deckModelList.clear()
        deckModelList.addAll(mFlashcardDatabaseHelper.readDeck())
        notifyDataSetChanged()
    }


}