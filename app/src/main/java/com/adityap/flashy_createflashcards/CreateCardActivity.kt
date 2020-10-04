package com.adityap.flashy_createflashcards

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adityap.flashy_createflashcards.database.DatabaseHelper
import com.adityap.flashy_createflashcards.database.DatabaseHelperFactory
import kotlinx.android.synthetic.main.activity_create_card.*

class CreateCardActivity : AppCompatActivity() {
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)
        databaseHelper = DatabaseHelperFactory.getDBHelper(this)

        button.setOnClickListener {
            // Write Function to add to the database

            val result = Intent()
            setResult(Activity.RESULT_OK, result)
            finish()
        }
    }
}