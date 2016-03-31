package com.example.lachlanmiller.flashcardsv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by lachlanmiller on 14/03/2016.
 */
public class FlashCardScreen extends Activity {

    Button backButton;
    Button addCardButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash_card_screen);

        TextView deck = (TextView) findViewById(R.id.deck_title);

        Intent intent = getIntent();
        String deckName = intent.getStringExtra("deck");
        boolean newCardAdded = (intent.hasExtra("newCard")) ? true : false;

        // Set listener for back button
        backButton = (Button) findViewById(R.id.back_to_decks_button);
        addCardButton = (Button) findViewById(R.id.add_cards_button);

        setButtonListeners(deckName);

        deck.setText(deckName);
        // load cards
        ArrayList<String> cards = new ArrayList<String>();
        cards = loadCards(deckName);

        ViewPager viewPager = (ViewPager) findViewById(R.id.flash_card_viewpager);
        FlashCardAdapter adapter = new FlashCardAdapter(this, cards);
        viewPager.setAdapter(adapter);

        if (newCardAdded) {
            viewPager.setCurrentItem(adapter.getCount()-1);
        }


    }

    private ArrayList<String> loadCards(String deckName) {

        ArrayList<String> cards = new ArrayList<String>();
        IOUtils utils = new IOUtils(getApplicationContext());

        // list of all cards
        String fileName = this.getFilesDir().toString() + File.separator + "decks" + File.separator + deckName;
        File file = new File(fileName);
        File[] cardList = file.listFiles();

        // read all cards from deck folder and add to cards array
        for (File card : cardList) {
            cards.add(utils.readFromFile(card.toString()));
        }
        return cards;
    }


    private void setButtonListeners(final String deckName) {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to deck select screen
                Intent backToDeckSelect = new Intent(getApplicationContext(), FirstScreen.class);
                startActivity(backToDeckSelect);
            }
        });
        addCardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent addCardScreen = new Intent(getApplicationContext(), AddCardScreen.class);
                addCardScreen.putExtra("deck", deckName);
                startActivity(addCardScreen);
            }
        });

    }

}
