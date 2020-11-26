package com.adityap.flashy_createflashcards.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.adityap.flashy_createflashcards.R
import com.adityap.flashy_createflashcards.card.FlipCardView
import com.adityap.flashy_createflashcards.models.FlashcardModel

class CustomPagerAdapter(private val context: Context, private val mFlashcards: List<FlashcardModel>) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val flashCardView = FlipCardView(context)
        flashCardView.cardPair = mFlashcards[position].toCardPair()
        container.addView(flashCardView)
        return flashCardView
    }

    override fun getCount(): Int {
        return mFlashcards.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val flashCardView = `object` as ConstraintLayout
        container.removeView(flashCardView)
    }

}
