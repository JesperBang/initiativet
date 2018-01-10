package com.example.jespe.initiativiet;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class ForumActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    FloatingActionButton fab;
    SearchView search;
    Button ForumBtn;
    ListView debat;
    DatabaseReference fb;
    ValueEventListener vEL;
    ArrayAdapter aa;

    Map<String, String> map;
    ArrayList<Lovforslag> lfs;

    //Lovforslag[] lfs = {
    //        new Lovforslag("Sammy", "Vestlige invandrer", "Integrations tekst", "Jeg mangler integration... bla bla bla"),
    //        new Lovforslag("Niklas", "Højere eller lavere SU?", "Uddannelse", "Højere eller lavere SU? Giv jeres holdninger til kende"),
    //        new Lovforslag("Jonathan", "Ikke-vestlige", "Integration", "Jeg synes, at..."),
    //        new Lovforslag("Gustav", "Bitcoins pristigninger", "Finans", "Er det værd at investere i bitcoins?"),
    //        new Lovforslag("Jesper", "Knallert stier", "Trafik", "Jeg synes os på knallerter skal have \"cykelstier\" ")
    //};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        ForumBtn = (Button) findViewById(R.id.ForumBtn);

        ForumBtn.setBackgroundColor(getResources().getColor(R.color.chosenbtn));

        debat = (ListView) findViewById(R.id.debat);

        lfs = new ArrayList<>();


        //Firebase Reference
        fb =  FirebaseDatabase.getInstance().getReference().child("forumentries");
        vEL = fb.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for ( DataSnapshot snapshot : dataSnapshot.getChildren()){

                    map = (Map) snapshot.getValue();
                    lfs.add(new Lovforslag(
                            map.get("title"),
                            map.get("content"),
                            map.get("tagName"),
                            map.get("type")
                    ));

                }

                debat.setAdapter(aa);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

            aa = new ArrayAdapter(this, R.layout.postlist, R.id.headline, lfs) {

                @Override
                public View getView(int position, View cachedView, ViewGroup parent) {

                    View v = super.getView(position, cachedView, parent);
                    if (position != lfs.size()) {
                        TextView headline = (TextView) v.findViewById(R.id.headline);
                        TextView category = (TextView) v.findViewById(R.id.category);
                        TextView description = (TextView) v.findViewById(R.id.description);
                        //TextView author = (TextView) v.findViewById(R.id.author);

                        headline.setText(lfs.get(position).getTitle());
                        category.setText(lfs.get(position).getTagName());
                        description.setText(lfs.get(position).getContent());
                        // author.setText(lfs.get(position).getAuthor());

                        switch (lfs.get(position).getTagName()) {
                            case "Miljø":
                                //parent.getChildAt(position).setBackgroundColor(Color.GREEN);
                                break;
                            case "Finans":
                                //parent.getChildAt(position).setBackgroundColor(Color.BLUE);
                                break;
                            case "Integration":
                                //parent.getChildAt(position).setBackgroundColor(Color.RED);
                                break;
                            case "Uddannelse":
                                //parent.getChildAt(position).setBackgroundColor(Color.YELLOW);
                                break;
                            default:
                                //parent.getChildAt(position).setBackgroundColor(Color.GRAY);
                                break;
                        }

                        //return v;
                    }
                    return v;
                }
            };

            //debat = new ListView(this);
            debat.setOnItemClickListener(this);
            //debat.setAdapter(aa);
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                createPost();
                break;
        }

    }
    public void onItemClick(AdapterView<?> I, View v, int position, long id){
        Intent iforumtitem = new Intent(ForumActivity.this, ForumItemActivity.class);
        startActivity(iforumtitem);
    }

    public void createPost(){
        Intent icpost = new Intent(ForumActivity.this, CreatePostActivity.class);
        startActivity(icpost);
    }

}
