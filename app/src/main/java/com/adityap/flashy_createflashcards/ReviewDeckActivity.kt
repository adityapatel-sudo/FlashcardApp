package com.adityap.flashy_createflashcards

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adityap.flashy_createflashcards.adapters.CardListAdapter
import com.adityap.flashy_createflashcards.adapters.RecycleViewDeckCardListAdapter
import com.adityap.flashy_createflashcards.dialogs.AddCardDialog
import com.adityap.flashy_createflashcards.models.DeckModel
import com.adityap.flashy_createflashcards.models.FlashcardModel
import kotlinx.android.synthetic.main.activity_review_deck.*

class ReviewDeckActivity : AppCompatActivity() {
    companion object{
        const val TAG = "ReviewDeckActivity"
    }
    lateinit var mFlashcardDatabaseHelper: FlashcardDatabaseHelper

    lateinit var mCardModelList:  MutableList<FlashcardModel>
    lateinit var mCardListAdapter: CardListAdapter


    private lateinit var recyclerViewCards: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var deleteIcon: Drawable
    private var swipeBackgroundColor: ColorDrawable = ColorDrawable(Color.parseColor("#ff0000"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_deck)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var bundle: Bundle = intent.extras
        val deckModel: DeckModel = bundle.getParcelable("Deck")

        mCardModelList = mutableListOf()
        mFlashcardDatabaseHelper = FlashcardDatabaseHelper(this)
        mCardModelList.addAll(mFlashcardDatabaseHelper.readFlashcards(deckModel.id))

        textViewDeckName.text = deckModel.deckName
        textViewDeckDescription.text = deckModel.deckDescription

        viewManager = LinearLayoutManager(this)
        viewAdapter = RecycleViewDeckCardListAdapter(this,mCardModelList,deckModel.id)

        deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_delete_24)!!

        recyclerViewCards = findViewById<RecyclerView>(R.id.recycler_view_cards).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        buttonAddCard.setOnClickListener(View.OnClickListener {
            showAddCardDialog(deckModel.id)

        })
        playDeckButton.setOnClickListener(View.OnClickListener {
            startPlayDeckActivity(deckModel.id)
        })

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT  or ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, postiion: Int) {
                (viewAdapter as RecycleViewDeckCardListAdapter).removeItem(viewHolder as RecycleViewDeckCardListAdapter.CardHolder)
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val itemView = viewHolder.itemView

                val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) /2
                if(dX>0){
                    swipeBackgroundColor.setBounds(itemView.left,itemView.top,dX.toInt(),itemView.bottom)
                    deleteIcon.setBounds(itemView.left + iconMargin,
                            itemView.top + iconMargin,
                            itemView.left + iconMargin + deleteIcon.intrinsicWidth,
                            itemView.bottom - iconMargin )
                }else {
                    swipeBackgroundColor.setBounds(itemView.right + dX.toInt(),itemView.top, itemView.right,itemView.bottom)
                    deleteIcon.setBounds(itemView.right-iconMargin-deleteIcon.intrinsicWidth,
                            itemView.top + iconMargin,
                            itemView.right-iconMargin,
                            itemView.bottom - iconMargin)
                }
                swipeBackgroundColor.draw(c)
                c.save()

                if(dX > 0 )
                    c.clipRect(itemView.left,itemView.top, dX.toInt(), itemView.bottom)
                else
                    c.clipRect(itemView.right+dX.toInt(),itemView.top,itemView.right, itemView.bottom)
                deleteIcon.draw(c)

                c.restore()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewCards)

    }

    private fun showAddCardDialog(deckId: Int) {
        val dialog = AddCardDialog.getInstance(deckId)
        dialog.listener = object : AddCardDialog.Listener{
            override fun onSave() {
                mCardModelList.clear()
                mCardModelList.addAll(mFlashcardDatabaseHelper.readFlashcards(deckId))
                viewAdapter.notifyDataSetChanged()
            }

        }
        dialog.show(supportFragmentManager, TAG)
    }
    private fun startPlayDeckActivity(deckId: Int){
        val intent = Intent(this, PlayCardActivity::class.java)
        intent.putExtra("Deck", deckId)
        startActivity(intent)
    }
}
