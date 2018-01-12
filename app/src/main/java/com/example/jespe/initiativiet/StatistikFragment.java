package com.example.jespe.initiativiet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class StatistikFragment extends Fragment {
    api_call_statistics api_stat = new api_call_statistics();

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.fragment_statistik, container, false);

        //To stop app from fetching 2800 pages of api data once again
        //when fidgeting around with the tabs. PLEASE DO NOT REMOVE <3
        if (api_stat.getApiLovRes().size() < 1){
            new AsyncTaskRunnerStat().execute();
        }

        return v;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //api_stat.fetchData();
    }

    private class AsyncTaskRunnerStat extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            api_stat.fetchData("http://oda.ft.dk/api/Sag?$inlinecount=allpages&$filter=statusid%20eq%2011");
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println("size: "+api_stat.getApiLovRes().size());
        }

        @Override
        protected void onPreExecute() {     }

        @Override
        protected void onProgressUpdate(String... text) {      }
    }
}