package com.adityap.flashy_createflashcards.card

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.adityap.flashy_createflashcards.R

/**
 * The Complete FlashCard with front and back, knows how to show front and back view.
 */
class FlipCardView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle) {

    init {
        View.inflate(context, R.layout.flashcard_item_layout, this)
    }
}
