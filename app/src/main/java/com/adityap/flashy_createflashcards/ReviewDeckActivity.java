package com.adityap.flashy_createflashcards;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.adityap.flashy_createflashcards.models.DeckModel;

import java.util.List;

public class ReviewDeckActivity extends AppCompatActivity {
    TextView textViewDeckName;
    TextView textViewDeckDescription;
    ListView listViewCards;
    EditText editTextAddCardName;
    EditText editTextAddCardDescription;
    Button buttonAddCard;
    FlashcardDatabaseHelper mFlashcardDatabaseHelper;
    DeckModel deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_deck);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewDeckName = findViewById(R.id.editTextAddCardName);
        textViewDeckDescription = findViewById(R.id.editTextAddCardDescription);
        listViewCards = findViewById(R.id.listViewCards);
        editTextAddCardName = findViewById(R.id.editTextAddCardName);
        editTextAddCardDescription = findViewById(R.id.editTextAddCardDescription);
        buttonAddCard = findViewById(R.id.buttonAddCard);

        mFlashcardDatabaseHelper = new FlashcardDatabaseHelper(this);
        mFlashcardDatabaseHelper.getReadableDatabase();
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        int deckId = bundle.getInt("DeckId");

        List<DeckModel> list = mFlashcardDatabaseHelper.readDeck();
        DeckModel deck = list.get(deckId);
        String deckName = deck.getDeckName();
        String deckDescription = deck.getDeckName();

        textViewDeckName.setText(deckName);
        textViewDeckDescription.setText(deckDescription);

    }


}
