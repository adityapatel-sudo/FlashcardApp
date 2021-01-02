package com.adityap.flashy_createflashcards

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adityap.flashy_createflashcards.adapters.DeckRecyclerListAdapter
import com.adityap.flashy_createflashcards.database.DatabaseHelper
import com.adityap.flashy_createflashcards.database.DatabaseHelperFactory
import com.adityap.flashy_createflashcards.models.DeckModel
import com.adityap.flashy_createflashcards.models.LOGGING_TAG
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment() {

    lateinit var databaseHelper: DatabaseHelper
    lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var swipeBackgroundColor: ColorDrawable = ColorDrawable(Color.parseColor("#d11a2a"))
    private lateinit var deleteIcon: Drawable

    lateinit var mDeckModelList: MutableList<DeckModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            LayoutInflater.from(context).inflate(R.layout.home_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.d(TAG, "onViewCreated")
        databaseHelper = DatabaseHelperFactory.getDBHelper(context!!)
        val fab = view.findViewById<View>(R.id.fab) as FloatingActionButton


        fab.setOnClickListener {
            startActivityForResult(Intent(activity, CreateDeckActivity::class.java), 123)
        }

        mDeckModelList = mutableListOf()
        mDeckModelList.addAll(databaseHelper.readDeck())

        viewManager = LinearLayoutManager(context)
        viewAdapter = DeckRecyclerListAdapter(context!!, mDeckModelList)

        deleteIcon = ContextCompat.getDrawable(context!!, R.drawable.ic_baseline_delete_24)!!

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, postiion: Int) {
                (viewAdapter as DeckRecyclerListAdapter).removeItem(viewHolder as DeckRecyclerListAdapter.CardHolder)
            }

            override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean) {

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
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 123) {
            mDeckModelList.clear()
            mDeckModelList.addAll(databaseHelper.readDeck())
            viewAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        val TAG = LOGGING_TAG + "_" + HomeFragment.javaClass
    }
}
