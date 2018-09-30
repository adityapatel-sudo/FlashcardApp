package com.adityap.flashy_createflashcards;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.adityap.flashy_createflashcards.models.FlashcardModel;

import java.util.List;

public class CreateCardActivity extends AppCompatActivity {

    private FlashcardDatabaseHelper mFlashcardDatabaseHelper;
    private TextView mWordTextView;
    private TextView mDefinitionTextView;
    private EditText mWordEditText;
    private EditText mDefinitionEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        mFlashcardDatabaseHelper = new FlashcardDatabaseHelper(this);

        mFlashcardDatabaseHelper.addFlashcard(new FlashcardModel("Worf", "Derf"));

        mWordTextView = findViewById(R.id.wordTextView);
        mDefinitionTextView = findViewById(R.id.definitionTextView);

        List<FlashcardModel> flashcardModels = mFlashcardDatabaseHelper.readFlashcards();
        mWordTextView.setText(flashcardModels.get(0).getWord());
        mDefinitionTextView.setText(flashcardModels.get(0).getDefinition());

    }

}
