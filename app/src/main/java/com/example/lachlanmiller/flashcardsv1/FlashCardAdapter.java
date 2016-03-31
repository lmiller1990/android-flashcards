package com.example.lachlanmiller.flashcardsv1;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lachlanmiller on 15/03/2016.
 */
public class FlashCardAdapter extends PagerAdapter {

    private Activity mActivity;
    private ArrayList<String> mCards;
    private LayoutInflater layoutInflater;

    public FlashCardAdapter(Activity activity, ArrayList<String> cards) {
        this.mActivity = activity;
        this.mCards = cards;
    }


    @Override
    public int getCount() {
        return mCards.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View flashcard = layoutInflater.inflate(R.layout.flash_card, container, false);

        final ViewFlipper flipper = (ViewFlipper) flashcard.findViewById(R.id.flipper);

        // Get list containing all views to populate
        ArrayList<EditText> cardEditTexts = new ArrayList<EditText>();

        for (int i = 0; i < flipper.getChildCount(); i++) {
            if (flipper.getChildAt(i) instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) flipper.getChildAt(i);
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    if (linearLayout.getChildAt(j) instanceof EditText) {
                        cardEditTexts.add((EditText) linearLayout.getChildAt(j));
                    }
                }
            }
        }

        // separate by `
        List<String> cardsData = Arrays.asList(mCards.get(position).split("`"));

        int i = 0;
        for (EditText editText : cardEditTexts) {
            if (cardsData.get(i).equals("blank"))
                editText.setText(" ");
            else
                editText.setText(cardsData.get(i));

            editText.setEnabled(true);
            editText.setFocusable(false);
            editText.setCursorVisible(false);
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnimationFactory.flipTransition(flipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
                }
            });
            i++;
        }

        container.addView(flashcard);

        return flashcard;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // destroy
        container.removeView((LinearLayout) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }
}
