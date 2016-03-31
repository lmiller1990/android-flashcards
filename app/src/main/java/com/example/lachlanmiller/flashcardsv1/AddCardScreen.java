package com.example.lachlanmiller.flashcardsv1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lachlanmiller on 14/03/2016.
 */
public class AddCardScreen extends Activity {

    Button backButton;
    Button flipButton;
    Button addCardButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_screen);

        backButton = (Button) findViewById(R.id.back_to_flashcards_button);
        flipButton = (Button) findViewById(R.id.flip_card_button);
        addCardButton = (Button) findViewById(R.id.add_card_deck_button);

        Intent received = getIntent();
        String deckName = received.getStringExtra("deck");
        TextView deck = (TextView) findViewById(R.id.deck_title);
        deck.setText(deckName);

        setButtonListeners(deckName);
    }

    private void setButtonListeners(final String deckName) {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent flashCardScreen = new Intent(getApplicationContext(), FlashCardScreen.class);
                flashCardScreen.putExtra("deck", deckName);
                startActivity(flashCardScreen);
            }
        });

        flipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip();
            }
        });

        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                writeCardToFile(deckName);
                // Go back to flash cards
                Intent ret = new Intent(getApplicationContext(), FlashCardScreen.class);
                ret.putExtra("newCard", "newCard");
                ret.putExtra("deck", deckName);
                startActivity(ret);

            }
        });
    }

    private void writeCardToFile(String deckName) {
        IOUtils utils = new IOUtils(getApplicationContext());
        // see how many cards in deck - name cards card1, card2...
        String fileName = this.getFilesDir().toString() + File.separator + "decks" + File.separator + deckName;
        File file = new File(fileName);
        File[] cards = file.listFiles();

        String cardName = "card" + cards.length;

        // Reference to all fields of card...
        // Tricky stuff to find all EditTexts. Find all children of flipper,
        // which is two linearlayouts, one for each side of card. Get each child,
        // which is an edittext, and add to the cardFields arraylist.
        ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper);
        ArrayList<EditText> cardFields = new ArrayList<EditText>();
        int a = 0;
        for (int i = 0; i < flipper.getChildCount(); i++) {
            if (flipper.getChildAt(i) instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) flipper.getChildAt(i);
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    if (linearLayout.getChildAt(j) instanceof EditText) {
                        cardFields.add((EditText) linearLayout.getChildAt(j));
                        a++;
                    }
                }
            }
        }
       // Make into string to write to file
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cardFields.size(); i++) {
            String line = cardFields.get(i).getText().toString();
            if (!line.equals("")) {
                sb.append(line + "`");
            }
            else if (line.equals("")) {
                sb.append("blank`");
            }
        }
        // now we have a string, write to file!
        utils.writeToFileWithPath(sb.toString(), cardName, "decks" + File.separator + deckName, false);
    }

    private void flip() {
        ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper);
        //flipper.showNext();
        AnimationFactory.flipTransition(flipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
    }
}

