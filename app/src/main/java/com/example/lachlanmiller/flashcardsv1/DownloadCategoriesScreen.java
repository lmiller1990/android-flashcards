package com.example.lachlanmiller.flashcardsv1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by lachlanmiller on 19/03/2016.
 */
public class DownloadCategoriesScreen extends Activity {


    TextView mTextView;
    String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_decks_screen);
        mTextView = (TextView) findViewById(R.id.downloadDeckTitle);
        mTextView.setText("Download New Deck");

        Intent rec = getIntent();
        mCategory = rec.getStringExtra("category");
        RequestDecks(mCategory);
    }


    private void populateDecksList(String data) {
        ListView listView = (ListView) findViewById(R.id.download_decks_categories_list);
        DownloadCategoriesListViewAdapter adapter = new DownloadCategoriesListViewAdapter(this, mCategory, data);
        listView.setAdapter(adapter);
    }

    private void RequestDecks(String category) {

        final ProgressDialog progressDialog= ProgressDialog.show(this, "",
                "Loading. Please wait...", true);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://lmiller.pythonanywhere.com/get_decks/" + category;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                populateDecksList(response.toString());
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        queue.add(stringRequest);
    }

}
