package com.example.ipmedt41617.ipmedt4_h;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ipmedt41617.ipmedt4_h.Models.Oefening;

import java.util.ArrayList;

public class OefeningenAdapter extends ArrayAdapter<Oefening> {

    private ImageView check;
    private LinearLayout linearLayout;
    private Oefening oefening;
    private TextView titelOefening;
    private View convertView;

    public OefeningenAdapter(Context context, ArrayList<Oefening> oefeningen) {
        super(context, R.layout.listrow_oefening, oefeningen);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater modulesInflater = LayoutInflater.from(getContext());
            convertView = modulesInflater.inflate(R.layout.listrow_oefening, parent, false);

            check = (ImageView) convertView.findViewById(R.id.check);
            linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout);

            oefening = getItem(position);
            titelOefening = (TextView) convertView.findViewById(R.id.titelOefening);

            titelOefening.setText(oefening.getNaam());

            if (oefening.getVoltooid() == 1) {
                int color = Color.parseColor("#72e023");
                int color2 = Color.parseColor("#dcfcc4");
                linearLayout.setBackgroundColor(color2);
                check.setColorFilter(color);
            } else {
                int color = Color.parseColor("#c6c6c6");
                int color2 = Color.parseColor("#ffffff");
                linearLayout.setBackgroundColor(color2);
                check.setColorFilter(color);
            }
        }
        this.convertView = convertView;
        return convertView;
    }
}
