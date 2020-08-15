package com.adityap.flashy_createflashcards

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adityap.flashy_createflashcards.adapters.DeckListAdapter
import com.adityap.flashy_createflashcards.adapters.DeckListAdapterRecycler
import com.adityap.flashy_createflashcards.adapters.RecycleViewDeckCardListAdapter
import com.adityap.flashy_createflashcards.models.DeckModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    //I am going to create a branch called BigBranch
    lateinit var mFlashcardDatabaseHelper: FlashcardDatabaseHelper
    lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var mDeckModelList: MutableList<DeckModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        mFlashcardDatabaseHelper = FlashcardDatabaseHelper(this)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton

        fab.setOnClickListener {
            startActivityForResult(Intent(this, CreateDeckActivity::class.java), 123)
        }

        mDeckModelList = mutableListOf()
        mDeckModelList.addAll(mFlashcardDatabaseHelper.readDeck())

        viewManager = LinearLayoutManager(this)
        viewAdapter = DeckListAdapterRecycler(this, mDeckModelList)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

/*        mDeckListAdapter = DeckListAdapter(this, mDeckModelList!!)
        listview.adapter = mDeckListAdapter*/
/*        listview.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ReviewDeckActivity::class.java)
            intent.putExtra("Deck", mDeckModelList!![position])
            startActivity(intent)
        }*/
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, postiion: Int) {
                (viewAdapter as DeckListAdapterRecycler).removeItem(viewHolder as DeckListAdapterRecycler.CardHolder)
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 123) {
            mDeckModelList.clear()
            mDeckModelList.addAll(mFlashcardDatabaseHelper.readDeck())
            viewAdapter.notifyDataSetChanged()
        }
    }
}
