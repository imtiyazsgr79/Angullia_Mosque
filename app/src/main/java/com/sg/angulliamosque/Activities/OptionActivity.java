package com.sg.angulliamosque.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sg.angulliamosque.R;

import in.ifarms.com.General.Dashboard;
import in.ifarms.com.General.WorkspaceActivity;

public class OptionActivity extends AppCompatActivity {
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        ImageView ifarms=findViewById(R.id.ifarms);
        ImageView mosque=findViewById(R.id.mosque);

        sharedPref = getSharedPreferences("sharedPrefs", MODE_PRIVATE);

        String name = sharedPref.getString("name", "");
        String token = sharedPref.getString("token", "");
        String role = sharedPref.getString("role", "");

        mosque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OptionActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        ifarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this, Dashboard.class);
                intent.putExtra("token",token );
                intent.putExtra("username", name);
                intent.putExtra("role", role);
                startActivity(intent);
            }
        });

    }
}