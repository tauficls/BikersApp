package com.example.taufic.bikeapps.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.taufic.bikeapps.Adapter.ListViewAdapter;
import com.example.taufic.bikeapps.ClassItem.NewsHolder;
import com.example.taufic.bikeapps.R;

import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


//public class News extends Fragment {
public class News extends Fragment {
    View news;
    JSONArray articleObject;
    ArrayList<NewsHolder> news_list;
    public News() {

    }

    //Filtered News
    JSONArray getFiltered (JSONArray data) {
        SimilarityStrategy strategy = new JaroWinklerStrategy();
        StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
        String compare = "bike";
        double score;

        //Initialize
        JSONArray dataFiltered = new JSONArray();

        for (int i = 0; i < data.length(); i++) {
            try {
                JSONObject temp = (JSONObject) data.get(i);
                score = service.score(temp.getString("title"), compare);

                if (score > 0.7) {
                    dataFiltered.put(temp);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return dataFiltered;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        news = inflater.inflate(R.layout.activity_news, container, false);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://newsapi.org/v1/articles?source=bbc-sport&sortBy=top&apiKey=4de3f6453a7d410799e3ed46745e9e7d";

        //Initialize
        news_list = new ArrayList<NewsHolder>();
        articleObject = new JSONArray();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject responseObject = null;
                        JSONArray filteredObject = null;

                        try {
                            responseObject = new JSONObject(response);
                            articleObject = responseObject.getJSONArray("articles");

                            Log.d("tes", "tes " + articleObject.length());
                            if (articleObject.length() > 0) {
                                for (int i = 0; i < articleObject.length(); i++) {

                                        JSONObject temp = (JSONObject) articleObject.get(i);
                                        news_list.add(new NewsHolder(temp.get("title").toString(), temp.get("description")
                                                .toString(),temp.get("urlToImage").toString(),temp.get("url").toString()));
                                }
                                ListView listView = (ListView) news.findViewById(R.id.list_view);
                                ListViewAdapter adapter = new ListViewAdapter(getContext(), R.layout.listview_news, news_list);
                                listView.setAdapter(adapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((TextView) news.findViewById(R.id.textView)).setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

//        View news = inflater.inflate(R.layout.activity_news, container, false);
//        ((TextView) news.findViewById(R.id.textView)).setText("News");

        return news;
    }

    public void linkToUrl(View view) {

    }
}


