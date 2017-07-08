package com.example.ipmedt41617.ipmedt4_h.Models;


import java.lang.reflect.Array;

public class Stap {
    private int id, bluetoothdoelwaarde, oefeningId, voltooid, stapNummer;
    private String omschrijving, videoNaam;

    public Stap(){}

    public Stap(int id, int stapNummer, int bluetoothdoelwaarde, String omschrijving, String videoNaam, int voltooid, int oefeningId) {
        this.id = id;
        this.stapNummer = stapNummer;
        this.bluetoothdoelwaarde = bluetoothdoelwaarde;
        this.omschrijving = omschrijving;
        this.videoNaam = videoNaam;
        this.voltooid = voltooid;
        this.oefeningId = oefeningId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBluetoothdoelwaarde() {
        return bluetoothdoelwaarde;
    }

    public void setBluetoothdoelwaarde(int bluetoothdoelwaarde) {
        this.bluetoothdoelwaarde = bluetoothdoelwaarde;
    }

    public int getOefeningId() {
        return oefeningId;
    }

    public void setOefeningId(int oefeningId) {
        this.oefeningId = oefeningId;
    }

    public int getVoltooid() {
        return voltooid;
    }

    public void setVoltooid(int voltooid) {
        this.voltooid = voltooid;
    }

    public int getStapNummer() {
        return stapNummer;
    }

    public void setStapNummer(int stapNummer) {
        this.stapNummer = stapNummer;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getVideoNaam() {
        return videoNaam;
    }

    public void setVideoNaam(String videoNaam) {
        this.videoNaam = videoNaam;
    }
}
