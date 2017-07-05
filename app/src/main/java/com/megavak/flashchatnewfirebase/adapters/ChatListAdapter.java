package com.megavak.flashchatnewfirebase.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.megavak.flashchatnewfirebase.R;
import com.megavak.flashchatnewfirebase.model.InstantMessage;

import java.util.ArrayList;

/**
 * Created by user on 2017/06/29.
 */

public class ChatListAdapter extends BaseAdapter {
    private Activity context;
    private DatabaseReference databaseReference;
    private String displayName;
    private ArrayList<DataSnapshot> snapshots;
    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            snapshots.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public ChatListAdapter(Activity context, DatabaseReference databaseReference, String displayName) {
        this.context = context;
        this.databaseReference = databaseReference.child("messages");
        databaseReference.addChildEventListener(childEventListener);
        this.displayName = displayName;
        snapshots = new ArrayList<>();
    }

    static  class ViewHolder{
        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
        return snapshots.size();
    }

    @Override
    public InstantMessage getItem(int position) {
        DataSnapshot snapshot = snapshots.get(position);
        return snapshot.getValue(InstantMessage.class);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
       // View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.chat_msg_row,parent,false);
           final  ViewHolder viewHolder = new ViewHolder();
            viewHolder.authorName = (TextView)view.findViewById(R.id.author);
            viewHolder.body = (TextView)view.findViewById(R.id.message);
            viewHolder.params = (LinearLayout.LayoutParams) viewHolder.authorName.getLayoutParams();
            view.setTag(viewHolder);
        }
        final InstantMessage message = getItem(position);
        final  ViewHolder holder = (ViewHolder)view.getTag();
        String author = message.getAuthor();
        String body = message.getMessage();
        holder.authorName.setText(author);
        holder.body.setText(body);
        return view;
    }

    public   void cleanUp(){
        databaseReference.removeEventListener(childEventListener);
    }

}
