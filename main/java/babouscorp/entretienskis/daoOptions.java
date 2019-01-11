package babouscorp.entretienskis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

public class daoOptions {
    private SQLiteDatabase mybdd;
    private dbEntretienSkis dbHelper;
    private String[] allcolums = { dbEntretienSkis.cOPTIONS_ID, dbEntretienSkis.cOPTIONS_VALEUR};

    public daoOptions(Context context) {
        File f = new File(Environment.getExternalStorageDirectory(), pagePrincipale.APP_NOM + "/" +
                pagePrincipale.APP_NOM + ".db");
        dbHelper = new dbEntretienSkis(context, f.getPath());
    }

    public void open() throws SQLException {
        mybdd = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public String getValeur(String id) {
        Cursor cursor = mybdd.query(dbEntretienSkis.tOPTIONS_NOM, allcolums,
                dbEntretienSkis.cOPTIONS_ID + " = \"" + id + "\"",
                null, null,null, null);
        if (cursor.getCount() == 0) {
            return "none";
        }
        cursor.moveToFirst();
        return cursor.getString(1);

    }

    public long insereOption(metaOptions option) {
        supprOption(option.getId());
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.cOPTIONS_ID,option.getId());
        contentValues.put(dbHelper.cOPTIONS_VALEUR,option.getValeur());
        return mybdd.insert(dbHelper.tOPTIONS_NOM,null, contentValues);

    }

    public long supprOption(String id) {
        return mybdd.delete(dbHelper.tOPTIONS_NOM,
                dbHelper.cOPTIONS_ID + " = \"" + id + "\"", null);
    }
}
