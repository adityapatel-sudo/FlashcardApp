package com.adityap.flashy_createflashcards.card

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.adityap.flashy_createflashcards.R
import com.adityap.flashy_createflashcards.models.CardModel

/**
 * Single side view of the card.
 */
class CardView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0) : LinearLayout(context, attrs, defStyle) {

    init {
        View.inflate(context, R.layout.card_view, this)
    }

    private val sideIndicator: TextView = findViewById(R.id.side)
    private val titleView: TextView = findViewById(R.id.title)
    private val descriptionView: TextView = findViewById(R.id.description)
    private val imageView: ImageView = findViewById(R.id.image)
    private val cardBody: ConstraintLayout = findViewById(R.id.cardBody)
    private var cardColor: Int = Color.WHITE
    private var cardSide: Int = 0

    init {
        setupAttributes(attrs)
        cardBody.setBackgroundColor(cardColor)
    }

    var cardModel: CardModel? = null
        set(value) {
            field = value
            cardModel?.let {
                sideIndicator.text = if (cardSide == 0) context.getText(R.string.front)
                    else context.getText(R.string.back)

                titleView.text = it.title ?: ""
                titleView.visibility = if (it.title.isNullOrBlank()) View.GONE else View.VISIBLE

                descriptionView.text = it.description ?: ""
                descriptionView.visibility = if (it.description.isNullOrBlank()) View.GONE else View.VISIBLE

                it.imageRes?.let {
                    imageView.setImageResource(it)
                }
                imageView.visibility = if (it.imageRes == null) View.GONE else View.VISIBLE
            }
        }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CardView,
                0, 0)
        cardSide = typedArray.getInt(R.styleable.CardView_side, 0)
        cardColor = typedArray.getColor(R.styleable.CardView_cardBackgroundColor, Color.WHITE)
        typedArray.recycle()
    }
}


