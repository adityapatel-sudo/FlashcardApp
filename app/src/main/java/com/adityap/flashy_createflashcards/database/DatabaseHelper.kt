package com.adityap.flashy_createflashcards.database

import com.adityap.flashy_createflashcards.models.DeckModel
import com.adityap.flashy_createflashcards.models.FlashcardModel

/**
 * Interface for the Database helper that defines the functions for interacting with database.
 * In future having this interface will allow us to change from local database (sql-lite) to
 * cloud based db.
 */

interface DatabaseHelper {
    fun addDeck(deckModel: DeckModel): Long
    fun addFlashcard(flashcard: FlashcardModel, deckId: Long)
    fun deleteFlashcard(flashcardId: Int)
    fun deleteDeck(deckId: Int)
    fun readDeck(): List<DeckModel>
    fun readFlashcards(deckId: Int?): List<FlashcardModel>
}

enum class Version {
    V1, V2
}