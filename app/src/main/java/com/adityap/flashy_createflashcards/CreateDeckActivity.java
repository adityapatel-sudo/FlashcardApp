package com.adityap.flashy_createflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.adityap.flashy_createflashcards.models.DeckModel;
import com.adityap.flashy_createflashcards.models.FlashcardModel;

import java.util.List;

public class CreateDeckActivity extends AppCompatActivity {

    private TextView mTextView;
    public FlashcardDatabaseHelper mFlashcardDatabaseHelper;

    private EditText mEditTextDeckNameEnter;
    private EditText mEditTextDeckDescriptionEnter;
    private Button mButtonEnterDeckIntoDatabase;
    private TextView mTextViewDisplayDeckFromDatabase;

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


        mEditTextDeckNameEnter = findViewById(R.id.editTextDeckName);
        mEditTextDeckDescriptionEnter = findViewById(R.id.editTextDescription);

        mButtonEnterDeckIntoDatabase = findViewById(R.id.read_edittext_button);
        mButtonEnterDeckIntoDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //The following adds name and description of deck into the deck table in db
                String editTextDeckName = mEditTextDeckNameEnter.getText().toString();
                String editTextDeckDescription = mEditTextDeckDescriptionEnter.getText().toString();

                mFlashcardDatabaseHelper.addDeck(new DeckModel(editTextDeckName, editTextDeckDescription));

                Intent result = new Intent();
                setResult(RESULT_OK, result);
                CreateDeckActivity.this.finish();
            }
        });



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
