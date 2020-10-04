package com.adityap.flashy_createflashcards.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.adityap.flashy_createflashcards.models.DeckModel
import com.adityap.flashy_createflashcards.models.FlashcardModel
import java.util.*

/**
 * Created by Aditya on 9/29/2018.
 */
class FlashcardDatabaseHelper(context: Context?) :
        SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), DatabaseHelper {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_DECK)
        sqLiteDatabase.execSQL(CREATE_TABLE_CARD)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {}

    override fun addDeck(deckModel: DeckModel): Long {
        val deckValues = ContentValues()
        deckValues.put(DECK_NAME, deckModel.deckName)
        deckValues.put(DECK_DESCRIPTION, deckModel.deckDescription)
        val db = this.writableDatabase
        val id = db.insert(DECK_TABLE, null, deckValues)
        db.close()

        return id;
    }

    override fun addFlashcard(flashcard: FlashcardModel, deckId: Long) {
        val cardValues = ContentValues()
        cardValues.put(WORD, flashcard.word)
        cardValues.put(DEFINITION, flashcard.definition)
        cardValues.put(DECK_ID, deckId)

        val db = this.writableDatabase
        db.insert(FLASHCARD_TABLE, null, cardValues)
        db.close()
    }

    override fun deleteFlashcard(flashcardId: Int) {
        val db = this.writableDatabase
        db.delete(FLASHCARD_TABLE, "$_ID=$flashcardId", null)
        db.close()
    }

    override fun deleteDeck(deckId: Int) {
        val db = this.writableDatabase
        db.delete(DECK_TABLE, "$_ID=$deckId", null)
        db.close()

        val cardDeck = this.readFlashcards(deckId)
        for (element in cardDeck) {
            this.deleteFlashcard(element.id)
        }
    }

    override fun readDeck(): List<DeckModel> {
        val query = "SELECT * FROM $DECK_TABLE"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        val deckList: MutableList<DeckModel> = ArrayList()
        if (cursor.moveToFirst()) {
            do {
                val deck = DeckModel(
                        cursor.getInt(cursor.getColumnIndex(_ID)),
                        cursor.getString(cursor.getColumnIndex(DECK_NAME)),
                        cursor.getString(cursor.getColumnIndex(DECK_DESCRIPTION)))
                // Add book to books
                deckList.add(deck)
            } while (cursor.moveToNext())
        }
        return deckList
    }

    override fun readFlashcards(deckId: Int?): List<FlashcardModel> {
        val query = "SELECT * FROM $FLASHCARD_TABLE"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        val flashcardList: MutableList<FlashcardModel> = ArrayList()
        if (cursor.moveToFirst()) {
            do {
                val flashcard = FlashcardModel(
                        cursor.getInt(cursor.getColumnIndex(_ID)),
                        cursor.getString(cursor.getColumnIndex(WORD)),
                        cursor.getString(cursor.getColumnIndex(DEFINITION)))

                // Add book to books
                if (deckId == cursor.getInt(cursor.getColumnIndex(DECK_ID))) {
                    flashcardList.add(flashcard)
                }
            } while (cursor.moveToNext())
        }
        return flashcardList
    }

    companion object {
        // Table Name
        const val FLASHCARD_TABLE = "FLASHCARD_TABLE"
        const val DECK_TABLE = "DECK_TABLE"

        // Table columns
        const val _ID = "_id"
        const val WORD = "word"
        const val DEFINITION = "definition"
        const val DECK_ID = "deck_id"
        const val DECK_NAME = "deck_name"
        const val DECK_DESCRIPTION = "deck_description"

        // Database Information
        const val DB_NAME = "FLASHCARDS.DB"

        // database version
        const val DB_VERSION = 1
        private const val CREATE_TABLE_DECK = ("create table " + DECK_TABLE +
                "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                " " + DECK_NAME + " TEXT NOT NULL, "
                + DECK_DESCRIPTION + " TEXT NOT NULL);")

        // Creating table query
        private const val CREATE_TABLE_CARD = ("create table " + FLASHCARD_TABLE +
                "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORD + " TEXT NOT NULL, "
                + DEFINITION + " TEXT NOT NULL, "
                + DECK_ID + " INTEGER ," +
                " FOREIGN KEY (" + DECK_ID + ") REFERENCES " + DECK_TABLE + " (" + _ID + "));")
    }
}
