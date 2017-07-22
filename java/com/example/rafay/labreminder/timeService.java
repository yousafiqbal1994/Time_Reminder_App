package com.example.rafay.labreminder;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channel;

public class timeService {

    private Exception e;
    private callback callback;
    public int timeValue;
    private int check = 1;

    public timeService(callback callback) {
        this.callback = callback;
    }




    public void getTime (final String url1){


        new AsyncTask<String , Void , String>(){

            @Override
            protected String doInBackground(String... params) {


                try {
                    URL url = new URL(url1);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    return result.toString();
                } catch (Exception e1) {
                    e = e1;
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                if (s == null && e!=null) {
                    callback.serviceFailure(e);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);

                    JSONArray queryResults = data.getJSONArray("routes");
                    JSONObject queryResults1 = queryResults.getJSONObject(0);
                    JSONArray queryResults2 = queryResults1.getJSONArray("legs");
                    JSONObject queryResults3 = queryResults2.getJSONObject(0);
                    JSONObject queryResults4 = queryResults3.getJSONObject("duration");
                    String timeText = queryResults4.optString("text");
                    timeValue = queryResults4.optInt("value");

                    Log.d("tag", timeText);
                    Log.d("tag", ""+timeValue);

                    DistanceActivity point = new DistanceActivity();



                    check++;

                    callback.serviceSuccess(timeValue);




                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }
        }.execute();


    }




    }
