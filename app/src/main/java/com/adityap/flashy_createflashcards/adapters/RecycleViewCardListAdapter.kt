package com.adityap.flashy_createflashcards.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.adityap.flashy_createflashcards.database.FlashcardDatabaseHelper
import com.adityap.flashy_createflashcards.R
import com.adityap.flashy_createflashcards.database.DatabaseHelper
import com.adityap.flashy_createflashcards.database.DatabaseHelperFactory
import com.adityap.flashy_createflashcards.models.FlashcardModel

class RecycleViewDeckCardListAdapter(
        private val mContext: Context, 
        var flashcardModelList: MutableList<FlashcardModel>, 
        var deckid: Int) : RecyclerView.Adapter<RecycleViewDeckCardListAdapter.CardHolder>() {
    
    companion object {
        private const val TAG: String = "Adapter"
    }
    var onEmptyStateChange :(isEmpty: Boolean)->Unit = {}

    lateinit var databaseHelper: DatabaseHelper

    class CardHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)
    
    init {
        registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onChanged() {
                Log.d(TAG, "onChanges is called" )
                super.onChanged()
                onEmptyStateChange.invoke(flashcardModelList.size == 0)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewDeckCardListAdapter.CardHolder {
        val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_cardlist_item_view, parent, false) as CardView
        return CardHolder(cardView)

    }

    override fun getItemCount(): Int {
        return flashcardModelList.size
    }

    override fun onBindViewHolder(holder: RecycleViewDeckCardListAdapter.CardHolder, position: Int) {
        holder.run {
            cardView.findViewById<TextView>(R.id.cardName).apply {
                text = flashcardModelList[position].word
            }
            cardView.findViewById<TextView>(R.id.cardDescription).apply {
                text = flashcardModelList[position].definition
            }
        }
    }

    fun removeItem(viewHolder: CardHolder) {
        databaseHelper = DatabaseHelperFactory.getDBHelper(mContext)
        databaseHelper.deleteFlashcard(flashcardModelList[viewHolder.adapterPosition].id)

        flashcardModelList.clear()
        flashcardModelList.addAll(databaseHelper.readFlashcards(deckid))
        notifyDataSetChanged()
    }
}
