package com.example.ipmedt41617.ipmedt4_h.Models;

import android.net.Uri;

public class Oefening {
    private String titel, omschrijving;
    private int stappen;

    public Oefening(int stappen, String titel, String omschrijving){
        this.titel = titel;
        this.omschrijving = omschrijving;
        this.stappen = stappen;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public String getTitel() {
        return titel;
    }

    public int getStappen() {
        return stappen;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public void setStappen(int stappen) {
        this.stappen = stappen;
    }
}
