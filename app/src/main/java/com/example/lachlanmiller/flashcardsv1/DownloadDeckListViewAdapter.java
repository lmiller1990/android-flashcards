package com.example.lachlanmiller.flashcardsv1;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lachlanmiller on 19/03/2016.
 */
public class DownloadDeckListViewAdapter extends BaseAdapter {

    List<String> mCategories;
    Activity mActivity;

    public DownloadDeckListViewAdapter(Activity activity, String data) {

        this.mCategories = Arrays.asList(data.split("`"));
        this.mActivity = activity;
    }

    @Override
    public int getCount() {
        return mCategories.size();
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
        textView.setText(mCategories.get(position));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClicked(mCategories.get(position));
            }
        });

        return convertView;
    }

    private void categoryClicked(String category) {
        Intent intent = new Intent(mActivity, DownloadCategoriesScreen.class);
        intent.putExtra("category", category);
        mActivity.startActivity(intent);

    }
}
