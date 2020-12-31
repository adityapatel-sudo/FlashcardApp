package com.adityap.flashy_createflashcards.card

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.adityap.flashy_createflashcards.models.CardModel
import com.adityap.flashy_createflashcards.models.CardPair
import com.adityap.flashy_createflashcards.models.FlashcardModel

class CardViewHolder(itemView: FlipCardView) : RecyclerView.ViewHolder(itemView) {
    fun bindItem(flashCard: FlashcardModel) {
        var flipCardView = itemView as FlipCardView
        flipCardView.cardPair = CardPair(CardModel(flashCard.word), CardModel((flashCard.definition)))
    }
}
