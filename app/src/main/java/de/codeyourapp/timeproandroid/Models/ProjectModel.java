package de.codeyourapp.timeproandroid.Models;

public class ProjectModel {
    public Integer userid;
    public boolean active;
    public  String elapsed;
    public  String name;
    public Integer id;
    public String description;
    public String budget;

    public void addProject(String name, String description, String budget){
        this.name = name;
        this.description = description;
        this.budget = budget;


    }


}
