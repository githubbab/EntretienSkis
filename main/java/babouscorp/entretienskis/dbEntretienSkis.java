package babouscorp.entretienskis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbEntretienSkis extends SQLiteOpenHelper {
    private static final Integer DB_VERSION=2;

    // Table pour les options
    public static String tOPTIONS_NOM="options";
    public static String cOPTIONS_ID="id";
    public static String cOPTIONS_VALEUR="valeur";
    public static String vOPTIONS_ID_DATEDEBUTSAISON="dateDebutSaison";
    private static final String tOPTIONS_CREATE =
            "CREATE TABLE " + tOPTIONS_NOM + "(" +
                    cOPTIONS_ID + " TEXT PRIMARY KEY, " +
                    cOPTIONS_VALEUR + " TEXT );";
    private static final String tOPTIONS_DELETE =
            "DROP TABLE IF EXISTS " + tOPTIONS_NOM + ";";

    // Table pour les skis
    public static String tSKIS_NOM="skis";
    public static String cSKIS_ID="id";
    public static String cSKIS_NOM="nom";
    public static String cSKIS_IMG="image";
    private static final String tSKIS_CREATE =
            "CREATE TABLE " + tSKIS_NOM + "(" +
                    cSKIS_ID + " TEXT PRIMARY KEY, " +
                    cSKIS_NOM + " TEXT," +
                    cSKIS_IMG +" BLOB );";
    private static final String tSKIS_DELETE =
            "DROP TABLE IF EXISTS " + tSKIS_NOM + ";";
    
    // Tables pour les actions(sortie,entretiens,fartage) sur les skis
    public static String tACTIONSSKIS_NOM="actionsSkis";
    public static String cACTIONSSKIS_ID="id";
    public static String cACTIONSSKIS_IDSKIS="idSkis";
    public static String cACTIONSSKIS_DATE="date";
    public static String cACTIONSSKIS_TYPE="type";
    public static String vACTIONSSKIS_TYPE_SORTIE="Sortie";
    public static String vACTIONSSKIS_TYPE_AFFUTAGE="Affutage";
    public static String vACTIONSSKIS_TYPE_FARTAGE="Fartage";
    public static String vACTIONSSKIS_TYPE_GRAPHITE="Graphite";
    public static String vACTIONSSKIS_TYPE_PIERRE="Pierre";
    public static String vACTIONSSKIS_TYPE_REPARATION="Réparation";
    public static String vACTIONSSKIS_TYPE_MAGASIN="Magasin";
    private static final String tACTIONSSKIS_CREATE =
            "CREATE TABLE " + tACTIONSSKIS_NOM + "(" +
                    cACTIONSSKIS_ID + " TEXT PRIMARY KEY, " +
                    cACTIONSSKIS_IDSKIS + " TEXT," +
                    cACTIONSSKIS_DATE + " INTEGER," +
                    cACTIONSSKIS_TYPE +" TEXT );";
    private static final String tACTIONSSKIS_DELETE =
            "DROP TABLE IF EXISTS " + tACTIONSSKIS_NOM + ";";

    public dbEntretienSkis(Context context, String dbname) {
        super(context, dbname, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tOPTIONS_CREATE);
        sqLiteDatabase.execSQL(tSKIS_CREATE);
        sqLiteDatabase.execSQL(tACTIONSSKIS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(tOPTIONS_DELETE);
        sqLiteDatabase.execSQL(tSKIS_DELETE);
        sqLiteDatabase.execSQL(tACTIONSSKIS_DELETE);
        onCreate(sqLiteDatabase);
    }
}
