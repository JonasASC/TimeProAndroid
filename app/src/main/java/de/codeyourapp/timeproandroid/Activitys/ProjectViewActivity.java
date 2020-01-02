package de.codeyourapp.timeproandroid.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.codeyourapp.timeproandroid.Adapter.RvAdapter;
import de.codeyourapp.timeproandroid.HTTP.HTTPGet;
import de.codeyourapp.timeproandroid.Models.ProjectModel;
import de.codeyourapp.timeproandroid.R;

public class ProjectViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    static List<ProjectModel> projectlist;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_list_view);
        getData();

    }

    public void getData(){
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
        initRecyclerView();

        }

    private void initRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RvAdapter adapter = new RvAdapter(projectlist);
        recyclerView.setAdapter(adapter);
    }


    public void openAddProject(View view){
        setContentView(R.layout.add_project);
    }

    public void addProject(View view){
        ProjectModel pro = new ProjectModel();
        EditText proName = findViewById(R.id.project_name);
        EditText proDesc = findViewById(R.id.project_description);
        EditText proBudget = findViewById(R.id.project_budget);
        pro.addProject(proName.getText().toString(),proDesc.getText().toString(),proBudget.getText().toString());

    }

}
