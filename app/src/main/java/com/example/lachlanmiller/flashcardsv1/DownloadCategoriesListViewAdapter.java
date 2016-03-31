package com.example.lachlanmiller.flashcardsv1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lachlanmiller on 20/03/2016.
 */
public class DownloadCategoriesListViewAdapter extends BaseAdapter {

    List<String> mDecks;
    Activity mActivity;
    String mCategory;

    public DownloadCategoriesListViewAdapter (Activity activity, String category, String data) {

        this.mDecks = Arrays.asList(data.split("`"));
        this.mActivity = activity;
        this.mCategory = category;
    }

    @Override
    public int getCount() {
        return mDecks.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = mActivity.getLayoutInflater();

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.category_item, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.category_item);
        textView.setText(mDecks.get(position));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClicked(mCategory, mDecks.get(position));
            }
        });

        return convertView;
    }

    private void categoryClicked(String category, String deck) {


        RequestCardsFromDeck(category, deck);
    }

    private void RequestCardsFromDeck(String category, final String deck) {

        final ProgressDialog progressDialog= ProgressDialog.show(mActivity, "",
                "Loading. Please wait...", true);

        RequestQueue queue = Volley.newRequestQueue(mActivity);
        String url = "http://lmiller.pythonanywhere.com/get_cards/" + deck;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Write cards to file

                writeDeckAndCardsToFile(deck, response);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity,("Coming soon..."), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

        queue.add(stringRequest);
    }

    private void writeDeckAndCardsToFile(String deck, String cards) {
        File file = new File(mActivity.getFilesDir() + File.separator + "decks" + File.separator + deck);
        file.mkdirs();

        // now we have made directory, we should write the cards.
        // break cards up based on `
        List<String> cardList = Arrays.asList(cards.split("\\^"));
        IOUtils utils = new IOUtils(mActivity);

        String cardName = "card";
        int i = 0;
        for (String card: cardList) {
            utils.writeToFileWithPath(card, cardName + String.valueOf(i), "decks" + File.separator + deck, false);
            i++;
        }

        Toast.makeText(mActivity, "Downloaded and added " + deck, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mActivity, FirstScreen.class);
        mActivity.startActivity(intent);

    }
}
