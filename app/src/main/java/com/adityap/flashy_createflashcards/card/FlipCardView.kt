package com.adityap.flashy_createflashcards.card

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.adityap.flashy_createflashcards.R
import com.adityap.flashy_createflashcards.models.CardPair

/**
 * The Complete FlashCard with front and back, knows how to show front and back view.
 */
class FlipCardView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle) {

    var isFrontVisible = true
    init {
        View.inflate(context, R.layout.flip_card_view, this)
        setOnClickListener {
            frontCard.isVisible = !isFrontVisible
            backCard.isVisible = isFrontVisible
            isFrontVisible = !isFrontVisible
        }
    }

    var frontCard : CardView = findViewById(R.id.front_card)
    var backCard : CardView = findViewById(R.id.back_card)

    var cardPair : CardPair? = null
        set(value) {
            field = value
            frontCard.cardModel = cardPair?.front
            backCard.cardModel = cardPair?.back
        }
}
