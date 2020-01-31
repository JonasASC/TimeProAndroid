package de.codeyourapp.timeproandroid.Models;

import java.util.concurrent.ExecutionException;
import de.codeyourapp.timeproandroid.HTTP.HTTPPost;

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
            http.execute("http://jwg.zollhaus.net:8080/Hello-Servlet-0.0.1-SNAPSHOT/startTs",data).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void EndTs(String data){
        HTTPPost http = new HTTPPost();
        try {
            http.execute("http://jwg.zollhaus.net:8080/Hello-Servlet-0.0.1-SNAPSHOT/endts",data).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
