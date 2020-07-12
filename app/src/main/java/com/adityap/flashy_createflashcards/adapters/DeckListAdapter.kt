package com.adityap.flashy_createflashcards.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.adityap.flashy_createflashcards.R
import com.adityap.flashy_createflashcards.models.DeckModel
import kotlinx.android.synthetic.main.custom_deckname_item_view.view.*

class DeckListAdapter(private val mContext: Context, var deckModels: List<DeckModel>) : BaseAdapter() {

    override fun getCount(): Int {
        return deckModels.size
    }

    override fun getItem(position: Int): Any {
        return deckModels[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val returnView = convertView ?: layoutInflater.inflate(R.layout.custom_deckname_item_view, null)
        returnView.let {
            it.textview_deckName.text = deckModels[position].deckName
            it.textview_deckDescription.text = deckModels[position].deckDescription
        }

        return returnView
    }

}