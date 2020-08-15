package com.adityap.flashy_createflashcards.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.adityap.flashy_createflashcards.FlashcardDatabaseHelper
import com.adityap.flashy_createflashcards.R
import com.adityap.flashy_createflashcards.models.FlashcardModel
import kotlinx.android.synthetic.main.custom_cardlist_item_view.view.*

class CardListAdapter(private val mContext: Context, var flashcardModelList: MutableList<FlashcardModel>, val deckId:Int) : BaseAdapter() {
    lateinit var mFlashcardDatabaseHelper: FlashcardDatabaseHelper


    override fun getCount(): Int {
        return flashcardModelList.size
    }

    override fun getItem(position: Int): Any {
        return flashcardModelList[position]
    }

    override fun getItemId(position: Int): Long {
        return flashcardModelList[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val returnView = convertView ?: layoutInflater.inflate(R.layout.custom_cardlist_item_view, null)
        returnView.let {
            it.cardName.text = flashcardModelList[position].word
            it.cardDescription.text = flashcardModelList[position].definition
        }


        return returnView
    }
}