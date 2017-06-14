package com.example.ipmedt41617.ipmedt4_h.Models;

import android.net.Uri;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Oefening {
    private String naamOefening, omschrijvingOefening;
    private ArrayList<Stap> stappen;

    public Oefening(String naamOefening, String omschrijving){
        this.stappen = new ArrayList<Stap>();
        this.naamOefening = naamOefening;
        this.omschrijvingOefening = omschrijving;
    }

    public String getNaamOefening() {
        return this.naamOefening;
    }

    public void toevoegenStap(String omschrijving, String naamVideo,int minBluetoothWaarde, int maxBluetoothWaarde){
        this.stappen.add(new Stap(omschrijving, naamVideo, minBluetoothWaarde, maxBluetoothWaarde));
    }

    public Stap getStapAtIndex(int index){
        return this.stappen.get(index);
    }

    public ArrayList getStappen(){
        return this.stappen;
    }

    public String getOmschrijving() {
        return this.omschrijvingOefening;
    }
}
