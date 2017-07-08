package com.example.ipmedt41617.ipmedt4_h.Models;


public class Patient {
    private int patientnummer, revalidatietijd;
    private String voornaam, achternaam;

    public Patient(int patientnummer, String voornaam, String achternaam, int revalidatietijd) {
        this.patientnummer = patientnummer;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.revalidatietijd = revalidatietijd;
    }

    public int getPatientnummer() {
        return patientnummer;
    }

    public void setPatientnummer(int patientnummer) {
        this.patientnummer = patientnummer;
    }

    public int getRevalidatietijd() {
        return revalidatietijd;
    }

    public void setRevalidatietijd(int revalidatietijd) {
        this.revalidatietijd = revalidatietijd;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }
}
