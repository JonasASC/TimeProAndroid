package de.codeyourapp.timeproandroid.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.ExecutionException;


import de.codeyourapp.timeproandroid.Adapter.HistoryRvAdapter;
import de.codeyourapp.timeproandroid.Adapter.LoadDataActiveProject;
import de.codeyourapp.timeproandroid.Adapter.LoadDataProjekt;
import de.codeyourapp.timeproandroid.Adapter.LoadDataTime;
import de.codeyourapp.timeproandroid.Adapter.RvAdapterProjectList;
import de.codeyourapp.timeproandroid.Models.DateTime;
import de.codeyourapp.timeproandroid.Models.Operator;
import de.codeyourapp.timeproandroid.Models.ProjectModel;
import de.codeyourapp.timeproandroid.Models.Tracker;
import de.codeyourapp.timeproandroid.R;
import de.codeyourapp.timeproandroid.dialogs.DeleteDialoge;

public class MainActivity extends AppCompatActivity implements RvAdapterProjectList.OnNoteListener, RvAdapterProjectList.OnNoteLongListener, PopupMenu.OnMenuItemClickListener, HistoryRvAdapter.HistoryOnNoteListener, HistoryRvAdapter.HistoryOnNoteLongListener {

    private RecyclerView recyclerView = null;
    public static RvAdapterProjectList adapter;
    public static HistoryRvAdapter historyAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Gson gson = new Gson();
    int position;
    ListView listView;

    @SuppressLint("WrongConstant")
    @Override

    // Diese Methode ist der Einstiegspunkt sie wird aufgerufen wen die App gestartet wird.
    // Die Methode parseLoginDate wird versucht aufzurufen diese ist dafür zuständig, ob der User bereits einmal angemeldet war
    // Sollte der Try erfolgreich gewesen sein, so wird Direkt der ProjektView aufgerufen.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            parseLoginData();
            LoadDataActiveProject.getData();
            setContentView(R.layout.project_list_view);
            initRecyclerView();
            createNotificationChannel();
        }catch (Exception e){
                setContentView(R.layout.login_page);
                createNotificationChannel();
        }

    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(getString(R.string.NEWS_CHANNEL_ID),getString(R.string.CHANNEL_NEWS), NotificationManager.IMPORTANCE_DEFAULT );
            notificationChannel.setDescription(getString(R.string.CHANNEL_DESCRIPTION));
            notificationChannel.setShowBadge(true);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationChannel.setSound(null,null);
        }
    }

    // Hier wird der Interne Speicher des Smartphones abgefragt ob es eine Variable mit "isLogedIn"vorhanden ist
    // Sollte das so sein, wird die Operator Classe mit den Credantials gefüllt.
    public void parseLoginData() throws IOException {
        FileInputStream myFIS = openFileInput("isLogedIn");
        int i_character;
        String s_temp="";
        while( (i_character = myFIS.read()) != -1) {
            s_temp = s_temp + Character.toString((char)i_character);
        }
        myFIS.close();
        Operator op = gson.fromJson(s_temp, Operator.class);
        op.sendLoginDate(s_temp);
    }
    // Diese Methode wird aufgerufen wenn der User in der Anmelde Maske den Login Button drückt
    // Es wird ein Objekt der Klasse Operator erstellt und dieser mit Mail und Passwort befüllt.
    // Das Operator Objekt wird von GSON in ein JSON Objekt umgewandelt.
    // Durch die Methode sendLoginData werden die Daten an die Schnittstelle gesedet.
    public void loginCheck(View view) throws IOException {
        String mail;
        String pw;
        TextView loginMail = findViewById(R.id.loginmail);
        TextView loginPassword = findViewById(R.id.loginpw);
        mail = loginMail.getText().toString();
        pw = loginPassword.getText().toString();
        Operator operator = new Operator(mail, pw);
        String json = gson.toJson(operator);
        operator.sendLoginDate(json);
        // Sollte vom Server der Code 200 zurück kommen, so wird die Variable acces auf true gesetzt
        // und die Projekte und der ProjektView wird geladen.
        // Danach wird dann auch der Interne Speicher mit der "isLogedIn" befüllt.
        if(operator.acces() == true){
            setContentView(R.layout.project_list_view);
            LoadDataActiveProject.getData();
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
        adapter = new RvAdapterProjectList(LoadDataActiveProject.projectList, this,this);
        recyclerView.setAdapter(adapter);

    }
    public void openAddProject(View view){
        setContentView(R.layout.add_project);
    }

    public void addProject(View view) throws ExecutionException, InterruptedException {
        EditText proName = findViewById(R.id.project_name);
        EditText proDesc = findViewById(R.id.project_description);
        EditText time = findViewById(R.id.project_time);
        EditText proContract = findViewById(R.id.contract);
        String proTime = time.getText().toString();
        ProjectModel pro = new ProjectModel(proName.getText().toString(),proDesc.getText().toString(),proContract.getText().toString(),proTime);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(pro);
        pro.addProject(json);
        setContentView(R.layout.project_list_view);
        initRecyclerView();
        LoadDataActiveProject.refresh();
    }

    @Override
    public void onNoteClick(int position) {
        Tracker tr = new Tracker(LoadDataActiveProject.projectList.get(position).userid);
        Intent mainActivity = new Intent(this,MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(mainActivity);
        PendingIntent resultPendingIntet = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,getString(R.string.NEWS_CHANNEL_ID))
                .setSmallIcon(R.drawable.ic_baseline_access_time_24)
                .setContentTitle("Eine TimePro Zeiterfassung läuft")
                .setContentText("Name: "+ LoadDataActiveProject.projectList.get(position).name)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setContentIntent(resultPendingIntet)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Name: "+ LoadDataActiveProject.projectList.get(position).name));
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        ProjectModel project = null;
        for (int p = 0 ; p < LoadDataActiveProject.projectList.size() ; p++){
            if (p == position)
                continue;
            project = LoadDataActiveProject.projectList.get(p);
            if (project.active) {
                tr.EndTs(project.id);
                notificationManager.cancel(2);
            }
        }
        project = LoadDataActiveProject.projectList.get(position);

        if (project.active) {
            tr.EndTs(project.id);
            notificationManager.cancel(2);
        } else {
            tr.StartTs(project.id);
            notificationManager.notify(2, notification);
        }

        LoadDataActiveProject.refresh();
    }

    @Override
    public void onNoteLongClick(int position, View view) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.inflate(R.menu.option_menu);
        menu.setOnMenuItemClickListener(this);
        menu.show();
        this.position = position;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        //Toast.makeText(this, "Item Clickt",Toast.LENGTH_SHORT).show();
        switch (menuItem.getItemId()){
            case R.id.menu_item_remove:
                DeleteDialoge delete = new DeleteDialoge(position);
                delete.show(getSupportFragmentManager(),"example dialog");
                //adapter.removeProject(position);
                return true;
            case R.id.menu_item_edit:
                setContentView(R.layout.project_info);
                loadProjectInfo(position);
                return true;
            case R.id.menu_item_history:
                historyList(RvAdapterProjectList.project.get(position).id);
            case R.id.menu_item_createBubble:

            default:
                return false;
        }
    }

    public void loadProjectInfo(int project){
        LoadDataProjekt.getData();
        TextView infoName = findViewById(R.id.info_projekt_name);
        TextView infoContract = findViewById(R.id.info_project_contrect);
        TextView infoDescription = findViewById(R.id.info_project_descrioption);
        infoName.setText(LoadDataProjekt.fullProjectList.get(project).name);
        infoDescription.setText(LoadDataProjekt.fullProjectList.get(project).description);
        infoContract.setText(LoadDataProjekt.fullProjectList.get(project).contract);
    }

    public void historyList(int projectid){
        setContentView(R.layout.history);
        LoadDataTime.getData(projectid);
        recyclerView = (RecyclerView) findViewById(R.id.history_rv);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        historyAdapter = new HistoryRvAdapter(LoadDataTime.dataTimeList, this,this);
        recyclerView.setAdapter(historyAdapter);

    }


    public void backToProjectView(View view){
        setContentView(R.layout.project_list_view);
        LoadDataActiveProject.refresh();
        initRecyclerView();
    }


    @Override
    public void historyOnNoteClick(int position) {
        DateTime time = null;
        time = LoadDataTime.dataTimeList.get(position);
        System.out.println("das klappt"+ time.projectid);
        setContentView(R.layout.edit_time);
        EditText startts = findViewById(R.id.edit_startts);
        EditText endts = findViewById(R.id.edit_endts);
        startts.setText(time.startts);
        endts.setText(time.endts);
    }

    @Override
    public void historyOnNoteLongListener(int position, View view) {
        DateTime time = null;
        time = LoadDataTime.dataTimeList.get(position);
        System.out.println("das klappt"+ time.projectid);
    }

    public void editTimeSlices(){


    }
}
