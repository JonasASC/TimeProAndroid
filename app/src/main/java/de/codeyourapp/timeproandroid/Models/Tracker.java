package de.codeyourapp.timeproandroid.Models;

import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.util.concurrent.ExecutionException;

import de.codeyourapp.timeproandroid.HTTP.HTTPPost;
import de.codeyourapp.timeproandroid.Constante.UrlConstants;

public class Tracker {
    @Expose
    Integer userid;
    @Expose
    Integer projectid;
    private Gson gson = new Gson().newBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public Tracker(Integer userid) {
        this.userid = userid;

    }

    public void StartTs(Integer projectid){
        this.projectid = projectid;
        String json = gson.toJson(this);
        HTTPPost http = new HTTPPost();
        try {
            http.execute(UrlConstants.startTsUrl,json).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void EndTs(Integer projectid){
        this.projectid = projectid;
        String json = gson.toJson(this);
        HTTPPost http = new HTTPPost();
        try {
            http.execute(UrlConstants.endTsUrl,json).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
