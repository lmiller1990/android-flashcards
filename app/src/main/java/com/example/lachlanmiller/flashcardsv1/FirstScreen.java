package com.example.lachlanmiller.flashcardsv1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FirstScreen extends AppCompatActivity {

    IOUtils ioUtils;
    ArrayList<String> deckNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        ioUtils = new IOUtils(this);
        ioUtils.firstTimeBoot();
        populateDeckLists();
    }


    public void onNewListButtonClick(View view) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage("Enter new deck name");
        final EditText editText = new EditText(this);
        dialogBuilder.setView(editText);

        dialogBuilder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String deckName = editText.getText().toString();
                if (!deckName.equals("")) {
                    File file = new File(getApplicationContext().getFilesDir() + File.separator + "decks" + File.separator + deckName);
                    file.mkdirs();
                    populateDeckLists();
                }
            }
        });
        dialogBuilder.show();
    }


    public void populateDeckLists() {
        deckNames = new ArrayList<String>();

        File file = new File(this.getFilesDir().toString() + File.separator + "decks");
        File[] decks = file.listFiles();

            for (File f : decks) {
            deckNames.add(ioUtils.removeExtension(ioUtils.getLastBitFromFilePath(f.toString())));
        }

        ListView deckListView = (ListView) findViewById(R.id.deck_list_view);
        DeckListViewAdapter adapter = new DeckListViewAdapter(this, deckNames);
        deckListView.setAdapter(adapter);
    }

    public void downloadDecksClick(View view) {
        Intent intent = new Intent(getApplicationContext(), DownloadDecksScreen.class);
        this.startActivity(intent);
    }

}
