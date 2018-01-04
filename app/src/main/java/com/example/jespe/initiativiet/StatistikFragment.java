package com.example.jespe.initiativiet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StatistikFragment extends Fragment implements View.OnClickListener {
    Button ValgBtn,ForumBtn, StatBtn;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.fragment_statistik, container, false);

        ValgBtn = (Button) v.findViewById(R.id.ValgBtn);
        ForumBtn = (Button) v.findViewById(R.id.ForumBtn);
        StatBtn = (Button) v.findViewById(R.id.StatBtn);
        ValgBtn.setOnClickListener(this);
        ForumBtn.setOnClickListener(this);

        System.out.println("Inside statistik");

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ValgBtn:
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ValgFragment())
                        .addToBackStack(null).commit();
                break;
            case R.id.ForumBtn:
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ForumFragment())
                        .addToBackStack(null).commit();
                break;
        }
    }
}