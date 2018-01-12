package com.example.jespe.initiativiet;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jespe on 11-Jan-18.
 */

public class api_call_statistics {
    //init
    Gson gson;
    String temp;
    ArrayList<String> apiLovResultat;
    String inputLine, inp;
    URL ft_api;
    int skip;
    String tempUrl = "ppppp";
    Stat lov;

    //get api lists
    public ArrayList<String> getApiLovRes(){ return apiLovResultat; }

    //Constructor
    public api_call_statistics() {
        apiLovResultat = new ArrayList<String>();
        JsonDeserializer<Value> valueJsonDeserializer = new JsonDeserializer<Value>() {
            @Override
            public Value deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();
                    Value value = new Value(
                            jsonObject.get("titel").getAsString(),
                            jsonObject.get("afstemningskonklusion").getAsString()
                    );
                    return value;
            }
        };
        gson = new GsonBuilder()
                .registerTypeAdapter(Value.class, valueJsonDeserializer)
                .create();
    }

    public void fetchData(String inp){
        this.inp = inp;
        new AsyncTaskRunnerVs().execute();
    }

    private class AsyncTaskRunnerVs extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            inputLine = "";
            getUrlData(inp);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            JsonReader reader = new JsonReader(new StringReader(temp));

            try{ lov = gson.fromJson(reader, Stat.class);} catch(JsonSyntaxException e){}

            for (Value value: lov.getValue()) {
                apiLovResultat.add(value.getAfstemningskonklusion());
            }
            temp = "";
            try {reader.close(); } catch (IOException e) {}

            if (!tempUrl.contains("2800") && tempUrl != "stop"){
                fetchData(tempUrl);}else if(tempUrl.contains("2800")){
                String temps = tempUrl;
                tempUrl = "stop";
                System.out.println("Statistics api fetch data is done!");
                System.out.println("Statistics api fetch data is done!");
                fetchData(temps);
            }
        }

        @Override
        protected void onPreExecute() {     }

        @Override
        protected void onProgressUpdate(String... text) {      }
    }

    public class Stat {
        private String odatacount;
        private String odatanextLink;
        private Value[] value;
        private String odatametadata;

        public String getOdatacount () {return odatacount;}
        public void setOdatacount (String odatacount) {this.odatacount = odatacount;}
        public String getOdatanextLink () {return odatanextLink;}
        public void setOdatanextLink (String odatanextLink) {this.odatanextLink = odatanextLink;}
        public Value[] getValue () {return value;}
        public void setValue (Value[] value) {this.value = value;}
        public String getOdatametadata () {return odatametadata;}
        public void setOdatametadata (String odatametadata) {this.odatametadata = odatametadata;}
    }

    public class Value {
        private String afgørelsesresultatkode;
        private String nummernumerisk;
        private String statsbudgetsag;
        private String lovnummer;
        private String id;
        private String lovnummerdato;
        private String typeid;
        private String fremsatundersagid;
        private String afgørelsesdato;
        private String offentlighedskode;
        private String kategoriid;
        private String rådsmødedato;
        private String begrundelse;
        private String retsinformationsurl;
        private String nummer;
        private String statusid;
        private String nummerprefix;
        private String deltundersagid;
        private String afstemningskonklusion;
        private String periodeid;
        private String resume;
        private String opdateringsdato;
        private String titel;
        private String paragraf;
        private String baggrundsmateriale;
        private String afgørelse;
        private String titelkort;
        private String nummerpostfix;
        private String paragrafnummer;

        public Value(String titel, String afstemningskonklusion) {
            this.titel = titel;
            this.afstemningskonklusion = afstemningskonklusion;
        }

        public String getAfgørelsesresultatkode () {return afgørelsesresultatkode;}
        public void setAfgørelsesresultatkode (String afgørelsesresultatkode) {this.afgørelsesresultatkode = afgørelsesresultatkode;}
        public String getNummernumerisk () {return nummernumerisk;}
        public void setNummernumerisk (String nummernumerisk) {this.nummernumerisk = nummernumerisk;}
        public String getStatsbudgetsag () {return statsbudgetsag;}
        public void setStatsbudgetsag (String statsbudgetsag) {this.statsbudgetsag = statsbudgetsag;}
        public String getLovnummer () {return lovnummer;}
        public void setLovnummer (String lovnummer) {this.lovnummer = lovnummer;}
        public String getId () {return id;}
        public void setId (String id) {this.id = id;}
        public String getLovnummerdato () {return lovnummerdato;}
        public void setLovnummerdato (String lovnummerdato) {this.lovnummerdato = lovnummerdato;}
        public String getTypeid () {return typeid;}
        public void setTypeid (String typeid) {this.typeid = typeid;}
        public String getFremsatundersagid () {return fremsatundersagid;}
        public void setFremsatundersagid (String fremsatundersagid) {this.fremsatundersagid = fremsatundersagid;}
        public String getAfgørelsesdato () {return afgørelsesdato;}
        public void setAfgørelsesdato (String afgørelsesdato) {this.afgørelsesdato = afgørelsesdato;}
        public String getOffentlighedskode () {return offentlighedskode;}
        public void setOffentlighedskode (String offentlighedskode) {this.offentlighedskode = offentlighedskode;}
        public String getKategoriid () {return kategoriid;}
        public void setKategoriid (String kategoriid) {this.kategoriid = kategoriid;}
        public String getRådsmødedato () {return rådsmødedato;}
        public void setRådsmødedato (String rådsmødedato) {this.rådsmødedato = rådsmødedato;}
        public String getBegrundelse () {return begrundelse;}
        public void setBegrundelse (String begrundelse) {this.begrundelse = begrundelse;}
        public String getRetsinformationsurl () {return retsinformationsurl;}
        public void setRetsinformationsurl (String retsinformationsurl) {this.retsinformationsurl = retsinformationsurl;}
        public String getNummer () {return nummer;}
        public void setNummer (String nummer) {this.nummer = nummer;}
        public String getStatusid () {return statusid;}
        public void setStatusid (String statusid) {this.statusid = statusid;}
        public String getNummerprefix () {return nummerprefix;}
        public void setNummerprefix (String nummerprefix) {this.nummerprefix = nummerprefix;}
        public String getDeltundersagid () {return deltundersagid;}
        public void setDeltundersagid (String deltundersagid) {this.deltundersagid = deltundersagid;}
        public String getAfstemningskonklusion () {return afstemningskonklusion;}
        public void setAfstemningskonklusion (String afstemningskonklusion) {this.afstemningskonklusion = afstemningskonklusion;}
        public String getPeriodeid () {return periodeid;}
        public void setPeriodeid (String periodeid) {this.periodeid = periodeid;}
        public String getResume () {return resume;}
        public void setResume (String resume) {this.resume = resume;}
        public String getOpdateringsdato () {return opdateringsdato;}
        public void setOpdateringsdato (String opdateringsdato) {this.opdateringsdato = opdateringsdato;}
        public String getTitel () {return titel;}
        public void setTitel (String titel) {this.titel = titel;}
        public String getParagraf () {return paragraf;}
        public void setParagraf (String paragraf) {this.paragraf = paragraf;}
        public String getBaggrundsmateriale () {return baggrundsmateriale;}
        public void setBaggrundsmateriale (String baggrundsmateriale) {this.baggrundsmateriale = baggrundsmateriale;}
        public String getAfgørelse () {return afgørelse;}
        public void setAfgørelse (String afgørelse) {this.afgørelse = afgørelse;}
        public String getTitelkort () {return titelkort;}
        public void setTitelkort (String titelkort) {this.titelkort = titelkort;}
        public String getNummerpostfix () {return nummerpostfix;}
        public void setNummerpostfix (String nummerpostfix) {this.nummerpostfix = nummerpostfix;}
        public String getParagrafnummer () {return paragrafnummer;}
        public void setParagrafnummer (String paragrafnummer) {this.paragrafnummer = paragrafnummer;}
    }

    public void getUrlData(String url){
        try {
            ft_api = new URL(url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            ft_api.openStream()));

            while ((inputLine = in.readLine()) != null) {
                temp += inputLine + "\n";

                if (inputLine.contains("nextLink")) {
                    tempUrl = inputLine.substring(inputLine.lastIndexOf(":\"") + 1);
                    tempUrl = tempUrl.replace("\"","");
                }
            }

            in.close();
        }catch (Exception e){}

        temp = temp.replaceFirst("null", "");
        temp = temp.replace("null", "\"\"");
        temp = temp.replace("odata.metadata", "odatametadata");
        temp = temp.replace("odata.count", "odatacount");
        temp = temp.replace("odata.nextLink", "odatanextLink");
    }
}