package com.example.lachlanmiller.flashcardsv1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by lachlanmiller on 14/03/2016.
 */
public class DeckListViewAdapter extends BaseAdapter {

    Activity mActivity;
    ArrayList<String> mList;

    public DeckListViewAdapter(Activity activity, ArrayList<String> list) {
        this.mActivity = activity;
        this.mList = list;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = mActivity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.deck_select_item, null);
        }

        TextView deckItem = (TextView) convertView.findViewById(R.id.deck_select_item);
        final String deckName = mList.get(position);
        deckItem.setText(deckName);

        // if user clicks item they should be taken to
        // Flash Card Screen and relevant cards shown.
        convertView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mActivity);
                dialogBuilder.setMessage("Delete deck?");
                dialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteDeck(deckName);
                    }
                });
                dialogBuilder.show();
                return false;
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deckSelected(deckName);
            }
        });

        return convertView;
    }

    /* delete deck */
    private void deleteDeck(String deckName) {
        File file = new File(mActivity.getFilesDir() + File.separator + "decks" + File.separator + deckName);
        deleteDir(file);
        notifyDataSetChanged();

        // notify main activity

    }

    void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();

        Intent intent = new Intent(mActivity, FirstScreen.class);
        mActivity.startActivity(intent);
    }

    // click event
    private void deckSelected(String deckName) {
        Intent flashCardScreen = new Intent(mActivity, FlashCardScreen.class);
        flashCardScreen.putExtra("deck", deckName);
        mActivity.startActivity(flashCardScreen);
    }
}
