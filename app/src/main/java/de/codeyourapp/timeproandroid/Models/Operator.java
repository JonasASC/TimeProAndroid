package de.codeyourapp.timeproandroid.Models;

import com.google.gson.annotations.Expose;

import java.util.concurrent.ExecutionException;

import de.codeyourapp.timeproandroid.HTTP.HTTPPost;

public class Operator {

    @Expose
    public String email;
    @Expose
    public String password;
    private String firstname;
    private String lastname;
    private Integer userId;
    private Boolean loggedIn;
    private Integer result;
    private String url;

    public Operator(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String sendLoginDate(String data){
        HTTPPost http  = new HTTPPost();
        try {
            result = http.execute("http://jwg.zollhaus.net:8080/Hello-Servlet-0.0.1-SNAPSHOT/login",data).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String s = "DataSend";

    return s;
    }
    public Boolean acces() {
        loggedIn = false;
        if (result == 200) {
            loggedIn = true;
        }
        return loggedIn;
    }
}
