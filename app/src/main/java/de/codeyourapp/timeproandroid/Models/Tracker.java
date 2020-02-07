package de.codeyourapp.timeproandroid.Models;

import java.util.concurrent.ExecutionException;

import de.codeyourapp.timeproandroid.HTTP.HTTPPost;
import de.codeyourapp.timeproandroid.Constante.UrlConstants;

public class Tracker {
    Integer projectid;
    String userid;

    public Tracker(Integer projectid, String userid) {
        this.projectid = projectid;
        this.userid = userid;
    }

    public void StartTs(String data){
        HTTPPost http = new HTTPPost();
        try {
            http.execute(UrlConstants.startTsUrl,data).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void EndTs(String data){
        HTTPPost http = new HTTPPost();
        try {
            http.execute(UrlConstants.endTsUrl,data).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
