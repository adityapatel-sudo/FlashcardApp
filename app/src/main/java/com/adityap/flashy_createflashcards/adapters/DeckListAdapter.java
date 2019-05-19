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

import java.util.List;

public class DeckListAdapter extends BaseAdapter {
    private List<DeckModel> mDeckModels;
    private Context mContext;

    public DeckListAdapter(Context context, List<DeckModel> deckModels) {
        mDeckModels = deckModels;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDeckModels.size();
    }

    @Override
    public Object getItem(int position) {
        return mDeckModels.get(position);
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

        textViewDeckName.setText(mDeckModels.get(position).getDeckName());
        textViewDeckDescription.setText(mDeckModels.get(position).getDeckName());
        return convertView ;
    }
}
