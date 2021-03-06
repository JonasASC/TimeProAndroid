package de.codeyourapp.timeproandroid.HTTP;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;


public class HTTPPost extends AsyncTask<String, Void, Integer> {

    private Integer statusCode;
    public static String sessionCookies;
    private String headerfields;

    @Override
    protected Integer doInBackground(String... jsonParams) {

        try {


                URL url = new URL(jsonParams[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("cookie", HTTPPost.sessionCookies);
                conn.setDoOutput(true);
                conn.setDoInput(true);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
                bw.write(jsonParams[1]);

                bw.flush();
                bw.close();

                Log.i("post", jsonParams[1]);
                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG", conn.getResponseMessage());

                statusCode = conn.getResponseCode();
                if(conn.getHeaderField("Set-Cookie") != null) {
                    sessionCookies = conn.getHeaderField("Set-Cookie");
                }

        }catch (IOException e) {
            e.printStackTrace();
        }


        return statusCode;
    }
}
