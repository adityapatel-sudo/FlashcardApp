package com.adityap.flashy_createflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.adityap.flashy_createflashcards.models.FlashcardModel;

import java.util.List;

public class CreateDeckActivity extends AppCompatActivity {

    TextView mTextView;
    FlashcardDatabaseHelper mFlashcardDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deck);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setSupportActionBar(toolbar);

        mFlashcardDatabaseHelper = new FlashcardDatabaseHelper(this);
        mFlashcardDatabaseHelper.addFlashcard(new FlashcardModel("Worf", "Derf"));

        mTextView = findViewById(R.id.textView);
       // mFlashcardDatabaseHelper = new FlashcardDatabaseHelper(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateDeckActivity.this, CreateCardActivity.class);
                startActivityForResult(intent, 123);
           }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 123) {
            // show newly added data
            List<FlashcardModel> list = mFlashcardDatabaseHelper.readFlashcards();
            FlashcardModel lastCard = list.get(list.size() - 1);
            mTextView.setText(lastCard.getWord() + " " + lastCard.getDefinition());
        }
    }

}
