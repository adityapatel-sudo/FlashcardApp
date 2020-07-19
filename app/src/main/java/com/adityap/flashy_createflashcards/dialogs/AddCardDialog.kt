package com.adityap.flashy_createflashcards.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.adityap.flashy_createflashcards.FlashcardDatabaseHelper
import com.adityap.flashy_createflashcards.R
import com.adityap.flashy_createflashcards.adapters.CardListAdapter
import com.adityap.flashy_createflashcards.models.FlashcardModel
import kotlinx.android.synthetic.main.dialog_add_card.*

class AddCardDialog : DialogFragment() {
    var mCardModelList: List<FlashcardModel>? = null
    var mCardListAdapter: CardListAdapter? = null
    lateinit var flashcardDatabaseHelper: FlashcardDatabaseHelper


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
        flashcardDatabaseHelper = FlashcardDatabaseHelper(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val deckId = requireNotNull(arguments?.getInt(DECK_ID_KEY))
            builder.setView(inflater.inflate(R.layout.dialog_add_card, null))
                    .setPositiveButton("add card",
                            DialogInterface.OnClickListener { dialog, id ->

                                val addCardWordEditText = view?.findViewById<EditText>(R.id.addCardWordEditText)
                                val addCardDefinitionEditText = view?.findViewById<EditText>(R.id.addCardDefinitionEditText)


                                val cardWord = addCardWordEditText?.text.toString()
                                val cardDefinition = addCardDefinitionEditText?.text.toString()
                                val deckIdForCard = deckId;

                                flashcardDatabaseHelper.addFlashcard(FlashcardModel(deckIdForCard, cardWord, cardDefinition))
                                val toast = Toast.makeText(context, "Card Entered", Toast.LENGTH_SHORT)
                                toast.show()
                                mCardModelList = flashcardDatabaseHelper.readFlashcards(deckIdForCard)
                                mCardListAdapter!!.setCardModels(mCardModelList!!)
                                mCardListAdapter!!.notifyDataSetChanged()
                                Log.i("flashcard", "flashcard sent to db")
                                dialog.dismiss()
                                Log.i("finish", "function finished")

                                dismiss();
                            })
                    .setNegativeButton("cancel",
                            DialogInterface.OnClickListener { dialog, id ->
                                dismiss();

                            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}
