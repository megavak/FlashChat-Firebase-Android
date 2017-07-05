package com.megavak.flashchatnewfirebase.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.megavak.flashchatnewfirebase.R;
import com.megavak.flashchatnewfirebase.adapters.ChatListAdapter;
import com.megavak.flashchatnewfirebase.model.InstantMessage;


public class MainChatActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;
    private DatabaseReference mDatabaseReference;
    private ChatListAdapter chatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        // TODO: Set up the display name and get the Firebase reference
                   setUpDisplayName();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        // Link the Views in the layout to the Java code
        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chat_list_view);

        // TODO: Send the message when the "enter" button is pressed
            mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    sendMessage();
                    return true;
                }
            });

        // TODO: Add an OnClickListener to the sendButton to send a message
              mSendButton.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      sendMessage();
                  }
              });
    }

    // TODO: Retrieve the display name from the Shared Preferences
    private  void  setUpDisplayName(){
        SharedPreferences prefs = getSharedPreferences("CHAT_PREFS",MODE_PRIVATE);
        mDisplayName = prefs.getString("DISPLAY_NAME",null);
        if (mDisplayName == null) {
            mDisplayName = "Anonymous";
        }


    }


    private void sendMessage() {
        Log.d("FlashChat","I sent something");
        // TODO: Grab the text the user typed in and push the message to Firebase
        String input = mInputText.getText().toString();
        if(TextUtils.isEmpty(input)){
            return;
        }else {
            InstantMessage message = new InstantMessage(input,mDisplayName);
            mDatabaseReference.child("messages").push().setValue(message);
            mInputText.setText("");
        }

    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.


    @Override
    protected void onStart() {
        super.onStart();
        chatListAdapter = new ChatListAdapter(this,mDatabaseReference,mDisplayName);
        mChatListView.setAdapter(chatListAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();

        // TODO: Remove the Firebase event listener on the adapter.
        chatListAdapter.cleanUp();

    }

}
