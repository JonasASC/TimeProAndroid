package de.codeyourapp.timeproandroid.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.concurrent.ExecutionException;

import de.codeyourapp.timeproandroid.Constante.UrlConstants;
import de.codeyourapp.timeproandroid.HTTP.HTTPPostProject;

public class DateTime {
    @Expose
    public Integer id;
    @Expose
    public Long projectid;
    @SerializedName("startts")
    @Expose
    public String startts = null;
    @SerializedName("endts")
    @Expose
    public String endts = null;
    @Expose
    public String active = null;

    public DateTime( Long projectid, String startts, String endts, String active, Integer id) {
        this.projectid = projectid;
        this.startts = startts;
        this.endts = endts;
        this.active = active;
        this.id = id;
    }

    public void updateTime(String data) throws ExecutionException, InterruptedException {
        HTTPPostProject http = new HTTPPostProject();
        http.execute(UrlConstants.addProjectUrl,data).get();
    }

}
