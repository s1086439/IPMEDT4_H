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
                DatabaseInfo.OefeningenColumn.VOLTOOID + " INTEGER);"
        );

        db.execSQL("CREATE TABLE " + DatabaseInfo.Tables.STAPPEN + " (" +
                DatabaseInfo.StappenCOLUMN.ID + " INTEGER PRIMARY KEY, " +
                DatabaseInfo.StappenCOLUMN.STAPNUMMER + " INTEGER," +
                DatabaseInfo.StappenCOLUMN.BLUETOOTHDOELWAARDE + " INTEGER, " +
                DatabaseInfo.StappenCOLUMN.OMSCRHIJVING + " TEXT, " +
                DatabaseInfo.StappenCOLUMN.VIDEONAAM + " TEXT," +
                DatabaseInfo.StappenCOLUMN.VOLTOOID + " INTEGER," +
                DatabaseInfo.StappenCOLUMN.OEFENINGID + " INTEGER);"
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
                oefening.setVoltooid(cursor.getInt(4));
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
                StappenList.add(stap);
            } while (cursor.moveToNext());
        }
        return StappenList;
    }

    public void updateQuery(String table, String column, Integer value, Integer id){
        ContentValues data = new ContentValues();
        data.put(column, value);
        db.update(table, data, "id=" + id, null);
    }

    private void vullenDb(){
        ContentValues insertValues = new ContentValues();
        insertValues.put("id", 1);
        insertValues.put("naam", "Strekken");
        insertValues.put("omschrijving", "Oefening voor het aansterken van de spieren");
        insertValues.put("week", 1);
        insertValues.put("voltooid", 0);
        db.insert("OEFENINGEN", null, insertValues);

        insertValues = new ContentValues();
        insertValues.put("id", 2);
        insertValues.put("naam", "Op en neer");
        insertValues.put("omschrijving", "Oefening voor het aansterken van de spieren");
        insertValues.put("week", 1);
        insertValues.put("voltooid", 0);
        db.insert("OEFENINGEN", null, insertValues);

        insertValues = new ContentValues();
        insertValues.put("id", 3);
        insertValues.put("naam", "Buigen");
        insertValues.put("omschrijving", "Oefening voor het aansterken van de spieren");
        insertValues.put("week", 1);
        insertValues.put("voltooid", 0);
        db.insert("OEFENINGEN", null, insertValues);

        insertValues = new ContentValues();
        insertValues.put("id", 4);
        insertValues.put("naam", "Strekken");
        insertValues.put("omschrijving", "Oefening voor het aansterken van de spieren");
        insertValues.put("week", 2);
        insertValues.put("voltooid", 0);
        db.insert("OEFENINGEN", null, insertValues);

        insertValues = new ContentValues();
        insertValues.put("id", 5);
        insertValues.put("naam", "Buigen");
        insertValues.put("omschrijving", "Oefening voor het aansterken van de spieren");
        insertValues.put("week", 2);
        insertValues.put("voltooid", 0);
        db.insert("OEFENINGEN", null, insertValues);

        insertValues = new ContentValues();
        insertValues.put("id", 6);
        insertValues.put("naam", "Buigen");
        insertValues.put("omschrijving", "Oefening voor het aansterken van de spieren");
        insertValues.put("week", 3);
        insertValues.put("voltooid", 0);
        db.insert("OEFENINGEN", null, insertValues);

        insertValues = new ContentValues();
        insertValues.put("id", 1);
        insertValues.put("stapNummer", 1);
        insertValues.put("bluetoothDoelwaarde", 25);
        insertValues.put("omschrijving", "Positioneer uw been");
        insertValues.put("videoNaam", "oefening_1_1");
        insertValues.put("voltooid", 0);
        insertValues.put("oefeningId", 1);
        db.insert("STAPPEN", null, insertValues);

        insertValues = new ContentValues();
        insertValues.put("id", 2);
        insertValues.put("stapNummer", 2);
        insertValues.put("bluetoothDoelwaarde", 70);
        insertValues.put("omschrijving", "Been omhoog");
        insertValues.put("videoNaam", "oefening_1_2");
        insertValues.put("voltooid", 0);
        insertValues.put("oefeningId", 1);
        db.insert("STAPPEN", null, insertValues);

        insertValues = new ContentValues();
        insertValues.put("id", 3);
        insertValues.put("stapNummer", 3);
        insertValues.put("bluetoothDoelwaarde", 20);
        insertValues.put("omschrijving", "Been omlaag");
        insertValues.put("videoNaam", "oefening_1_3");
        insertValues.put("voltooid", 0);
        insertValues.put("oefeningId", 1);
        db.insert("STAPPEN", null, insertValues);
    }
}