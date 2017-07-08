package com.example.ipmedt41617.ipmedt4_h.Models;

import android.net.Uri;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Oefening implements Serializable{

    private int id, week, voltooid;
    private String naam, omschrijving;

    public Oefening(){}

    public Oefening(int id, String naam, String omschrijving, int week, int voltooid) {
        this.id = id;
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.week = week;
        this.voltooid = voltooid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getVoltooid() {
        return voltooid;
    }

    public void setVoltooid(int voltooid) {
        this.voltooid = voltooid;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }
}
