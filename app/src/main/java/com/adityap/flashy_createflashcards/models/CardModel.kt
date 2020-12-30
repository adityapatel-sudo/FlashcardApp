package com.adityap.flashy_createflashcards.models

import androidx.annotation.DrawableRes

/**
 * Represent the single side of the Flashcard, could be front or back.
 */
data class CardModel(
        val title: String? = null,
        val description: String? = null,
        @DrawableRes val imageRes: Int? = null ) {

    init {
        require(!title.isNullOrEmpty() || !description.isNullOrEmpty() || imageRes != null) {
            "At least one non-null , non-empty value needed for card to render"
        }
    }
}

/**
 * Represents the front and back view of a Flash card.
 */
data class CardPair(val front: CardModel, val back: CardModel)
