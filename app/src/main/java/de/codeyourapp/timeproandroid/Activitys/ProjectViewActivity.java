package de.codeyourapp.timeproandroid.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.PopupMenu;
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

import de.codeyourapp.timeproandroid.Adapter.LoadData;
import de.codeyourapp.timeproandroid.Adapter.RvAdapter;
import de.codeyourapp.timeproandroid.HTTP.HTTPGet;
import de.codeyourapp.timeproandroid.HTTP.HTTPPost;
import de.codeyourapp.timeproandroid.Models.Operator;
import de.codeyourapp.timeproandroid.Models.ProjectModel;
import de.codeyourapp.timeproandroid.Models.Tracker;
import de.codeyourapp.timeproandroid.R;
import de.codeyourapp.timeproandroid.Constante.UrlConstants;

public class ProjectViewActivity extends AppCompatActivity implements RvAdapter.OnNoteListener, RvAdapter.OnNoteLongListener, PopupMenu.OnMenuItemClickListener{

    private RecyclerView recyclerView = null;
    public static RvAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Gson gson = new Gson();
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            parseLoginData();
            System.out.println("on Create with Try cookie" + HTTPPost.sessionCookies);
            LoadData.getData();
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
            setContentView(R.layout.project_list_view);
            LoadData.getData();
            initRecyclerView();
            FileOutputStream myFOS = openFileOutput("isLogedIn",MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(myFOS, "UTF-8"));
            writer.write(json);
            writer.close();
        }
    }

    private void initRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RvAdapter(LoadData.projectlist, this,this);
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
        LoadData.refresh();
    }

    @Override
    public void onNoteClick(int position) {
        System.out.println("Hier wird geklcikt "+ position);
        Tracker tr = new Tracker(LoadData.projectlist.get(position).id,LoadData.projectlist.get(position).userid.toString());
        Gson gson = new Gson();
        String json = gson.toJson(tr);
        if(LoadData.projectlist.get(position).active){
            tr.EndTs(json);
        }else{
            tr.StartTs(json);
        }
        LoadData.refresh();
    }

    @Override
    public void onNoteLongClick(int position, View view) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.inflate(R.menu.option_menu);
        menu.setOnMenuItemClickListener(this);
        menu.show();
        System.out.println("hier wird lange geklickt");
        this.position = position;
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        Toast.makeText(this, "Item Clickt",Toast.LENGTH_SHORT).show();
        switch (menuItem.getItemId()){
            case R.id.menu_item_remove:
                adapter.removeProject(position);
                return true;
            case R.id.menu_item_edit:
                System.out.println("Edit");
                return true;
            default:
                System.out.println("default");
                return false;
        }
    }
}
