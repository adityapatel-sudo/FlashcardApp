package com.adityap.flashy_createflashcards;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.adityap.flashy_createflashcards.adapters.CustomPagerAdapter;
import com.adityap.flashy_createflashcards.models.DeckModel;
import com.adityap.flashy_createflashcards.models.FlashcardModel;

import java.util.List;

public class PlayCardActivity extends AppCompatActivity {

    FlashcardDatabaseHelper mFlashcardDatabaseHelper;
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_card);

        mFlashcardDatabaseHelper = new FlashcardDatabaseHelper(this);
        mFlashcardDatabaseHelper.getReadableDatabase();

        Bundle bundle;
        bundle = getIntent().getExtras();
        final DeckModel deckModel = bundle.getParcelable("Deck");

        List<FlashcardModel> listOfCards = mFlashcardDatabaseHelper.readFlashcards(deckModel.getId());

        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(new CustomPagerAdapter(this,listOfCards));
    }
}
