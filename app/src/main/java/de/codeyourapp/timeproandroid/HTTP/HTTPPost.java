package de.codeyourapp.timeproandroid.HTTP;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class HTTPPost extends AsyncTask<String, Void, Integer> {

    private Integer statusCode;

    private String headerfields;

    @Override
    protected Integer doInBackground(String... jsonParams) {

        try {


                URL url = new URL(jsonParams[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("cookie", HTTPLoginPost.sessionCookies);
                conn.setRequestProperty("Accept-Charset", "UTF-8");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonParams[1]);

                writer.flush();
                writer.close();

                Log.i("post", jsonParams[1]);
                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG", conn.getResponseMessage());

                statusCode = conn.getResponseCode();
                

        }catch (IOException e) {
            e.printStackTrace();
        }


        return statusCode;
    }
}
