package com.adityap.flashy_createflashcards.database

import android.content.Context

class DatabaseHelperFactory {
    companion object {
        fun getDBHelper(context: Context, version: Version = Version.V1) = when (version) {
            Version.V1, Version.V2 -> FlashcardDatabaseHelper(context)
        }
    }
}