package com.adityap.flashy_createflashcards.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adityap.flashy_createflashcards.R;
import com.adityap.flashy_createflashcards.models.FlashcardModel;

import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<FlashcardModel> mFlashcards;

    public CustomPagerAdapter(Context context, List<FlashcardModel> flashcards){
        mContext = context;
        mFlashcards = flashcards;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ConstraintLayout flashCardView = (ConstraintLayout)
                LayoutInflater.from(mContext).inflate(R.layout.flashcard_item_layout, container, false);
        TextView frontText = flashCardView.findViewById(R.id.front_text);
        frontText.setText(mFlashcards.get(position).getWord());
        return flashCardView;
    }

    @Override
    public int getCount() {
        return mFlashcards.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
