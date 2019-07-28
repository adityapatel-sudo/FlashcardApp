package com.adityap.flashy_createflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.adityap.flashy_createflashcards.adapters.DeckListAdapter;
import com.adityap.flashy_createflashcards.models.DeckModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    FlashcardDatabaseHelper mFlashcardDatabaseHelper;
    TextView displayDeckTextView;
    ListView deckListView;
    DeckListAdapter mDeckListAdapter;
    List<DeckModel> mDeckModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFlashcardDatabaseHelper = new FlashcardDatabaseHelper(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MainActivity.this, CreateDeckActivity.class);
                startActivityForResult(intent,123);
            }
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, CreateCardActivity.class);
//                startActivityForResult(intent, 123);
//            }
        });

        mDeckModelList = mFlashcardDatabaseHelper.readDeck();
        deckListView = findViewById(R.id.listview);
        mDeckListAdapter = new DeckListAdapter(this, mDeckModelList);
        deckListView.setAdapter(mDeckListAdapter);
        deckListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, ReviewDeckActivity.class);
                intent.putExtra("Deck",mDeckModelList.get(position));
                startActivity(intent);
            }
        });

//        displayDeckTextView = findViewById(R.id.text_view);
//
//        List<DeckModel> mDeckModelList = mFlashcardDatabaseHelper.readDeck();
//        DeckModel lastDeck = mDeckModelList.get(mDeckModelList.size() - 1);
//        displayDeckTextView.setText(lastDeck.getDeckName() + " " + lastDeck.getDeckDescription());


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 123) {
            Log.d("AdityaDebug","Result recieved");

            mDeckModelList = mFlashcardDatabaseHelper.readDeck();
            mDeckListAdapter.setDeckModels(mDeckModelList);
            mDeckListAdapter.notifyDataSetChanged();

        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == 123) {
//            // show newly added data
//            List<FlashcardModel>  mDeckModelList = mFlashcardDatabaseHelper.readFlashcards();
//            FlashcardModel lastCard = mDeckModelList.get(mDeckModelList.size() - 1);
//            mTextView.setText(lastCard.getWord() + " " + lastCard.getDefinition());
//        }
//    }
}
