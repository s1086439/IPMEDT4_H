package com.example.ipmedt41617.ipmedt4_h;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void openenOverzichtOefeningen(View view){
        Intent intent = new Intent(this, ExercisesActivity.class);
        startActivity(intent);
    }

}