package com.example.jespe.initiativiet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ValgFragment extends Fragment implements View.OnClickListener {

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
        FirebaseApp.initializeApp(getActivity());
        auth = FirebaseAuth.getInstance();

        LogoutBtn = (Button) getActivity().findViewById(R.id.LogoutBtn);
        LogoutBtn.setOnClickListener(this);

        api.fetchData();

        list_cat = (ListView) getActivity().findViewById(R.id.list_category);
        list_cat.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, api.getApiCategory()));

        list_cat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("clicked on:"+api.getApiCategory().get(position));
            }
        });

        //For testing
        //System.out.println(auth.getCurrentUser().getUid());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LogoutBtn:
                logout(v);
                break;
        }
    }
    private void logout(View v) {
        auth.signOut();

        try {
            if(auth.getCurrentUser() == null) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new LoginFragment())
                        .addToBackStack(null).commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Snackbar snackBar = Snackbar.make(v,"User signed out",Snackbar.LENGTH_SHORT);
        snackBar.show();
    }
}