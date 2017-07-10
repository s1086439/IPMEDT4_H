package com.example.ipmedt41617.ipmedt4_h.Fragments;


import android.database.Cursor;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ipmedt41617.ipmedt4_h.DatabaseHelper;
import com.example.ipmedt41617.ipmedt4_h.DatabaseInfo;
import com.example.ipmedt41617.ipmedt4_h.Models.Patient;
import com.example.ipmedt41617.ipmedt4_h.R;

import static com.example.ipmedt41617.ipmedt4_h.R.id.aantalWekenText;
import static com.example.ipmedt41617.ipmedt4_h.R.id.view;

public class ProfielFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private TextView voornaam, oefeningenVoltooid, wekenText1, wekenText2;
    private Cursor rs;
    private ProgressBar progressie;
    private Patient patient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profiel, container, false);

        this.dbHelper = DatabaseHelper.getHelper(getActivity());
        voornaam = (TextView) view.findViewById(R.id.naamTextId);
        oefeningenVoltooid = (TextView) view.findViewById(R.id.oefeningenVoltooidText);
        wekenText1 = (TextView) view.findViewById(R.id.wekenText1);
        wekenText2 = (TextView) view.findViewById(R.id.wekenText2);
        progressie = (ProgressBar) view.findViewById(R.id.progressBar);

        patient = dbHelper.querySqlitePatient("SELECT * FROM PATIENTEN");

        wekenText1.setText((patient.getRevalidatietijdHuidig()+1) + "/" + patient.getRevalidatietijd());
        progressie.setMax(patient.getRevalidatietijd());
        wekenText2.setText("U zit in week: " + (patient.getRevalidatietijdHuidig() + 1));

        progressie.setProgress((patient.getRevalidatietijdHuidig() + 1));

        rs = dbHelper.rawQuery("SELECT count(id) FROM OEFENINGEN WHERE voltooid=1");
        rs.moveToFirst();
        Integer voltooid = rs.getInt(0);
        rs.close();

        voornaam.setText("Welkom " + patient.getVoornaam());
        oefeningenVoltooid.setText("Oefeningen voltooid: " + voltooid);

        return view;


    }

}
