package com.example.ipmedt41617.ipmedt4_h;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.ipmedt41617.ipmedt4_h.Models.Oefening;
import com.example.ipmedt41617.ipmedt4_h.Models.Patient;
import com.example.ipmedt41617.ipmedt4_h.Models.Stap;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static SQLiteDatabase mSQLDB;
    private static DatabaseHelper mInstance;
    private static final int DATABASEVERSION = 1;
    private static final String DATABASENAME = "ipmedt4.db";
    private SQLiteDatabase db;

    public DatabaseHelper(Context ctx) {
        super(ctx, DATABASENAME, null, DATABASEVERSION);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version ){
        super(context,name,factory, version);
    }

    // Singleton voor het verkijgen van de DatabaseHelper instance

    public static synchronized DatabaseHelper getHelper (Context ctx){
        if (mInstance == null){
            mInstance = new DatabaseHelper(ctx);
            mSQLDB = mInstance.getWritableDatabase();
        }
        return mInstance;
    }

    // Maakt de database één keer aan bij de eerste opstart van de applicatie

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        createTables();
        vullenDb();
    }

    // Het aanmaken van de table in SQLite met de daarbij behorende kolommen verkregen van de models

    private void createTables(){
        db.execSQL("CREATE TABLE " + DatabaseInfo.Tables.PATIENTEN + " (" +
                DatabaseInfo.PatientenColumn.PATIENTNUMMER + " INTEGER PRIMARY KEY, " +
                DatabaseInfo.PatientenColumn.VOORNAAM + " TEXT," +
                DatabaseInfo.PatientenColumn.ACHTERNAAM + " TEXT," +
                DatabaseInfo.PatientenColumn.REVALIDATIETIJD + " INTEGER);"
        );

        db.execSQL("CREATE TABLE " + DatabaseInfo.Tables.OEFENINGEN + " (" +
                DatabaseInfo.OefeningenColumn.ID + " INTEGER PRIMARY KEY, " +
                DatabaseInfo.OefeningenColumn.NAAM + " TEXT," +
                DatabaseInfo.OefeningenColumn.OMSCRHIJVING + " TEXT," +
                DatabaseInfo.OefeningenColumn.WEEK + " INTEGER," +
                DatabaseInfo.OefeningenColumn.DAGVANDEWEEK + " INTEGER," +
                DatabaseInfo.OefeningenColumn.VOLTOOID + " INTEGER," +
                DatabaseInfo.OefeningenColumn.TYPE + " INTEGER);"
        );

        db.execSQL("CREATE TABLE " + DatabaseInfo.Tables.STAPPEN + " (" +
                DatabaseInfo.StappenCOLUMN.ID + " INTEGER PRIMARY KEY, " +
                DatabaseInfo.StappenCOLUMN.STAPNUMMER + " INTEGER," +
                DatabaseInfo.StappenCOLUMN.BLUETOOTHDOELWAARDE + " INTEGER, " +
                DatabaseInfo.StappenCOLUMN.OMSCRHIJVING + " TEXT, " +
                DatabaseInfo.StappenCOLUMN.VIDEONAAM + " TEXT," +
                DatabaseInfo.StappenCOLUMN.VOLTOOID + " INTEGER," +
                DatabaseInfo.StappenCOLUMN.OEFENINGTYPE + " INTEGER);"
        );
    }

    // Drop table SQLlite voor het verwijderen van de doorgegeven tabel

    public void dropTable(String table){
        mSQLDB.execSQL("DROP TABLE IF EXISTS "+ table);
    }

    // Upgrade de SQLite database bij een update van de applicatie

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseInfo.Tables.PATIENTEN + "," + DatabaseInfo.Tables.OEFENINGEN + "," + DatabaseInfo.Tables.STAPPEN);
        onCreate(db);
    }

    // Insert een row in de SQLite database

    public void insert(String table, String nullColumnHack, ContentValues values){
        mSQLDB.insert(table, nullColumnHack, values);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectArgs, String groupBy, String having, String orderBy){
        return mSQLDB.query(table, columns, selection, selectArgs, groupBy, having, orderBy);
    }

    public Cursor rawQuery(String sql){
        return mSQLDB.rawQuery(sql, null);
    }

    public ArrayList<Oefening> querySqliteOefeningen(String query) {
        ArrayList<Oefening> OefeningenList = new ArrayList<>();

        String selectQuery = query;

        Cursor cursor = mSQLDB.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Oefening oefening = new Oefening();
                oefening.setId(cursor.getInt(0));
                oefening.setNaam(cursor.getString(1));
                oefening.setOmschrijving(cursor.getString(2));
                oefening.setWeek(cursor.getInt(3));
                oefening.setDagVanDeWeek(cursor.getInt(4));
                oefening.setVoltooid(cursor.getInt(5));
                oefening.setType(cursor.getInt(6));
                OefeningenList.add(oefening);
            } while (cursor.moveToNext());
        }
        return OefeningenList;
    }

    public ArrayList<Stap> querySqliteStappen(String query) {
        ArrayList<Stap> StappenList = new ArrayList<>();

        String selectQuery = query;

        Cursor cursor = mSQLDB.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Stap stap = new Stap();
                stap.setId(cursor.getInt(0));
                stap.setStapNummer(cursor.getInt(1));
                stap.setBluetoothdoelwaarde(cursor.getInt(2));
                stap.setOmschrijving(cursor.getString(3));
                stap.setVideoNaam(cursor.getString(4));
                stap.setVoltooid(cursor.getInt(5));
                stap.setOefeningType(cursor.getInt(6));
                StappenList.add(stap);
            } while (cursor.moveToNext());
        }
        return StappenList;
    }

    public void updateQuery(String table, String column, Integer value, Integer id){
        ContentValues data = new ContentValues();
        data.put(column, value);
        mSQLDB.update(table, data, "id=" + id, null);
    }

    private void vullenDb(){

        addOefening(1,"Strekken","Oefening voor het aansterken van de spieren",1,1,0,1);
        addOefening(2,"Buigen","Oefening voor het aansterken van de spieren",1,1,0,1);
        addOefening(3,"Op en neer","Oefening voor het aansterken van de spieren",1,2,0,1);
        addOefening(4,"Heen en weer","Oefening voor het aansterken van de spieren",2,3,0,1);
        addOefening(5,"Daling","Oefening voor het aansterken van de spieren",2,2,0,1);
        addOefening(6,"Op en neer","Oefening voor het aansterken van de spieren",2,2,0,1);
        addOefening(7,"Op en neer","Oefening voor het aansterken van de spieren",3,1,0,1);
        addOefening(8,"Heen en weer","Oefening voor het aansterken van de spieren",3,3,0,1);

        addStap(1,1,24,"Positioneer uw been", "oefening_1_1",0,1);
        addStap(2,2,70,"Been omhoog", "oefening_1_2",0,1);
        addStap(3,3,20,"Been omlaag", "oefening_1_3",0,1);
    }

    private void addOefening(int id, String naam, String omschrijving, int week, int dagVanDeWeek, int voltooid, int type){
        ContentValues insertValues = new ContentValues();
        insertValues.put("id", id);
        insertValues.put("naam", naam);
        insertValues.put("omschrijving", omschrijving);
        insertValues.put("week", week);
        insertValues.put("dagVanDeWeek", dagVanDeWeek);
        insertValues.put("voltooid", voltooid);
        insertValues.put("type", type);
        db.insert("OEFENINGEN", null, insertValues);
    }

    private void addStap(int id, int stapNummer, int bluetoothDoelwaarde, String omschrijving, String videoNaam, int voltooid, int oefeningType){
        ContentValues insertValues = new ContentValues();
        insertValues.put("id", id);
        insertValues.put("stapNummer", stapNummer);
        insertValues.put("bluetoothDoelwaarde", bluetoothDoelwaarde);
        insertValues.put("omschrijving", omschrijving);
        insertValues.put("videoNaam", videoNaam);
        insertValues.put("voltooid", voltooid);
        insertValues.put("oefeningType", oefeningType);
        db.insert("STAPPEN", null, insertValues);
    }
}