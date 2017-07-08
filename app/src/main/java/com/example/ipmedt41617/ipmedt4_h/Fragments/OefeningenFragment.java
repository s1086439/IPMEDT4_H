package com.example.ipmedt41617.ipmedt4_h.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ipmedt41617.ipmedt4_h.Activities.OefeningActivity;
import com.example.ipmedt41617.ipmedt4_h.DatabaseHelper;
import com.example.ipmedt41617.ipmedt4_h.Models.Oefening;
import com.example.ipmedt41617.ipmedt4_h.OefeningenAdapter;
import com.example.ipmedt41617.ipmedt4_h.R;

import java.util.ArrayList;
import java.util.List;

public class OefeningenFragment extends Fragment {

    private ArrayList<Oefening> oefeningen;
    private DatabaseHelper dbHelper;
    private OefeningenAdapter oefeningenAdapter;
    private int position;
    private ListView oefeningenListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_oefeningen, container, false);

        Bundle args = getArguments();
        position = args.getInt("position");

        dbHelper = DatabaseHelper.getHelper(getActivity());

        oefeningen = new ArrayList<>();

        for(Oefening oefening : dbHelper.querySqliteOefeningen("SELECT  * FROM OEFENINGEN WHERE week=" + "" + position)){
            this.oefeningen.add(oefening);
        }

        oefeningenAdapter = new OefeningenAdapter(getActivity(), oefeningen);

        oefeningenListView = (ListView)view.findViewById(R.id.oefeningenListView);
        oefeningenListView.setAdapter(oefeningenAdapter);

        oefeningenListView.setDivider(null);

        oefeningenListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), OefeningActivity.class);
            intent.putExtra("Oefening", oefeningen.get(position));
            startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(oefeningenListView != null){
            oefeningenListView.deferNotifyDataSetChanged();
        }
    }
}
