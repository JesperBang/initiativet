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
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.ArrayList;

public class ValgFragment extends Fragment{

    Button LogoutBtn;
    private FirebaseAuth auth;
    ListView list_cat;
    ArrayList<String> al;
    api_call api = new api_call();

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.fragment_valg, container, false);

        return v;
    }
    
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Firebase init
        auth = FirebaseAuth.getInstance();
        //auth.signOut();
        list_cat = (ListView) view.findViewById(R.id.list_category);

        new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                api.fetchData();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);

                //Wrapper to fix NullPointerException
                if (getActivity() != null) {
                    list_cat.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, api.getApiCategory()));
                }
            }
        }.execute();

        System.out.println("this "+api.getApiCategory());

        list_cat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("clicked on:" + api.getApiIdCategory().get(position));
            }
        });
    }
}