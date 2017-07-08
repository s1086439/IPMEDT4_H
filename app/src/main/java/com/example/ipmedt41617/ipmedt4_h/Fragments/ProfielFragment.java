package com.example.ipmedt41617.ipmedt4_h.Fragments;


import android.database.Cursor;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ipmedt41617.ipmedt4_h.DatabaseHelper;
import com.example.ipmedt41617.ipmedt4_h.DatabaseInfo;
import com.example.ipmedt41617.ipmedt4_h.R;

import static com.example.ipmedt41617.ipmedt4_h.R.id.aantalWekenText;
import static com.example.ipmedt41617.ipmedt4_h.R.id.view;

public class ProfielFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private TextView voornaam, oefeningenVoltooid, wekenText;
    private Cursor rs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profiel, container, false);

        this.dbHelper = DatabaseHelper.getHelper(getActivity());
        voornaam = (TextView) view.findViewById(R.id.naamTextId);
        oefeningenVoltooid = (TextView) view.findViewById(R.id.oefeningenVoltooidText);
        wekenText = (TextView) view.findViewById(R.id.wekenText);



        rs = dbHelper.query(DatabaseInfo.Tables.PATIENTEN, new String[]{"*"}, null, null, null, null, null);
        rs.moveToFirst();
        String naam = (String) rs.getString(rs.getColumnIndex("voornaam"));

        rs = dbHelper.rawQuery("SELECT count(id) FROM OEFENINGEN WHERE voltooid=1");
        rs.moveToFirst();
        Integer voltooid = rs.getInt(0);
        //Integer voltooid = rs.getInt(rs.getColumnIndex("oefeningen"));
        rs.close();

        voornaam.setText("Welkom " + naam);
        oefeningenVoltooid.setText("Oefeningen voltooid: " + voltooid);
        wekenText.setText("U zit in week 1");



        return view;


    }

}
