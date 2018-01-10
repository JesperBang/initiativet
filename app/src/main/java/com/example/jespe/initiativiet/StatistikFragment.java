package com.example.jespe.initiativiet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StatistikFragment extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.fragment_statistik, container, false);

        return v;
    }

    @Override
    public void onClick(View v) {

    }
}