package com.example.lachlanmiller.flashcardsv1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lachlanmiller on 19/03/2016.
 */
public class DownloadDecksScreen extends Activity {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_decks_screen);
        mTextView = (TextView) findViewById(R.id.downloadDeckTitle);
        mTextView.setText("Download New Deck");

        RequestCategories();
        // split categories into a list
    }


    private void populateCategoryList(String data) {
        ListView listView = (ListView) findViewById(R.id.download_decks_categories_list);
        DownloadDeckListViewAdapter adapter = new DownloadDeckListViewAdapter(this, data);
        listView.setAdapter(adapter);
    }

    private void RequestCategories() {

        final ProgressDialog progressDialog= ProgressDialog.show(this, "",
                "Loading. Please wait...", true);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://lmiller.pythonanywhere.com/get_categories";

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                populateCategoryList(response.toString());
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
