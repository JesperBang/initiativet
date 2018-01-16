package com.example.jespe.initiativiet;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatistikFragment extends Fragment {
    api_call_statistics api_stat = new api_call_statistics();
    ListView piechartlist;
    ArrayList<String> lovres;
    Float[] ydata = new Float[]{1f,2f,3f};
    String[] xdata = new String[]{"For", "Imod", "Hverken for eller imod"};;
    String titel = "";
    ArrayList<PieChart> PL = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.fragment_statistik, container, false);
        piechartlist = (ListView) v.findViewById(R.id.piechartlist);
        //To stop app from fetching 2800 pages of api data once again
        //when fidgeting around with the tabs. PLEASE DO NOT REMOVE <3
        if (api_stat.getApiLovRes().size() < 1){
            new AsyncTaskRunnerStat().execute();
        }






        titel = "L125: Rengøring af søer på sjælland";
        String test1 = "102,3,4";
        String test2 ="30,2,10";
        String test3 = "40,10,2";
        lovres = new ArrayList<>();
        lovres.add(test1);
        lovres.add(test2);
        lovres.add(test3);


        /*for(int j = 0; j < lovres.size(); j++) {
            PieChart pieChart = new PieChart(getActivity());
            String sub = lovres.get(j);
            //String afstemning = sub.substring(sub.lastIndexOf("\"afstemningskonklusion\":") + 1);

            String[] test = sub.split(",");
            System.out.println(test[0]);
            Float for1 = Float.valueOf(Integer.parseInt(test[0]));
            Float imod = Float.valueOf(Integer.parseInt(test[1]));
            Float hverken = Float.valueOf(Integer.parseInt(test[2]));
            System.out.println(for1 + ", "+ imod + ", " + hverken);
            pieChart = addDataSet(pieChart,for1,imod,hverken);
            PL.add(pieChart);
            System.out.println(""+ PL.get(j).toString());
        }
        */

        Boolean testsss= PL.isEmpty();
        System.out.println(testsss);
        System.out.println("PL lænhgde ");
        //System.out.println(PL.toString());

        ArrayAdapter aap = new ArrayAdapter(getActivity(), R.layout.stat_piechart, R.id.textView2, lovres){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                PieChart pieChart = (PieChart) v.findViewById(R.id.piechart);
                TextView textView = (TextView) v.findViewById(R.id.textView2);
                textView.setVisibility(View.GONE);

                String sub = lovres.get(position);
                //String afstemning = sub.substring(sub.lastIndexOf("\"afstemningskonklusion\":") + 1);

                String[] test = sub.split(",");
                System.out.println(test[0]);
                Float for1 = Float.valueOf(Integer.parseInt(test[0]));
                Float imod = Float.valueOf(Integer.parseInt(test[1]));
                Float hverken = Float.valueOf(Integer.parseInt(test[2]));
                System.out.println(for1 + ", "+ imod + ", " + hverken);
                pieChart = addDataSet(pieChart,for1,imod,hverken, titel);
                PL.add(pieChart);
                System.out.println(""+ PL.get(position).toString());


                return v;

            }

                    @Override
                    public int getCount() {
                        return lovres.size();
                    }
                };
        System.out.println("getcount: "+aap.getCount());
        piechartlist.setAdapter(aap);
        return v;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //api_stat.fetchData();
    }
    private PieChart addDataSet(PieChart pieChart, Float for1, Float imod, Float hverken,String titel) {
        //https://github.com/PhilJay/MPAndroidChart/
        List<PieEntry> entries = new ArrayList<>();

        final Float[] ydata = {for1, imod, hverken};
        entries.add(new PieEntry(for1,"For"));
        entries.add(new PieEntry(imod,"Imod"));
        entries.add(new PieEntry(hverken,"ved ikke"));

        PieDataSet pieDataSet = new PieDataSet(entries,titel);
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.GRAY);

        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        pieChart.setNoDataText("Data mangler, refresh side");
        pieChart.setDrawSliceText(false);
        pieChart.setHoleRadius(40);
        pieChart.setTransparentCircleRadius(45);
        pieChart.setTransparentCircleAlpha(50);
        pieChart.animateX(1000);
        pieChart.animateY(1000);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int position = e.toString().indexOf("(sum): ");
                String votes = e.toString().substring(position + 7);

                for(int i = 0; i < ydata.length; i++){
                    if(ydata[i] == Float.parseFloat(votes)){
                        position = i;
                        break;
                    }
                }
                String votesAnswer = xdata[position];
                Toast.makeText(getActivity(), "" + votesAnswer + ": \n" + votes, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        return pieChart;
    }


    private class AsyncTaskRunnerStat extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            api_stat.fetchData("http://oda.ft.dk/api/Sag?$inlinecount=allpages&$filter=statusid%20eq%2017");
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