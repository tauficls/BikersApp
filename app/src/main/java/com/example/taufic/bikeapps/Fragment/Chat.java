package com.example.taufic.bikeapps.Fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taufic.bikeapps.R;

public class Chat extends Fragment {
    public Chat() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View chat = inflater.inflate(R.layout.activity_chat, container, false);
        ((TextView) chat.findViewById(R.id.textView)).setText("Chats");
        return chat;
    }
}
