package com.example.jespe.initiativiet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class ValgFragment extends Fragment implements View.OnClickListener {

    Button StatsBtn, ForumBtn, ValgBtn, LogoutBtn;
    private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
            View v = i.inflate(R.layout.fragment_valg, container, false);
        StatsBtn = (Button) v.findViewById(R.id.StatsButton);
        ForumBtn = (Button) v.findViewById(R.id.ForumBtn);
        ValgBtn = (Button) v.findViewById(R.id.ValgBtn);
        LogoutBtn = (Button) v.findViewById(R.id.LogoutBtn);


        ForumBtn.setOnClickListener(this);
        StatsBtn.setOnClickListener(this);
        LogoutBtn.setOnClickListener(this);

        ValgBtn.setBackgroundColor(getResources().getColor(R.color.chosenbtn));

        //Firebase init
        FirebaseApp.initializeApp(getActivity());
        auth = FirebaseAuth.getInstance();

        //For testing
        System.out.println(auth.getCurrentUser().getUid());

    return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.StatsButton:
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new StatistikFragment())
                        .addToBackStack(null).commit();
                System.out.println("Niklas siger Niggah");
                break;
            case R.id.ForumBtn:
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ForumFragment())
                        .addToBackStack(null).commit();
                break;
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