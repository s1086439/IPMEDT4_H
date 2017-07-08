package com.example.ipmedt41617.ipmedt4_h;


public class DatabaseInfo {

    // Namen van de tabellen die moeten worden aangemaakt

    public class Tables {
        public static final String PATIENTEN = "patienten";
        public static final String OEFENINGEN = "oefeningen";
        public static final String STAPPEN = "stappen";
    }

    public class PatientenColumn {
        public static final String PATIENTNUMMER = "patientnummer";
        public static final String VOORNAAM = "voornaam";
        public static final String ACHTERNAAM = "achternaam";
        public static final String REVALIDATIETIJD = "revalidatietijd";
    }

    public class OefeningenColumn {
        public static final String ID = "id";
        public static final String NAAM = "naam";
        public static final String OMSCRHIJVING = "omschrijving";
        public static final String WEEK = "week";
        public static final String VOLTOOID = "voltooid";
    }

    public class StappenCOLUMN {
        public static final String ID = "id";
        public static final String STAPNUMMER = "stapNummer";
        public static final String BLUETOOTHDOELWAARDE = "bluetoothdoelwaarde";
        public static final String OMSCRHIJVING = "omschrijving";
        public static final String VIDEONAAM = "videoNaam";
        public static final String VOLTOOID = "voltooid";
        public static final String OEFENINGID = "oefeningId";
    }
}
