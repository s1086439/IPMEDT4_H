package com.example.ipmedt41617.ipmedt4_h.Models;


import java.lang.reflect.Array;

public class Stap{
    private String omschrijving, naamVideo;
    private int bluetoothMinWaarde, bluetoothDoelWaarde;

    public Stap(String omschrijving, String naamVideo, int bluetoothMinWaarde, int bluetoothDoelWaarde){
        this.omschrijving = omschrijving;
        this.naamVideo = naamVideo;
        this.bluetoothMinWaarde = bluetoothMinWaarde;
        this.bluetoothDoelWaarde = bluetoothDoelWaarde;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getNaamVideo() {
        return naamVideo;
    }

    public void setNaamVideo(String naamVideo) {
        this.naamVideo = naamVideo;
    }

    public int getBluetoothMinWaarde() {
        return bluetoothMinWaarde;
    }

    public void setBluetoothMinWaarde(int bluetoothMinWaarde) {
        this.bluetoothMinWaarde = bluetoothMinWaarde;
    }

    public int getBluetoothDoelWaarde() {
        return bluetoothDoelWaarde;
    }

    public void setBluetoothDoelWaarde(int bluetoothDoelWaarde) {
        this.bluetoothDoelWaarde = bluetoothDoelWaarde;
    }

    public void afspelenGeluidVoltooid(){

    }
}
