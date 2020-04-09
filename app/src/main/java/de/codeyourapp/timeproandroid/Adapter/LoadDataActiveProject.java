package de.codeyourapp.timeproandroid.Adapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.codeyourapp.timeproandroid.Activitys.ProjectViewActivity;
import de.codeyourapp.timeproandroid.Constante.UrlConstants;
import de.codeyourapp.timeproandroid.HTTP.HTTPGet;
import de.codeyourapp.timeproandroid.Models.ProjectModel;

public class LoadDataActiveProject {
    public static List<ProjectModel> projectList;

    public static void getData(){
        String result = "";
        HTTPGet http = new HTTPGet();
        try {
            result = http.execute(UrlConstants.getProjectUrl).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Type projectListType = new TypeToken<ArrayList<ProjectModel>>(){}.getType();
        projectList = gson.fromJson(result, projectListType);
    }

    public static void refresh(){
        LoadDataActiveProject.getData();
        ProjectViewActivity.adapter.loadWithNewData(LoadDataActiveProject.projectList);
    }

}
