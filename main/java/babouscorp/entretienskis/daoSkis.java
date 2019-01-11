package babouscorp.entretienskis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.provider.Settings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class daoSkis {
    private SQLiteDatabase mybdd;
    private dbEntretienSkis dbHelper;
    private String[] allcolums = { dbEntretienSkis.cSKIS_ID, dbEntretienSkis.cSKIS_NOM,
            dbEntretienSkis.cSKIS_IMG };
    private String android_id;
    private Context myContext;

    public daoSkis(Context context) {
        myContext = context;
        File f = new File(Environment.getExternalStorageDirectory(),pagePrincipale.APP_NOM + "/" +
                pagePrincipale.APP_NOM + ".db");
        dbHelper = new dbEntretienSkis(context,f.getPath());

        android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public void open() throws SQLException {
        mybdd = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    private metaSkis cursorToSkis(Cursor cursor) {
        metaSkis skis = new metaSkis();
        skis.setId(cursor.getString(0));
        skis.setNom(cursor.getString(1));
        return skis;
    }

    public long insereSki(String nom) {
        ContentValues valeurs = new ContentValues();
        valeurs.put(dbHelper.cSKIS_NOM, nom);
        valeurs.put(dbHelper.cSKIS_ID, android_id + "-" + System.currentTimeMillis());
        return mybdd.insert(dbHelper.tSKIS_NOM,null, valeurs);
    }

    public long supprSki(String id) {
        daoSkisActions daoSkisActions = new daoSkisActions(myContext);
        daoSkisActions.open();
        daoSkisActions.supprSkisActionbyIdSkis(id);
        daoSkisActions.close();
        return mybdd.delete(dbHelper.tSKIS_NOM, dbHelper.cSKIS_ID + " = '" + id + "'",
                null);
    }

    public List<metaSkis> getAll() {
        List<metaSkis> skisList = new ArrayList<metaSkis>();

        Cursor cursor = mybdd.query(dbEntretienSkis.tSKIS_NOM, allcolums,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            metaSkis skis = cursorToSkis(cursor);
            skisList.add(skis);
            cursor.moveToNext();
        }
        return  skisList;
    }

    public metaSkis getOne(String id) {
        Cursor cursor = mybdd.query(dbEntretienSkis.tSKIS_NOM, allcolums,
                dbEntretienSkis.cSKIS_ID + " = \"" + id + "\"", null, null, null, null);
        cursor.moveToFirst();
        return cursorToSkis(cursor);
    }

    public void setNomSkis(String id, String nom) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbEntretienSkis.cSKIS_NOM,nom);
        mybdd.update(dbEntretienSkis.tSKIS_NOM,contentValues,dbEntretienSkis.cSKIS_ID + " = '" + id + "'",null);

    }

}
