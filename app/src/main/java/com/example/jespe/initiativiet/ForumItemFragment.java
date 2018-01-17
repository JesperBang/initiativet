package com.example.jespe.initiativiet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

public class ForumItemFragment extends Fragment implements View.OnClickListener {

    ListView commentList;
    EditText comment;
    ImageButton send;

    private FirebaseAuth auth;

    DatabaseReference fb, fbc;
    ArrayAdapter aa;
    Calendar cal;

    ArrayList<CommentMade> postedComments;
    Map<String, String> map;
    String key, commentsKey, type;

    long commentCount, userCount;
    Bundle bundle;

    boolean exists;

    @Override
    public View onCreateView(LayoutInflater i, final ViewGroup container, Bundle savedInstanceState) {
        View view = i.inflate(R.layout.fragment_forum_item, container, false);

        FirebaseApp.initializeApp(getActivity());
        auth = FirebaseAuth.getInstance();

        exists = false;

        commentList = (ListView) view.findViewById(R.id.CommentList);
        bundle = this.getArguments();
        if (bundle != null) {
            key = bundle.getString("Key", "");
        }

        postedComments = new ArrayList<>();

        comment = (EditText) view.findViewById(R.id.editText);
        send= (ImageButton) view.findViewById(R.id.imageButton);
        send.setOnClickListener(this);

        comment.setText("Deltag i debatten her");

        //Firebase Reference
        fbc = FirebaseDatabase.getInstance().getReference().child("forumentriescomments");
        fb =  FirebaseDatabase.getInstance().getReference().child("forumentries").child(key);
        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                map = (Map) dataSnapshot.getValue();
                type = map.get("type");
                userCount = dataSnapshot.child("comUsers").getChildrenCount();
                if (!type.equals("Normal")){
                    if (dataSnapshot.child("comUsers").getValue() == null){
                        fb.child("comUsers")
                                .child("user: " + userCount)
                                .setValue(map.get("authorID"));
                    }

                    for (DataSnapshot typeShot : dataSnapshot.child("comUsers").getChildren()){
                        if (auth.getCurrentUser().getUid().toString().equals(typeShot.getValue().toString())){
                            exists = true;
                            break;
                        }
                    }

                    if (type.equals("1on1 Debat")){
                        if (dataSnapshot.child("comUsers").getChildrenCount() == 2){
                            if (!exists){
                                send.setVisibility(View.GONE);
                                comment.setVisibility(View.GONE);
                            }
                        }
                    }
                    else {
                        if (dataSnapshot.child("comUsers").getChildrenCount() == 4){
                            if (!exists){
                                send.setVisibility(View.GONE);
                                comment.setVisibility(View.GONE);
                            }
                        }
                    }
                }

                postedComments.clear();
                if (map.get("commentsKey") == null){
                    commentsKey = fbc.push().getKey();
                    fb.child("commentsKey").setValue(commentsKey);
                } else {

                    commentsKey = map.get("commentsKey");
                    fbc = FirebaseDatabase.getInstance().getReference().child("forumentriescomments").child(commentsKey);
                    fbc.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null){
                                CommentMade firstComment = new CommentMade(map.get("content"), map.get("dateCreated"), map.get("authorID"));
                                fbc.child("Comment: " + commentCount).setValue(firstComment);
                                commentCount = dataSnapshot.getChildrenCount();
                            }
                            postedComments.clear();
                            for ( DataSnapshot snapshot : dataSnapshot.getChildren()){
                                map = (Map) snapshot.getValue();
                                postedComments.add(new CommentMade(
                                    map.get("content"),
                                    map.get("date"),
                                    map.get("author")));
                            }
                            commentCount = dataSnapshot.getChildrenCount();
                            commentList.setAdapter(aa);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        aa = new ArrayAdapter(getActivity(), R.layout.postlistitem, R.id.author, postedComments){


            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {
               if(cachedView == null ) {
                   cachedView = LayoutInflater.from(getContext()).inflate(R.layout.postlistitem, parent, false);
               }

                if (position != postedComments.size()) {
                   TextView content = (TextView) cachedView.findViewById(R.id.description);
                   content.setText(postedComments.get(position).getContent());
                   //TextView author = (TextView) cachedView.findViewById(R.id.author);
                   //author.setText(postedComments.get(position).getAuthor());
                   TextView date = (TextView) cachedView.findViewById(R.id.date);
                   date.setText(postedComments.get(position).getDate());
                }

                return cachedView;
            }
        };
        commentList.setAdapter(aa);

        return view;
    }
    @Override
    public void onClick(View view) {

        Date time = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("hh:mm - dd/MM/yyyy");
        String currentTime = formatter.format(time);
        CommentMade newComment = new CommentMade(
            comment.getText().toString(),
            currentTime,
            auth.getCurrentUser().getUid());

        if (!type.equals("Normal") && !exists){
            fb.child("comUsers")
                    .child("user: " + userCount)
                    .setValue(auth.getCurrentUser().getUid());
        }

        fbc.child("Comment: " + commentCount).setValue(newComment);
        commentList.setAdapter(aa);

    }
}