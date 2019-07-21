package com.adityap.flashy_createflashcards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adityap.flashy_createflashcards.adapters.CardListAdapter;
import com.adityap.flashy_createflashcards.models.DeckModel;
import com.adityap.flashy_createflashcards.models.FlashcardModel;

import java.util.List;

public class ReviewDeckActivity extends AppCompatActivity {
    TextView mTextViewDeckName;
    TextView mTextViewDeckDescription;
    ListView listViewCards;
    EditText mEditTextAddCardWord;
    EditText mEditTextAddCardDefinition;
    Button buttonAddCard;
    FlashcardDatabaseHelper mFlashcardDatabaseHelper;
    DeckModel deck;
    List<FlashcardModel> mCardModelList;
    CardListAdapter mCardListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_deck);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextViewDeckName = findViewById(R.id.textViewDeckName);
        mTextViewDeckDescription = findViewById(R.id.textViewDeckDescription);
        listViewCards = findViewById(R.id.listViewCards);
        mEditTextAddCardWord = findViewById(R.id.editTextAddCardWord);
        mEditTextAddCardDefinition = findViewById(R.id.editTextAddCardDefinition);
        buttonAddCard = findViewById(R.id.buttonAddCard);

        mFlashcardDatabaseHelper = new FlashcardDatabaseHelper(this);
        mFlashcardDatabaseHelper.getReadableDatabase();
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        final int deckId = bundle.getInt("DeckId");

        List<DeckModel> list = mFlashcardDatabaseHelper.readDeck();
        DeckModel deck = list.get(deckId-1);
        String deckName = deck.getDeckName();
        String deckDescription = deck.getDeckDescription();

        mTextViewDeckName.setText(deckName);
        mTextViewDeckDescription.setText(deckDescription);


        //the following will be a way to add flashcards to this deck

        buttonAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardWord = mEditTextAddCardWord.getText().toString();
                String cardDefinition = mEditTextAddCardDefinition.getText().toString();
                int deckIdForCard = deckId;


                mFlashcardDatabaseHelper.addFlashcard(new FlashcardModel(cardWord,cardDefinition,deckIdForCard));

                Toast toast=Toast.makeText(getApplicationContext(),"Card Entered",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();

                mCardModelList = mFlashcardDatabaseHelper.readFlashcards(deckId);
                mCardListAdapter.setCardModels(mCardModelList);
                mCardListAdapter.notifyDataSetChanged();
            }
        });

        //the next few lines are about creating a listview to view the cards in the deck
        mCardModelList = mFlashcardDatabaseHelper.readFlashcards(deckId);
        mCardListAdapter = new CardListAdapter(this,mCardModelList);
        listViewCards.setAdapter(mCardListAdapter);


    }


}
