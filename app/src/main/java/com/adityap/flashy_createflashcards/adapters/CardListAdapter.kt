package com.adityap.flashy_createflashcards.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.adityap.flashy_createflashcards.R;
import com.adityap.flashy_createflashcards.models.DeckModel;
import com.adityap.flashy_createflashcards.models.FlashcardModel;

import java.util.List;

public class CardListAdapter extends BaseAdapter {
    private List<FlashcardModel> mFlashcardModel;
    private Context mContext;

    public CardListAdapter(Context context, List<FlashcardModel> flashcardModels) {
        mFlashcardModel = flashcardModels;
        mContext = context;

    }

    public List<FlashcardModel> getFlashcardModel() {
        return mFlashcardModel;
    }

    public void setCardModels(List<FlashcardModel> mFlashcardModel) {
        this.mFlashcardModel = mFlashcardModel;
    }

    @Override
    public int getCount() {
        return mFlashcardModel.size();
    }

    @Override
    public Object getItem(int position) {
        return mFlashcardModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.custom_deckname_item_view,null);

        TextView textViewDeckName = (TextView)convertView.findViewById(R.id.textview_deckName);
        TextView textViewDeckDescription = (TextView)convertView.findViewById(R.id.textview_deckDescription);

        textViewDeckName.setText(mFlashcardModel.get(position).getWord());
        textViewDeckDescription.setText(mFlashcardModel.get(position).getDefinition());
        return convertView ;
    }
}
