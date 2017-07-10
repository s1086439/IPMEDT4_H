package com.example.ipmedt41617.ipmedt4_h.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ipmedt41617.ipmedt4_h.DatabaseHelper;
import com.example.ipmedt41617.ipmedt4_h.DatabaseInfo;
import com.example.ipmedt41617.ipmedt4_h.R;
import com.example.ipmedt41617.ipmedt4_h.SharedPrefs;

import static com.example.ipmedt41617.ipmedt4_h.DatabaseInfo.Tables.PATIENTEN;

public class PatientgegevensActivity extends AppCompatActivity {
    private int weken, naamSet, wekenSet;
    private DatabaseHelper dbHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientgegevens);
        this.weken = 0;
        this.wekenSet = 0;
        this.context = this;

        this.dbHelper = DatabaseHelper.getHelper(this);

        final EditText voornaamText = (EditText)findViewById(R.id.voornaamText);
        Button gaVerderButton = (Button) findViewById(R.id.gaVerderButton);

        final EditText aantalWeken = (EditText) findViewById(R.id.aantalWekenText);

        gaVerderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    if(voornaamText.getText().toString().length() == 0 && naamSet == 0){
                        voornaamText.setError("Uw naam graag.");
                    } else {
                        naamSet = 1;
                    }

                    if(aantalWeken.getText().toString().length() == 0 && wekenSet == 0){
                        aantalWeken.setError("De weken graag.");
                    } else {
                        wekenSet = 1;
                    }

                    if(naamSet == 1 && wekenSet == 1){
                        ContentValues values = new ContentValues();
                        values.put(DatabaseInfo.PatientenColumn.VOORNAAM, voornaamText.getText().toString());
                        values.put(DatabaseInfo.PatientenColumn.ACHTERNAAM, "");
                        values.put(DatabaseInfo.PatientenColumn.REVALIDATIETIJD, Integer.parseInt(aantalWeken.getText().toString()));
                        dbHelper.insert(DatabaseInfo.Tables.PATIENTEN, null, values);
                        SharedPrefs.getInstance(context).putBooleanValue("firstRun", false);

                        startActivity(new Intent(PatientgegevensActivity.this, MenuActivity.class));
                        finish();
                    }
            }
        });

    }
}
