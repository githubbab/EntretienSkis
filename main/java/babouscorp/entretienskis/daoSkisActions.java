package babouscorp.entretienskis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class daoSkisActions {
    private SQLiteDatabase mybdd;
    private dbEntretienSkis dbHelper;
    private String[] allcolums = { dbEntretienSkis.cACTIONSSKIS_ID,
            dbEntretienSkis.cACTIONSSKIS_IDSKIS, dbEntretienSkis.cACTIONSSKIS_DATE,
            dbEntretienSkis.cACTIONSSKIS_TYPE};
    private String android_id;

    public daoSkisActions(Context context) {
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

    private metaSkisActions cursorToSkisActions(Cursor cursor) {
        metaSkisActions entretiens = new metaSkisActions();
        entretiens.setId(cursor.getString(0));
        entretiens.setIdSki(cursor.getString(1));
        entretiens.setDate(cursor.getLong(2));
        entretiens.setType(cursor.getString(3));
        return entretiens;
    }


    public long insereSkisAction(String idSki, long date, String type) {
        ContentValues valeurs = new ContentValues();
        valeurs.put(dbHelper.cACTIONSSKIS_ID, android_id + "-" + System.currentTimeMillis());
        valeurs.put(dbHelper.cACTIONSSKIS_DATE, date);
        valeurs.put(dbHelper.cACTIONSSKIS_IDSKIS, idSki);
        valeurs.put(dbHelper.cACTIONSSKIS_TYPE, type);
        return mybdd.insert(dbHelper.tACTIONSSKIS_NOM,null, valeurs);
    }

    public long supprSkisAction(String id) {
        ContentValues valeurs = new ContentValues();
        return mybdd.delete (
                dbHelper.tACTIONSSKIS_NOM,
                dbHelper.cACTIONSSKIS_ID + " = '" + id + "'",
                null
        );
    }

    public long supprSkisActionbyIdSkis(String idSkis) {
        ContentValues valeurs = new ContentValues();
        return mybdd.delete (
                dbHelper.tACTIONSSKIS_NOM,
                dbHelper.cACTIONSSKIS_IDSKIS + " = '" + idSkis + "'",
                null
        );
    }

    public int getNbSortie4Saison(long lDebutSaison, String strIdSki) {
        int result=0;
        String strWhere = dbEntretienSkis.cACTIONSSKIS_DATE+ ">" + lDebutSaison + " AND " +
                dbEntretienSkis.cACTIONSSKIS_IDSKIS + "='" + strIdSki + "' AND " +
                dbEntretienSkis.cACTIONSSKIS_TYPE + " = '" + dbEntretienSkis.vACTIONSSKIS_TYPE_SORTIE + "'";
        Cursor cursor = mybdd.query(dbEntretienSkis.tACTIONSSKIS_NOM, allcolums,
                strWhere,
                null, null, null, null);
        result = cursor.getCount();
        cursor.close();
        return result;
    }


    public int getNbAffutage4Saison(long lDebutSaison, String strIdSki) {
        Cursor cursor = mybdd.query(dbEntretienSkis.tACTIONSSKIS_NOM, allcolums,
                dbEntretienSkis.cACTIONSSKIS_DATE+ " > "+ lDebutSaison + " AND " +
                        dbEntretienSkis.cACTIONSSKIS_IDSKIS + " = '" + strIdSki + "' AND " +
                        dbEntretienSkis.cACTIONSSKIS_TYPE + " = '" + dbEntretienSkis.vACTIONSSKIS_TYPE_AFFUTAGE + "'",
                null, null, null, null);
        return cursor.getCount();
    }

    public long getDateLastAffutage(long lDebutSaison, String strIdSki) {
        Cursor cursor = mybdd.query(dbEntretienSkis.tACTIONSSKIS_NOM, allcolums,
                dbEntretienSkis.cACTIONSSKIS_DATE+ " > "+ lDebutSaison + " AND " +
                        dbEntretienSkis.cACTIONSSKIS_IDSKIS + " = '" + strIdSki + "' AND " +
                        dbEntretienSkis.cACTIONSSKIS_TYPE + " = '" + dbEntretienSkis.vACTIONSSKIS_TYPE_AFFUTAGE + "'",
                null, null, null, dbEntretienSkis.cACTIONSSKIS_DATE);
        cursor.moveToLast();
        if (cursor.getCount() == 0) return lDebutSaison;
        return cursor.getLong(2);

    }

    public int getSortieAffutage(long lDebutSaison, String strIdSki) {
        long lastAffutage = getDateLastAffutage(lDebutSaison,strIdSki);
        Cursor cursor = mybdd.query(dbEntretienSkis.tACTIONSSKIS_NOM, allcolums,
                dbEntretienSkis.cACTIONSSKIS_DATE+ " > "+ lastAffutage + " AND " +
                        dbEntretienSkis.cACTIONSSKIS_IDSKIS + " = '" + strIdSki + "' AND " +
                        dbEntretienSkis.cACTIONSSKIS_TYPE + " = '" + dbEntretienSkis.vACTIONSSKIS_TYPE_SORTIE + "'",
                null, null, null, null);
        return cursor.getCount();
    }

    public int getNbFartage4Saison(long lDebutSaison, String strIdSki) {
        Cursor cursor = mybdd.query(dbEntretienSkis.tACTIONSSKIS_NOM, allcolums,
                dbEntretienSkis.cACTIONSSKIS_DATE+ " > "+ lDebutSaison + " AND " +
                        dbEntretienSkis.cACTIONSSKIS_IDSKIS + " = '" + strIdSki + "' AND (" +
                        dbEntretienSkis.cACTIONSSKIS_TYPE + " = '" + dbEntretienSkis.vACTIONSSKIS_TYPE_FARTAGE + "' OR " +
                        dbEntretienSkis.cACTIONSSKIS_TYPE + " = '" + dbEntretienSkis.vACTIONSSKIS_TYPE_GRAPHITE + "')",
                null, null, null, null);
        return cursor.getCount();
    }

    public long getDateLastFartage(long lDebutSaison, String strIdSki) {
        Cursor cursor = mybdd.query(dbEntretienSkis.tACTIONSSKIS_NOM, allcolums,
                dbEntretienSkis.cACTIONSSKIS_DATE+ " > "+ lDebutSaison + " AND " +
                        dbEntretienSkis.cACTIONSSKIS_IDSKIS + " = '" + strIdSki + "' AND (" +
                        dbEntretienSkis.cACTIONSSKIS_TYPE + " = '" + dbEntretienSkis.vACTIONSSKIS_TYPE_FARTAGE + "'OR " +
                        dbEntretienSkis.cACTIONSSKIS_TYPE + " = '" + dbEntretienSkis.vACTIONSSKIS_TYPE_GRAPHITE + "')",
                null, null, null, dbEntretienSkis.cACTIONSSKIS_DATE);
        cursor.moveToLast();
        if (cursor.getCount() == 0) return lDebutSaison;
        return cursor.getLong(2);
    }

    public int getSortieFartage(long lDebutSaison, String strIdSki) {
        long lastFartage = getDateLastFartage(lDebutSaison,strIdSki);
        Cursor cursor = mybdd.query(dbEntretienSkis.tACTIONSSKIS_NOM, allcolums,
                dbEntretienSkis.cACTIONSSKIS_DATE+ " > "+ lastFartage + " AND " +
                        dbEntretienSkis.cACTIONSSKIS_IDSKIS + " = '" + strIdSki + "' AND " +
                        dbEntretienSkis.cACTIONSSKIS_TYPE + " = '" + dbEntretienSkis.vACTIONSSKIS_TYPE_SORTIE + "'",
                null, null, null, null);
        return cursor.getCount();
    }

    public int getNbReparation4Saison(long lDebutSaison, String strIdSki) {
        Cursor cursor = mybdd.query(dbEntretienSkis.tACTIONSSKIS_NOM, allcolums,
                dbEntretienSkis.cACTIONSSKIS_DATE+ " > "+ lDebutSaison + " AND " +
                        dbEntretienSkis.cACTIONSSKIS_IDSKIS + " = '" + strIdSki + "' AND (" +
                        dbEntretienSkis.cACTIONSSKIS_TYPE + " = '" + dbEntretienSkis.vACTIONSSKIS_TYPE_REPARATION + "' OR " +
                        dbEntretienSkis.cACTIONSSKIS_TYPE + " = '" + dbEntretienSkis.vACTIONSSKIS_TYPE_MAGASIN + "')",
                null, null, null, null);
        return cursor.getCount();
    }

    public int getNbReparation(String strIdSki) {
        Cursor cursor = mybdd.query(dbEntretienSkis.tACTIONSSKIS_NOM, allcolums,
                        dbEntretienSkis.cACTIONSSKIS_IDSKIS + " = '" + strIdSki + "' AND (" +
                        dbEntretienSkis.cACTIONSSKIS_TYPE + " = '" + dbEntretienSkis.vACTIONSSKIS_TYPE_REPARATION + "' OR " +
                        dbEntretienSkis.cACTIONSSKIS_TYPE + " = '" + dbEntretienSkis.vACTIONSSKIS_TYPE_MAGASIN + "')",
                null, null, null, null);
        return cursor.getCount();
    }


    public List<metaSkisActions> getAll4Saison(long lDebutSaison, String strIdSki) {
        List<metaSkisActions> skisActionsList = new ArrayList<metaSkisActions>();

        Cursor cursor = mybdd.query(dbEntretienSkis.tACTIONSSKIS_NOM, allcolums,
                dbEntretienSkis.cACTIONSSKIS_DATE+ " > "+ lDebutSaison + " AND " +
                dbEntretienSkis.cACTIONSSKIS_IDSKIS + " = '" + strIdSki + "'",
                null, null, null, null);

        cursor.moveToLast();
        while (!cursor.isBeforeFirst()) {
            metaSkisActions skisActions = cursorToSkisActions(cursor);
            skisActionsList.add(skisActions);
            cursor.moveToPrevious();
        }
        return skisActionsList;
    }

    public boolean exportSkis(String strIdSki, String nomExport) {

        nomExport = nomExport.replaceAll("[\\s]","_");
        nomExport = nomExport.replaceAll("[^0-9a-zA-Z\\_\\-]","");
        File exportFile = new File(Environment.getExternalStorageDirectory(),pagePrincipale.APP_NOM + "/" +
                nomExport + ".csv");

        if (exportFile.exists()) {
            exportFile.delete();
        }

        PrintWriter printWriter;
        try {
            exportFile.createNewFile();
            printWriter = new PrintWriter(new FileWriter(exportFile));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Cursor cursor = mybdd.query(dbEntretienSkis.tACTIONSSKIS_NOM, allcolums,
                        dbEntretienSkis.cACTIONSSKIS_IDSKIS + " = '" + strIdSki + "'",
                null, null, null, null);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            metaSkisActions skisActions = cursorToSkisActions(cursor);
            printWriter.println(skisActions.toCSV());
            cursor.moveToNext();
        }

        printWriter.close();

        return true;
    }

    public String getIdSkis4Id(String id) {
        Cursor cursor = mybdd.query(dbEntretienSkis.tACTIONSSKIS_NOM, allcolums,
                dbEntretienSkis.cACTIONSSKIS_ID + " = '"+ id + "'",
                null, null, null, null);
        cursor.moveToFirst();
        return cursor.getString(1);

    }

}
