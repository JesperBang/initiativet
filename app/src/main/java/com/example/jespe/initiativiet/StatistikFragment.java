package com.example.jespe.initiativiet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class StatistikFragment extends Fragment {
    api_call_statistics api_stat = new api_call_statistics();
    ListView list_stat;
    String inp = "http://oda.ft.dk/api/Sag?$inlinecount=allpages&$filter=statusid%20eq%2011";

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.fragment_statistik, container, false);
        list_stat = (ListView) v.findViewById(R.id.list_stat);

        //To stop app from fetching 2800 pages of api data once again
        //when fidgeting around with the tabs. PLEASE DO NOT REMOVE <3
        if (api_stat.getApiLovRes().size() < 1) {
            api_stat.fetchData(new Runnable() {
                @Override
                public void run() {
                    StatistikFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list_stat.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, api_stat.getApiLovRes()));
                        }
                    });
                }
            }, inp);
        }
        return v;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
}