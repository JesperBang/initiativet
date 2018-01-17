package com.example.jespe.initiativiet;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jespe on 10-Jan-18.
 */

public class api_call {
    //init
    ArrayList<String> apiList;
    ArrayList<Integer> apiCatId;
    Gson gson;
    String lastCat = "Andet emne";

    //get api list of categories.
    public ArrayList<String> getApiCategory(){ return apiList; }
    public ArrayList<Integer> getApiIdCategory(){ return apiCatId; }

    //Constructor
    public api_call() {
        JsonDeserializer<Value> valueJsonDeserializer = new JsonDeserializer<Value>() {
            @Override
            public Value deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();
                try {
                    Value value = new Value(
                            jsonObject.get("id").getAsInt(),
                            jsonObject.get("kategori").getAsString(),
                            new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss.S").parse(jsonObject.get("opdateringsdato").getAsString())
                    );
                    return value;
                } catch (ParseException e) {e.printStackTrace();}
                return null;
            }
        };
        gson = new GsonBuilder()
                .registerTypeAdapter(Value.class, valueJsonDeserializer)
                .create();
    }

    public void fetchData(final Runnable runnable){
        apiList = new ArrayList<String>();
        apiCatId = new ArrayList<Integer>();

        getUrlData(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JsonReader reader = new JsonReader(response.body().charStream());
                Kategori lov = gson.fromJson(reader, Kategori.class);
                for (Value value: lov.getValue()) {
                    apiList.add(value.getKategori());
                    apiCatId.add(value.getId());
                }
                apiList.add(lastCat);
                runnable.run();
            }
        });
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
    }

    public class Value {
        private int id;
        private Date opdateringsdato;
        private String kategori;

        public Value(int id, String kategori, Date opdateringsdato) {
            this.id = id;
            this.opdateringsdato = opdateringsdato;
            this.kategori = kategori;
        }

        public int getId () {return id;}
        public void setId (int id) {this.id = id;}
        public Date getOpdateringsdato() {return opdateringsdato;}
        public void setOpdateringsdato(Date opdateringsdato) {this.opdateringsdato = opdateringsdato;}
        public String getKategori () {return kategori;}
        public void setKategori (String kategori) {this.kategori = kategori;}
    }

    public void getUrlData(Callback callback){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://oda.ft.dk/api/Sagskategori?$inlinecount=allpages&$skip=20")
                .build();

        client.newCall(request).enqueue(callback);
    }
}