package de.codeyourapp.timeproandroid.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.codeyourapp.timeproandroid.Adapter.RvAdapter;
import de.codeyourapp.timeproandroid.HTTP.HTTPGet;
import de.codeyourapp.timeproandroid.HTTP.HTTPPost;
import de.codeyourapp.timeproandroid.Models.Operator;
import de.codeyourapp.timeproandroid.Models.ProjectModel;
import de.codeyourapp.timeproandroid.Models.Tracker;
import de.codeyourapp.timeproandroid.R;

public class ProjectViewActivity extends AppCompatActivity implements RvAdapter.OnItemListener {

    private RecyclerView recyclerView = null;
    private RvAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    static List<ProjectModel> projectlist;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            parseLoginData();
            System.out.println("on Create with Try cookie" + HTTPPost.sessionCookies);
            getData();
            setContentView(R.layout.project_list_view);
            initRecyclerView();


        }catch (Exception e){
            setContentView(R.layout.login_page);
        }
    }

    public void parseLoginData() throws IOException {
        Gson gson = new Gson();
        FileInputStream myFIS = openFileInput("isLogedIn");
        int i_character;
        String s_temp="";
        while( (i_character = myFIS.read()) != -1) {
            s_temp = s_temp + Character.toString((char)i_character);
        }
        myFIS.close();
        Operator op = gson.fromJson(s_temp, Operator.class);
        System.out.println(op.email);
        System.out.println(op.password);
        op.sendLoginDate(s_temp);
    }


    public void loginCheck(View view) throws IOException {
        String mail;
        String pw;
        TextView loginMail = findViewById(R.id.loginmail);
        TextView loginPassword = findViewById(R.id.loginpw);
        mail = loginMail.getText().toString();
        pw = loginPassword.getText().toString();
        Operator operator = new Operator("jonas@jonas", "test");
        String json = gson.toJson(operator);
        operator.sendLoginDate(json);

        if(operator.acces() == true){
            Log.i("Anmeldung erfolgreich", "true");
            setContentView(R.layout.project_list_view);
            getData();
            initRecyclerView();
            FileOutputStream myFOS = openFileOutput("isLogedIn",MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(myFOS, "UTF-8"));
            writer.write(json);
            System.out.println("wird gespeichert "+ json);
            writer.close();

        }
    }


    public static void getData(){
        String myURL ="http://jwg.zollhaus.net:8080/Hello-Servlet-0.0.1-SNAPSHOT/getActivelist";
        String result = "";
        HTTPGet http = new HTTPGet();
        try {
            result = http.execute(myURL).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Type projectListType = new TypeToken<ArrayList<ProjectModel>>(){}.getType();
        projectlist = gson.fromJson(result, projectListType);


        }

    private void initRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RvAdapter(projectlist, this);
        recyclerView.setAdapter(adapter);

    }

    public void openAddProject(View view){
        setContentView(R.layout.add_project);
    }

    public void addProject(View view) throws ExecutionException, InterruptedException {

        EditText proName = findViewById(R.id.project_name);
        EditText proDesc = findViewById(R.id.project_description);
        EditText proBudget = findViewById(R.id.project_budget);
        EditText proContract = findViewById(R.id.contract);
        ProjectModel pro = new ProjectModel(proName.getText().toString(),proDesc.getText().toString(),proContract.getText().toString(),proBudget.getText().toString());
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(pro);
        Log.i("Json",json);
        pro.addProject(json);
        setContentView(R.layout.project_list_view);
        initRecyclerView();
        refresh();

    }

    public void refresh(){
        getData();
        adapter.loadWithNewData(projectlist);
    }

    @Override
    public void onItemClick(int position) {
        Toast toast = Toast.makeText(this, projectlist.get(position).name,Toast.LENGTH_SHORT);
        toast.show();
        Tracker tr = new Tracker(projectlist.get(position).id,projectlist.get(position).userid.toString());
        Gson gson = new Gson();
        String json = gson.toJson(tr);

        if(projectlist.get(position).active){
            tr.EndTs(json);
        }else{
            tr.StartTs(json);
        }
        refresh();

    }

    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 121:
                adapter.removeProject(item.getGroupId());
                System.out.println("hier wird gelöscht");
                refresh();
                break;
            case 122:
                System.out.println("Edit");

                refresh();
                break;
            default:
                System.out.println("Default");
                return super.onContextItemSelected(item);
        }
        return false;
    }


}
