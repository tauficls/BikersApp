package com.example.taufic.bikeapps.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.taufic.bikeapps.Adapter.ChatAdapter;
import com.example.taufic.bikeapps.ClassItem.ChatMessage;
import com.example.taufic.bikeapps.R;

import java.util.ArrayList;

public class Chat extends Fragment {

    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter chatAdapter;
    private ArrayList<ChatMessage> chatHistory;
    private ArrayList<ChatMessage> chatMessages;

    public Chat() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View chat = inflater.inflate(R.layout.activity_chat, container, false);
        ((TextView) chat.findViewById(R.id.textView)).setText("Chats");
//        chatHistory = new ArrayList<>();
//        chatMessages = new ArrayList<>();
//        messagesContainer = (ListView) chat.findViewById(R.id.messagesContainer);
//        messageET = (EditText) chat.findViewById(R.id.messageEdit);
//        sendBtn = (Button) chat.findViewById(R.id.chatSendButton);
//        TextView meLabel = (TextView) chat.findViewById(R.id.meLbl);
//        TextView companionLabel = (TextView) chat.findViewById(R.id.friendLabel);
////        RelativeLayout container = (RelativeLayout) chat.findViewById(R.id.container);
//        companionLabel.setText("My Buddy");
//
//        sendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String messageText = messageET.getText().toString();
//                if (TextUtils.isEmpty(messageText)) {
//                    return;
//                }
//
//                ChatMessage chatMessage = new ChatMessage();
//                chatMessage.setId(1);//dummy
//                chatMessage.setMessage(messageText);
//                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//                chatMessage.setMe(true);
//
//                messageET.setText("");
//
//                displayMessage(chatMessage);
////                chatHistory.add(chatMessage);
////                chatAdapter = new ChatAdapter(getContext(), R.layout.listchat, chatMessages);
////                messagesContainer.setAdapter(chatAdapter);
//            }
//        });
        //Database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("Community_chat").child("tes_1");
//
//        ref.addChildEventListener(new ChildEventListener(){
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey){
//                //TODO Change to LIST VIEW, getKey itu user, getValue itu message
//                Log.d("DATA-CHAT", dataSnapshot.getKey()+ " " + dataSnapshot.getValue().toString());
//                ChatMessage chatMessage = new ChatMessage();
//                chatMessage.setId(2);
//                chatMessage.setMessage(dataSnapshot.getValue().toString());
//                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//                chatMessage.setMe(true);
//                chatHistory.add(chatMessage);
//                chatAdapter = new ChatAdapter(getContext(), R.layout.listchat, chatHistory);
//                messagesContainer.setAdapter(chatAdapter);
//                for(int i=0; i<chatHistory.size(); i++) {
//                    ChatMessage message = chatHistory.get(i);
//                    displayMessage(message);
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        //TODO ADD SEND BUTTON AND MESSAGEAREA
//        /*sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String messageText = messageArea.getText().toString();
//
//                if(!messageText.equals("")){
//
//
//                    Map<String,Object> map= new HashMap<String,Object>();
//                    map.put(UserDetails.username,messageText);
//                    ref.updateChildren(map);
//
//                }
//            }
//        });*/

        return chat;
    }
//    public void displayMessage(ChatMessage message) {
//        chatAdapter.add(message);
//        chatAdapter.notifyDataSetChanged();
//        scroll();
//    }
//    private void scroll() {
//        messagesContainer.setSelection(messagesContainer.getCount() - 1);
//    }
//    private void loadDummyHistory(){
//
//        chatHistory = new ArrayList<ChatMessage>();
//
//        ChatMessage msg = new ChatMessage();
//        msg.setId(1);
//        msg.setMe(false);
//        msg.setMessage("Hi");
//        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//        chatHistory.add(msg);
//        ChatMessage msg1 = new ChatMessage();
//        msg1.setId(2);
//        msg1.setMe(false);
//        msg1.setMessage("How r u doing???");
//        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//        chatHistory.add(msg1);
//
//        adapter = new ChatAdapter(view.get, new ArrayList<ChatMessage>());
//        messagesContainer.setAdapter(adapter);
//
//        for(int i=0; i<chatHistory.size(); i++) {
//            ChatMessage message = chatHistory.get(i);
//            displayMessage(message);
//        }
//
//    }

}
