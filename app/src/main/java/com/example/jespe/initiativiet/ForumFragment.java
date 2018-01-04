package com.example.jespe.initiativiet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ForumFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    FloatingActionButton fab;
    SearchView search;
    Button ForumBtn;
    ListView debat;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = i.inflate(R.layout.fragment_forum, container, false);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        ForumBtn = (Button) v.findViewById(R.id.ForumBtn);

        ForumBtn.setBackgroundColor(getResources().getColor(R.color.chosenbtn));

        debat = (ListView) v.findViewById(R.id.debat);

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

        ArrayAdapter aa = new ArrayAdapter(getActivity(), R.layout.postlist, R.id.author, list){
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

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                createPost();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ForumItemFragment())
                .addToBackStack(null).commit();
        }

    public void createPost(){
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new CreatePostFragment())
                .addToBackStack(null).commit();
    }
}