package com.adityap.flashy_createflashcards.adapters;

import android.content.Context;
import android.graphics.Color;
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
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        ConstraintLayout flashCardView = (ConstraintLayout)
                LayoutInflater.from(mContext).inflate(R.layout.flashcard_item_layout, container, false);
        final TextView frontText = flashCardView.findViewById(R.id.frontTextView);
        final TextView backText = flashCardView.findViewById(R.id.backTextView);


        frontText.setText(mFlashcards.get(position).getWord());
        backText.setText(mFlashcards.get(position).getDefinition());
        container.addView(flashCardView);
        backText.setVisibility(View.GONE);

        frontText.setBackgroundColor(Color.rgb(161, 239, 255));

        frontText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frontText.setVisibility(View.GONE);
                backText.setVisibility(View.VISIBLE);
            }
        });

        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backText.setVisibility(View.GONE);
                frontText.setVisibility(View.VISIBLE);
            }
        });



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

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ConstraintLayout flashCardView = (ConstraintLayout) object;
        container.removeView(flashCardView);
    }
}
