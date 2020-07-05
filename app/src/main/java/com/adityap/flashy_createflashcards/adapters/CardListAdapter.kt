package com.adityap.flashy_createflashcards.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.adityap.flashy_createflashcards.R
import com.adityap.flashy_createflashcards.models.FlashcardModel
import kotlinx.android.synthetic.main.custom_deckname_item_view.view.*

class CardListAdapter(private val mContext: Context, var flashcardModel: List<FlashcardModel>) : BaseAdapter() {

    fun setCardModels(mFlashcardModel: List<FlashcardModel>) {
        flashcardModel = mFlashcardModel
    }

    override fun getCount(): Int {
        return flashcardModel.size
    }

    override fun getItem(position: Int): Any {
        return flashcardModel[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val returnView = convertView ?: layoutInflater.inflate(R.layout.custom_deckname_item_view, null)
        returnView.let {
            it.textview_deckName.text = flashcardModel[position].definition
            it.textview_deckDescription.text = flashcardModel[position].word
        }

        return returnView
    }


}