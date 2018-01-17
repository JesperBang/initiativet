package com.example.jespe.initiativiet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ValgFragment extends Fragment{

    private api_call api = new api_call();
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
    }

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.fragment_valg, container, false);

        // / get the listview
        expListView = (ExpandableListView) v.findViewById(R.id.lvExp);

        //Fetch and update list with data from API
            api.fetchData(new Runnable() {
                    @Override
                    public void run() {
                        ValgFragment.this.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listDataHeader.addAll(api.getApiLovHeader());

                                // preparing list data
                                prepareListData();

                                //Creating adapter
                                listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

                                // setting list adapter
                                expListView.setAdapter(listAdapter);
                            }
                        });
                    }
                });

        return v;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {}


    private void prepareListData() {
        for (int i = 0; i < api.getApiLovHeader().size(); i++){
            ArrayList<String> temp = new ArrayList<String>();

            temp.add("Nummer: "+
                    api.getApiLovNummer().get(i)+"\n\n"+
                    "Resume:\n"+
                    api.getApiLovResume().get(i)
            );

            System.out.println(api.getApiLovResume().get(i));
            listDataChild.put(api.getApiLovHeader().get(i),temp);
        }
    }
}