package de.codeyourapp.timeproandroid.Models;

import com.google.gson.annotations.Expose;

import java.util.concurrent.ExecutionException;

import de.codeyourapp.timeproandroid.HTTP.HTTPPost;
import de.codeyourapp.timeproandroid.Constante.UrlConstants;
import de.codeyourapp.timeproandroid.HTTP.HTTPPostProject;

public class ProjectModel {

    public  String elapsed;
    @Expose
    public  String name;
    @Expose
    public Integer id;
    @Expose
    public String description;
    public Integer budget;
    @Expose
    public String contract;
    public Integer userid;
    public boolean active;
    @Expose
    public String projectTime;
    public Double progress = 0.0;


    public ProjectModel(String name, String description,String contract, String projectTime){
        this.name = name;
        this.description = description;
        this.projectTime = projectTime;
        this.contract = contract;

    }


    public ProjectModel(Integer id){
        this.id = id;
    }

    public void addProject(String data) throws ExecutionException, InterruptedException {
        HTTPPostProject http = new HTTPPostProject();
        http.execute(UrlConstants.addProjectUrl,data).get();
    }




}
