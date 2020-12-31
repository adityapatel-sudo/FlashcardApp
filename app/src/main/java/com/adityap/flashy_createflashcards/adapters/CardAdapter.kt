package com.adityap.flashy_createflashcards.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adityap.flashy_createflashcards.card.CardView
import com.adityap.flashy_createflashcards.card.CardViewHolder
import com.adityap.flashy_createflashcards.card.FlipCardView
import com.adityap.flashy_createflashcards.models.FlashcardModel

class CardAdapter(private val flashCards : List<FlashcardModel>): RecyclerView.Adapter<CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder = CardViewHolder(FlipCardView(parent.context))

    override fun getItemCount(): Int = flashCards.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindItem(flashCards[position])
    }
}
