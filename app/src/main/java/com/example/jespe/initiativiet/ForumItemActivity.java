package com.example.jespe.initiativiet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

public class ForumItemActivity extends AppCompatActivity implements View.OnClickListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_item);
        commentList = (ListView) findViewById(R.id.CommentList);
        Intent prevIntent = getIntent();

        postedComments = new ArrayList<>();
        key = prevIntent.getStringExtra("Key");

        comment = (EditText) findViewById(R.id.editText);
        send= (ImageButton) findViewById(R.id.imageButton);
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

        aa = new ArrayAdapter(this, R.layout.postlistitem, R.id.author, postedComments){
            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {

                View v = super.getView(position, cachedView, parent);

                if (position != postedComments.size()) {
                   // TextView headline = (TextView) v.findViewById(R.id.headline);
                   // TextView category = (TextView) v.findViewById(R.id.category);
                    TextView content = (TextView) v.findViewById(R.id.description);
                    //TextView author = (TextView) v.findViewById(R.id.author);
                   // headline.setText(lfs[position].getHeadline());
                   // category.setText(lfs[position].getCategory());
                    content.setText(postedComments.get(position).getContent());
                    //author.setText(lfs[position].getAuthor());
                }



                return v;
            }
        };
        commentList.setAdapter(aa);

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
