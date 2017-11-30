package com.example.jespe.initiativiet;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ForumActivity extends AppCompatActivity implements View.OnClickListener, OnItemClickListener {

    FloatingActionButton fab;
    SearchView search;
    Button ForumBtn;
    ListView debat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        ForumBtn = (Button) findViewById(R.id.ForumBtn);

        ForumBtn.setBackgroundColor(getResources().getColor(R.color.chosenbtn));

        debat = (ListView) findViewById(R.id.debat);

        final Lovforslag[] lfs = {
                new Lovforslag("Sammy", "Vestlige invandrer", "Integrations tekst", "Jeg mangler integration... bla bla bla"),
                new Lovforslag("Niklas", "Højere eller lavere SU?", "Uddannelse", "Højere eller lavere SU? Giv jeres holdninger til kende"),
                new Lovforslag("Jonathan", "Ikke-vestlige", "Integration", "Jeg synes, at..."),
                new Lovforslag("Gustav", "Bitcoins pristigninger", "Finans", "Er det værd at investere i bitcoins?"),
                new Lovforslag("Jesper", "Knallert stier", "Trafik", "Jeg synes os på knallerter skal have \"cykelstier\" ")
        };

        String[] list = {
                "Test1",
                "Test2",
                "Test3",
                "Test4",
                "Test5"
        };

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.postlist, R.id.author, list){
            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {

                View v = super.getView(position, cachedView, parent);

                if (position != lfs.length) {
                    TextView headline = (TextView) v.findViewById(R.id.headline);
                    TextView category = (TextView) v.findViewById(R.id.category);
                    TextView description = (TextView) v.findViewById(R.id.description);
                    TextView author = (TextView) v.findViewById(R.id.author);

                    headline.setText(lfs[position].getHeadline());
                    category.setText(lfs[position].getCategory());
                    description.setText(lfs[position].getDescription());
                    author.setText(lfs[position].getAuthor());

                    switch (lfs[position].getCategory()) {
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
        debat.setAdapter(aa);


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
