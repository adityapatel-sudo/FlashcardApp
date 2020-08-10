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
import com.adityap.flashy_createflashcards.models.FlashcardModel

class CustomPagerAdapter(private val mContext: Context, private val mFlashcards: List<FlashcardModel>) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val flashCardView = LayoutInflater.from(mContext).inflate(R.layout.flashcard_item_layout, container, false) as ConstraintLayout
        val frontText = flashCardView.findViewById<TextView>(R.id.frontTextView)
        val backText = flashCardView.findViewById<TextView>(R.id.backTextView)

        frontText.text = mFlashcards[position].word
        backText.text = mFlashcards[position].definition
        container.addView(flashCardView)
        backText.visibility = View.GONE
        frontText.setBackgroundColor(Color.rgb(161, 239, 255))


        frontText.setOnClickListener {
            frontText.visibility = View.GONE
            backText.visibility = View.VISIBLE
        }
        backText.setOnClickListener {
            backText.visibility = View.GONE
            frontText.visibility = View.VISIBLE
        }
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