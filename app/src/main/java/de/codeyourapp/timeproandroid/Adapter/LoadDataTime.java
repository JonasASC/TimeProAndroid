package de.codeyourapp.timeproandroid.Adapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.codeyourapp.timeproandroid.Constante.UrlConstants;
import de.codeyourapp.timeproandroid.Models.DateTime;
import de.codeyourapp.timeproandroid.HTTP.HTTPGet;

public class LoadDataTime {

    public static List<DateTime> dataTimeList;

    public static void getData(int projectid){

        String result = "";
        HTTPGet http = new HTTPGet();
        try {
            result = http.execute(UrlConstants.getDataTime+projectid).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Type dataTimelistType = new TypeToken<ArrayList<DateTime>>(){}.getType();
        dataTimeList = gson.fromJson(result, dataTimelistType);
        System.out.println(dataTimeList);

    }

}