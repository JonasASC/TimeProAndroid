package de.codeyourapp.timeproandroid.Models;

import com.google.gson.annotations.Expose;

import java.util.concurrent.ExecutionException;

import de.codeyourapp.timeproandroid.HTTP.HTTPPost;
import de.codeyourapp.timeproandroid.Constante.UrlConstants;

public class ProjectModel {

    public  String elapsed;
    @Expose
    public  String name;
    public Integer id;
    @Expose
    public String description;
    @Expose
    public String budget;
    @Expose
    public String contract;
    public Integer userid;
    public boolean active;


    public ProjectModel(String name, String description,String contract, String budget){
        this.name = name;
        this.description = description;
        this.budget = budget;

        this.contract = contract;
    }

    public void addProject(String data) throws ExecutionException, InterruptedException {
        HTTPPost http = new HTTPPost();
        http.execute(UrlConstants.addProjectUrl,data).get();
    }




}
