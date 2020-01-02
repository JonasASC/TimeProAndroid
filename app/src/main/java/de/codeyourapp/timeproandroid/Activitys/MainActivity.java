package de.codeyourapp.timeproandroid.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.codeyourapp.timeproandroid.Adapter.RvAdapter;
import de.codeyourapp.timeproandroid.HTTP.HTTPGet;
import de.codeyourapp.timeproandroid.Models.LoginModel;
import de.codeyourapp.timeproandroid.Models.Operator;
import de.codeyourapp.timeproandroid.Models.ProjectModel;
import de.codeyourapp.timeproandroid.R;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
    }

    public void loginCheck(View view){
        String mail;
        String pw;
        TextView loginMail = findViewById(R.id.loginmail);
        TextView loginPassword = findViewById(R.id.loginpw);
        mail = loginMail.getText().toString();
        pw = loginPassword.getText().toString();

        Operator operator = new Operator("jonas@jonas", "test");
        Gson gson = new Gson();
        String json = gson.toJson(operator);
        operator.sendLoginDate(json);

        if(operator.acces() == true){
            Log.i("Anmeldung erfolgreich", "true");
            Intent intent = new Intent(this, ProjectViewActivity.class);
            startActivity(intent);

        }
    }
}
