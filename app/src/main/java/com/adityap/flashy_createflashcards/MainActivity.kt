package com.adityap.flashy_createflashcards

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.adityap.flashy_createflashcards.adapters.DeckListAdapter
import com.adityap.flashy_createflashcards.models.DeckModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    //I am going to create a branch called BigBranch


    var mFlashcardDatabaseHelper: FlashcardDatabaseHelper? = null
    var mDeckListAdapter: DeckListAdapter? = null
    var mDeckModelList: List<DeckModel>? = null

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

        mDeckModelList = mFlashcardDatabaseHelper!!.readDeck()
        mDeckListAdapter = DeckListAdapter(this, mDeckModelList!!)
        listview.setAdapter(mDeckListAdapter)
        listview.setOnItemClickListener(OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ReviewDeckActivity::class.java)
            intent.putExtra("Deck", mDeckModelList!![position])
            startActivity(intent)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 123) {
            Log.d("AdityaDebug", "Result recieved")
            mDeckModelList = mFlashcardDatabaseHelper!!.readDeck()
            mDeckListAdapter!!.deckModels = mDeckModelList!!
            mDeckListAdapter!!.notifyDataSetChanged()
        }
    }
}
