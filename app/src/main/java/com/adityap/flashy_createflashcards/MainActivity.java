package com.adityap.flashy_createflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.adityap.flashy_createflashcards.adapters.DeckListAdapter;
import com.adityap.flashy_createflashcards.models.DeckModel;
import com.adityap.flashy_createflashcards.models.FlashcardModel;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    FlashcardDatabaseHelper mFlashcardDatabaseHelper;
    TextView displayDeckTextView;
    ListView deckListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFlashcardDatabaseHelper = new FlashcardDatabaseHelper(this);

        mTextView = findViewById(R.id.text_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateDeckActivity.class);
                startActivity(intent);
            }
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, CreateCardActivity.class);
//                startActivityForResult(intent, 123);
//            }
        });

        List<DeckModel> list = mFlashcardDatabaseHelper.readDeck();
        deckListView = findViewById(R.id.listview);
        DeckListAdapter deckListAdapter = new DeckListAdapter(this,list );
        deckListView.setAdapter(deckListAdapter);
        deckListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, ReviewDeckActivity.class);
                intent.putExtra("DeckId",position);
                startActivityForResult(intent,123);
            }
        });

//        displayDeckTextView = findViewById(R.id.text_view);
//
//        List<DeckModel> list = mFlashcardDatabaseHelper.readDeck();
//        DeckModel lastDeck = list.get(list.size() - 1);
//        displayDeckTextView.setText(lastDeck.getDeckName() + " " + lastDeck.getDeckDescription());


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 123) {
            MainActivity.this.finish();
            startActivity(getIntent());
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == 123) {
//            // show newly added data
//            List<FlashcardModel>  list = mFlashcardDatabaseHelper.readFlashcards();
//            FlashcardModel lastCard = list.get(list.size() - 1);
//            mTextView.setText(lastCard.getWord() + " " + lastCard.getDefinition());
//        }
//    }
}
