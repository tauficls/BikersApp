package com.example.taufic.bikeapps.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.taufic.bikeapps.ClassItem.ChatMessage;

import java.util.ArrayList;

/**
 * Created by taufic on 2/25/2017.
 */

public class ChatAdapter extends ArrayAdapter<ChatMessage> {
    private ArrayList<ChatMessage> items;

    public ChatAdapter(Context context, int resource, ArrayList<ChatMessage> items) {
        super(context, resource, items);
        this.items = items;

    }

    @Override
    public View getView() {}
}
