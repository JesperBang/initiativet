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

    private ListView list_cat;
    private api_call api = new api_call();
    int tmp;
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



        System.out.println("re re re");
        //Fetch and update list with data from API
            api.fetchData(new Runnable() {
                    @Override
                    public void run() {
                        ValgFragment.this.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listDataHeader.addAll(api.getApiCategory());

                                // preparing list data
                                prepareListData();

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

    /* https://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
     * Preparing the list data
     */
    private void prepareListData() {
        // Adding child data
        List<String> cat1 = new ArrayList<String>();
        List<String> cat2 = new ArrayList<String>();
        List<String> cat3 = new ArrayList<String>();
        List<String> cat4 = new ArrayList<String>();
        List<String> cat5 = new ArrayList<String>();
        List<String> cat6 = new ArrayList<String>();
        List<String> cat7 = new ArrayList<String>();
        List<String> cat8 = new ArrayList<String>();
        List<String> cat9 = new ArrayList<String>();
        List<String> cat10 = new ArrayList<String>();
        List<String> cat11 = new ArrayList<String>();
        List<String> cat12 = new ArrayList<String>();

        cat1.add("The Shawshank Redemption");
        cat1.add("Another thing");
        cat1.add("more stuff for the boxes");
        cat2.add("The Conjuring");
        cat3.add("2 Guns");
        cat4.add("2 Guns");
        cat5.add("2 Guns");
        cat6.add("2 Guns");
        cat7.add("2 Guns");
        cat8.add("2 Guns");
        cat9.add("2 Guns");
        cat10.add("2 Guns");
        cat11.add("2 Guns");
        cat12.add("2 Guns");

        listDataChild.put(listDataHeader.get(0), cat1);
        listDataChild.put(listDataHeader.get(1), cat2);
        listDataChild.put(listDataHeader.get(2), cat3);
        listDataChild.put(listDataHeader.get(3), cat4);
        listDataChild.put(listDataHeader.get(4), cat5);
        listDataChild.put(listDataHeader.get(5), cat6);
        listDataChild.put(listDataHeader.get(6), cat7);
        listDataChild.put(listDataHeader.get(7), cat8);
        listDataChild.put(listDataHeader.get(8), cat9);
        listDataChild.put(listDataHeader.get(9), cat10);
        listDataChild.put(listDataHeader.get(10), cat11);
        listDataChild.put(listDataHeader.get(11), cat12);
    }
}