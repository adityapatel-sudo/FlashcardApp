package com.adityap.flashy_createflashcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adityap.flashy_createflashcards.models.FlashcardModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aditya on 9/29/2018.
 */

public class FlashcardDatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "FLASHCARD_DATABASE";

    // Table columns
    public static final String _ID = "_id";
    public static final String WORD = "word";
    public static final String DEFINITION = "definition";

    // Database Information
    static final String DB_NAME = "FLASHCARDS.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + WORD + " TEXT NOT NULL, " + DEFINITION + " TEXT NOT NULL);";



    public FlashcardDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addFlashcard(FlashcardModel flashcard){
        ContentValues values = new ContentValues();

        values.put(WORD, flashcard.getWord());
        values.put(DEFINITION, flashcard.getDefinition());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<FlashcardModel> readFlashcards(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        List<FlashcardModel> flashcardList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                FlashcardModel flashcard = new FlashcardModel();
                flashcard.setmId(cursor.getInt(cursor.getColumnIndex(_ID)));
                flashcard.setWord(cursor.getString(cursor.getColumnIndex(WORD)));
                flashcard.setDefinition(cursor.getString(cursor.getColumnIndex(DEFINITION)));

                // Add book to books
                flashcardList.add(flashcard);
            } while (cursor.moveToNext());
        }

        return flashcardList;
    }
}
