package com.example.ipmedt41617.ipmedt4_h;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class OefeningenAdapter extends ArrayAdapter<String> {
    public OefeningenAdapter(Context context, String[] modules) {
        super(context, R.layout.listrow_oefening, modules);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater modulesInflater = LayoutInflater.from(getContext());
            convertView = modulesInflater.inflate(R.layout.listrow_oefening, parent, false);

            String oefening = getItem(position);
            TextView titelOefening = (TextView) convertView.findViewById(R.id.titelOefening);

            titelOefening.setText(oefening);
        }
        return convertView;
    }

}
