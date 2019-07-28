package com.adityap.flashy_createflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    Button cancelAddCard;
    Button buttonAddCardDialog;
    Button beginCardPlaythrough;
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
//        mEditTextAddCardWord = findViewById(R.id.editTextAddCardWord);
//        mEditTextAddCardDefinition = findViewById(R.id.editTextAddCardDefinition);
        buttonAddCardDialog = findViewById(R.id.buttonAddCard);

        mFlashcardDatabaseHelper = new FlashcardDatabaseHelper(this);
        mFlashcardDatabaseHelper.getReadableDatabase();
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        final DeckModel deckModel = bundle.getParcelable("Deck");

//        List<DeckModel> list = mFlashcardDatabaseHelper.readDeck();
//        DeckModel deck = list.get(deckId-1);
//        String deckName = deck.getDeckName();
//        String deckDescription = deck.getDeckDescription();

        mTextViewDeckName.setText(deckModel.getDeckName());
        mTextViewDeckDescription.setText(deckModel.getDeckDescription());



        buttonAddCardDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ReviewDeckActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_card, null);

                Button buttonAddCard = mView.findViewById(R.id.buttonAddCard);

                final EditText mEditTextAddCardWord;
                final EditText mEditTextAddCardDefinition;
//
//                mEditTextAddCardWord = findViewById(R.id.addCardWordEditText);
//                mEditTextAddCardDefinition = findViewById(R.id.addCardDefinitionEditText);
                mEditTextAddCardWord = mView.findViewById(R.id.addCardWordEditText);
                mEditTextAddCardDefinition = mView.findViewById(R.id.addCardDefinitionEditText);

                mBuilder.setView(mView);
               final AlertDialog dialog = mBuilder.create();

                buttonAddCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("click","Click Successful");

                        String cardWord = mEditTextAddCardWord.getText().toString();
                        String cardDefinition = mEditTextAddCardDefinition.getText().toString();
                        int deckIdForCard = deckModel.getId();


                        mFlashcardDatabaseHelper.addFlashcard(new FlashcardModel(cardWord,cardDefinition,deckIdForCard));

                        Toast toast=Toast.makeText(getApplicationContext(),"Card Entered",Toast.LENGTH_SHORT);
                        toast.show();

                        mCardModelList = mFlashcardDatabaseHelper.readFlashcards(deckIdForCard);
                        mCardListAdapter.setCardModels(mCardModelList);
                        mCardListAdapter.notifyDataSetChanged();
                        Log.i("flashcard","flashcard sent to db");

                        dialog.dismiss();
                        Log.i("finish","function finished");

                    }
                });
                cancelAddCard = mView.findViewById(R.id.cancelAddCard);
                cancelAddCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        //the next few lines are about creating a listview to view the cards in the deck
        mCardModelList = mFlashcardDatabaseHelper.readFlashcards(deckModel.getId());
        mCardListAdapter = new CardListAdapter(this,mCardModelList);
        listViewCards.setAdapter(mCardListAdapter);

        beginCardPlaythrough = this.findViewById(R.id.playDeckButton);
        beginCardPlaythrough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewDeckActivity.this, PlayCardActivity.class);
                intent.putExtra("Deck",deckModel);
                startActivity(intent);
            }
        });


    }




}
