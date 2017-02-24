package com.example.taufic.bikeapps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.taufic.bikeapps.ClassItem.NewsHolder;
import com.example.taufic.bikeapps.R;

import java.util.ArrayList;

/**
 * Created by taufic on 2/20/2017.
 */

public class ListViewAdapter extends ArrayAdapter<NewsHolder> {
    ArrayList<NewsHolder> items;
    Context context;

    public ListViewAdapter(Context context, int resources, ArrayList<NewsHolder> items) {
        super(context, resources, items);
        this.context = context;
        this.items = items;
    }
    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        context.startActivity(launchBrowser);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view ==  null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.listview_news, null);
        }
        NewsHolder i = items.get(position);
        if (i != null) {
            TextView title_news = (TextView) view.findViewById(R.id.title_news);
            TextView description_news = (TextView) view.findViewById(R.id.description_news);
//            ListView listView = (ListView) view.findViewById(R.id.list_view);
//            ImageView imageView_news = (ImageView) convertView.findViewById(R.id.imageview_news);
            title_news.setText(items.get(position).getTitle());
            description_news.setText(items.get(position).getDescription());
//            imageView_news.setImageResource(items.get(position).getImage());
            final String webs = items.get(position).getWeb();
//            if (listView != null) {
                title_news.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToUrl(webs);
                    }
                });
        }
        return view;

    }
}
