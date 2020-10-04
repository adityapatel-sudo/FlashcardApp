package com.adityap.flashy_createflashcards.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.adityap.flashy_createflashcards.R
import com.adityap.flashy_createflashcards.database.DatabaseHelper
import com.adityap.flashy_createflashcards.database.DatabaseHelperFactory
import com.adityap.flashy_createflashcards.models.FlashcardModel

class AddCardDialog : DialogFragment() {
    interface Listener {
        fun onSave()
    }

    var listener: Listener? = null
    lateinit var databaseHelper: DatabaseHelper


    companion object {
        var DECK_ID_KEY = "deck_id_key"
        fun getInstance(deckId: Int): AddCardDialog =
                AddCardDialog().apply {
                    arguments = Bundle().apply {
                        putInt(DECK_ID_KEY, deckId)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = DatabaseHelperFactory.getDBHelper(context!!)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val deckId = requireNotNull(arguments?.getInt(DECK_ID_KEY))

            val dialogView = inflater.inflate(R.layout.dialog_add_card, null)

            val addCardWordEditText = dialogView?.findViewById<EditText>(R.id.addCardWordEditText)
            val addCardDefinitionEditText = dialogView?.findViewById<EditText>(R.id.addCardDefinitionEditText)


            builder.setView(dialogView)
                    .setPositiveButton("add card",
                            DialogInterface.OnClickListener { dialog, id ->
                                val cardWord = addCardWordEditText?.text.toString()
                                val cardDefinition = addCardDefinitionEditText?.text.toString()

                                databaseHelper.addFlashcard(FlashcardModel(
                                        word = cardWord,
                                        definition = cardDefinition), deckId.toLong())
                                listener?.onSave()

                                dialog.dismiss()

                            })
                    .setNegativeButton("cancel",
                            DialogInterface.OnClickListener { dialog, id ->
                                dismiss()
                            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }
}
