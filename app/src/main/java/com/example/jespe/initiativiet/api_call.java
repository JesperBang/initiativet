package com.example.jespe.initiativiet;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by jespe on 10-Jan-18.
 */

public class api_call {
    ArrayList<String> apiList;
    Gson gson = new Gson();
    String temp;

    public ArrayList<String> getApiCategory(){ return apiList; }

    public void fetchData(){
        apiList = new ArrayList<String>();
        new AsyncTaskRunnerVs().execute();
    }

    private class AsyncTaskRunnerVs extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            getUrlData();

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            JsonReader reader = new JsonReader(new StringReader(temp));
            Kategori lov = gson.fromJson(reader, Kategori.class);

            System.out.println(Arrays.toString(lov.getValue()));
        }

        @Override
        protected void onPreExecute() {     }

        @Override
        protected void onProgressUpdate(String... text) {      }
    }

    public void getUrlData(){
        String inputLine;

        try {
            URL ft_api = new URL("http://oda.ft.dk/api/Sagskategori?$inlinecount=allpages&$skip=20");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            ft_api.openStream()));

            while ((inputLine = in.readLine()) != null)
                temp += inputLine+"\n";

            in.close();
        }catch (Exception e){e.printStackTrace();}

        temp = temp.replace("null", "");
        temp = temp.replace("odata.metadata", "odatametadata");
        temp = temp.replace("odata.count", "odatacount");
    }

    public class Kategori {
        private String odatacount;
        private Value[] value;
        private String odatametadata;

        public String getOdatacount () {return odatacount;}
        public void setOdatacount (String odatacount) {this.odatacount = odatacount;}
        public Value[] getValue () {return value;}
        public void setValue (Value[] value) {this.value = value;}
        public String getOdatametadata () {return odatametadata;}
        public void setOdatametadata (String odatametadata) {this.odatametadata = odatametadata;}

        @Override
        public String toString() {
            //System.out.println(Arrays.toString(value));
            //System.out.println("[odatacount = "+odatacount+", value = "+value+", odatametadata = "+odatametadata+"]");
            return "[odatacount = "+odatacount+", value = "+Arrays.toString(value)+", odatametadata = "+odatametadata+"]";
        }
    }

    public class Value {
        private String id;
        private String opdateringsdato;
        private String kategori;

        public String getId () {return id;}
        public void setId (String id) {this.id = id;}
        public String getOpdateringsdato () {return opdateringsdato;}
        public void setOpdateringsdato (String opdateringsdato) {this.opdateringsdato = opdateringsdato;}
        public String getKategori () {return kategori;}
        public void setKategori (String kategori) {this.kategori = kategori;}

        @Override
        public String toString() {
            System.out.println("[id = "+id+", opdateringsdato = "+opdateringsdato+", kategori = "+kategori+"]");
            return "[id = "+id+", opdateringsdato = "+opdateringsdato+", kategori = "+kategori+"]";
        }
    }
}