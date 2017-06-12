package com.example.ipmedt41617.ipmedt4_h.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.ipmedt41617.ipmedt4_h.OefeningActivity;
import com.example.ipmedt41617.ipmedt4_h.OefeningenAdapter;
import com.example.ipmedt41617.ipmedt4_h.R;

public class OefeningFragment extends Fragment {

    String[] oefeningen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_oefeningen, container, false);
        oefeningen = new String[] {"Oefening 1", "Oefening 2", "Oefening 3"};

        ListAdapter oefeningenAdapter = new OefeningenAdapter(getActivity(), oefeningen);

        ListView oefeningenListView = (ListView)view.findViewById(R.id.oefeningenListView);
        oefeningenListView.setAdapter(oefeningenAdapter);

        oefeningenListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), OefeningActivity.class);
                intent.putExtra("oefening", oefeningen[position]);
                startActivity(intent);
                int itemPosition = position;
            }
        });

        return view;
    }
}
