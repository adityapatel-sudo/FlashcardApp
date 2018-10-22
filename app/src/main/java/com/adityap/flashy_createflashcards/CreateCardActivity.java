package com.adityap.flashy_createflashcards;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.adityap.flashy_createflashcards.models.FlashcardModel;

import java.util.List;

public class CreateCardActivity extends AppCompatActivity {

    public FlashcardDatabaseHelper mFlashcardDatabaseHelper;
    public TextView mWordTextView;
    public TextView mDefinitionTextView;
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

        Button endActivityButton = (Button) findViewById(R.id.button);
        endActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent result = new Intent();
                setResult(RESULT_OK, result);
                CreateCardActivity.this.finish();
            }
        });
    }

}
