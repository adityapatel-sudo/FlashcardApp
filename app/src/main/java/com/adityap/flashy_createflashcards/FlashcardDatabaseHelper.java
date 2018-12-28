package com.adityap.flashy_createflashcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.adityap.flashy_createflashcards.models.FlashcardModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aditya on 9/29/2018.
 */

public class FlashcardDatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String FLASHCARD_TABLE = "FLASHCARD_TABLE";
    public static final String DECK_TABLE = "DECK_TABLE";
    // Table columns
    public static final String _ID = "_id";
    public static final String WORD = "word";
    public static final String DEFINITION = "definition";
    public static final String DECK_ID = "deck_id";
    public static final String DECK_NAME = "deck_name";
    public static final String DECK_DESCRIPTION = "deck_description";

    // Database Information
    static final String DB_NAME = "FLASHCARDS.DB";

    // database version
    static final int DB_VERSION = 1;

    private static final String CREATE_TABLE_DECK = "create table " + DECK_TABLE + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DECK_NAME + " TEXT NOT NULL, " + DECK_DESCRIPTION + " TEXT NOT NULL);";
    // Creating table query
    private static final String CREATE_TABLE_CARD = "create table " + FLASHCARD_TABLE + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + WORD + " TEXT NOT NULL, " + DEFINITION + " TEXT NOT NULL, " + DECK_ID +" INTEGER , FOREIGN KEY (" + DECK_ID + ") REFERENCES " + DECK_TABLE + " (" + _ID + "));";



    //private static final String FOREIGN_KEY_LINK = "ALTER " + CREATE_TABLE_CARD + " ADD FOREIGN KEY (" + DECK_ID + ") REFERENCES " + CREATE_TABLE_DECK + " (" + _ID + ");";

    public FlashcardDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("debugKey", CREATE_TABLE_DECK);
        sqLiteDatabase.execSQL(CREATE_TABLE_DECK);

        Log.d("debugKey", CREATE_TABLE_CARD);
        sqLiteDatabase.execSQL(CREATE_TABLE_CARD);

        //sqLiteDatabase.execSQL(FOREIGN_KEY_LINK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addFlashcard(FlashcardModel flashcard){
        ContentValues values = new ContentValues();

        values.put(WORD, flashcard.getWord());
        values.put(DEFINITION, flashcard.getDefinition());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(FLASHCARD_TABLE, null, values);
        db.close();
    }

    public List<FlashcardModel> readFlashcards(){
        String query = "SELECT * FROM " + FLASHCARD_TABLE;
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
