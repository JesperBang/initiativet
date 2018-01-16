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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

public class ForumItemFragment extends Fragment implements View.OnClickListener {

    ListView commentList;
    EditText comment;
    ImageButton send;

    DatabaseReference fb, fbc;
    ArrayAdapter aa;
    Calendar cal;

    ArrayList<CommentMade> postedComments;
    Map<String, String> map;
    String key, commentsKey;
    int commentCount;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View view = i.inflate(R.layout.fragment_forum_item, container, false);


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
                if (map.get("commentsKey") == null){
                    CommentMade firstComment = new CommentMade(map.get("content"), map.get("dateCreated"), null);
                    commentsKey = fbc.push().getKey();
                    fbc.child(commentsKey).child("Comment 0").setValue(firstComment);
                    fb.child("commentsKey").setValue(commentsKey);
                    postedComments.clear();
                    postedComments.add(firstComment);
                    commentCount = (int) dataSnapshot.getChildrenCount();
                    commentList.setAdapter(aa);
                } else {

                    fbc = fbc.child(map.get("commentsKey"));
                    fbc.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            postedComments.clear();
                            for ( DataSnapshot snapshot : dataSnapshot.getChildren()){
                                map = (Map) snapshot.getValue();
                                postedComments.add(new CommentMade(
                                    map.get("content"),
                                    map.get("date"),
                                    map.get("author")));
                            }
                            commentCount = (int) dataSnapshot.getChildrenCount();
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
        System.out.println("AA");

        aa = new ArrayAdapter(getActivity(), R.layout.postlistitem, R.id.author, postedComments){


            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {
               if(cachedView == null ) {
                   cachedView = LayoutInflater.from(getContext()).inflate(R.layout.postlistitem, parent, false);
               }

                System.out.println("insidekkk");
                //View v = super.getView(position, cachedView, parent);
                if (position != postedComments.size()) {
                   TextView content = (TextView) cachedView.findViewById(R.id.description);
                   content.setText(postedComments.get(position).getContent());
                }

                return cachedView;
            }
        };
        commentList.setAdapter(aa);

        return view;
    }
    @Override
    public void onClick(View view) {

        cal = GregorianCalendar.getInstance();
        CommentMade newComment = new CommentMade(
            comment.getText().toString(),
            String.valueOf(cal.getTimeInMillis()),
            "");

        fbc.child("Comment: " + commentCount).setValue(newComment);
    }
}