package babouscorp.entretienskis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

public class pagePrincipale extends AppCompatActivity {

    public static final String APP_NOM="EntretienSkis";
    private static String TAG="EntretienSkis";

    private static final int PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_principale);

        if (checkPermission()) {

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), pageAjoutSkis.class);
                    startActivityForResult(intent,1);
                }
            });

            File f = new File(Environment.getExternalStorageDirectory(), APP_NOM);
            if (!f.exists()) {
                f.mkdirs();
            }
            else {
                if (!f.isDirectory()) {
                    f.delete();
                    f.mkdirs();
                }
            }

            final ListView listView = findViewById(R.id.lvSki_PagePrincipale);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(view.getContext(),pageAjoutActionsSkis.class);
                    metaRow4Skis_PagePrincipale row = (metaRow4Skis_PagePrincipale) adapterView.getAdapter().getItem(i);
                    intent.putExtra("SKI_ID", row.getId());
                    startActivityForResult(intent, 1);
                }
            });

            refreshListView();

        }
        else {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Si vous voyez cela donner les droits");
            toolbar.setSubtitle("et redémarrer l'application");
            requestPermission();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page_principale, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intentOptions = new Intent(pagePrincipale.this, pageOptions.class);
            startActivityForResult(intentOptions,1);
        }
        if (id == R.id.action_add_ski) {
            Intent intentOptions = new Intent(pagePrincipale.this, pageAjoutSkis.class);
            startActivityForResult(intentOptions,1);
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshListView() {
        ListView mListView;
        daoSkis daoSkis;
        daoSkisActions daoSkisActions;
        long iSaison = getSaison();
        daoSkis = new daoSkis(this);
        daoSkis.open();

        daoSkisActions = new daoSkisActions(this);
        daoSkisActions.open();


        List<metaSkis> skisList = daoSkis.getAll();

        ArrayList<metaRow4Skis_PagePrincipale> listNomSkis = new ArrayList<>();


        for (metaSkis skis : skisList) {
            listNomSkis.add(new metaRow4Skis_PagePrincipale(skis.getId(),skis.getNom(),
                    daoSkisActions.getNbSortie4Saison(iSaison,skis.getId()),
                    daoSkisActions.getSortieAffutage(iSaison,skis.getId()),
                    daoSkisActions.getSortieFartage(iSaison,skis.getId()),
                    daoSkisActions.getNbReparation(skis.getId()),
                    daoSkisActions.getNbReparation4Saison(iSaison,skis.getId())));
        }

        Collections.sort(listNomSkis, new Comparator<metaRow4Skis_PagePrincipale>() {
            @Override
            public int compare(metaRow4Skis_PagePrincipale t2, metaRow4Skis_PagePrincipale t1) {
                int nbs1 = Integer.parseInt(t1.getNbSortieSaison());
                int nbs2 = Integer.parseInt(t2.getNbSortieSaison());
                return nbs1 - nbs2;
            }
        });
        mListView = findViewById(R.id.lvSki_PagePrincipale);

        final adapterSkis adapter = new adapterSkis(pagePrincipale.this, listNomSkis);
        mListView.setAdapter(adapter);
        daoSkis.close();

    }

    private long getSaison() {
        long iSaison;
        daoOptions daoOptions = new daoOptions(this);
        daoOptions.open();
        String strMoisSaison = daoOptions.getValeur(dbEntretienSkis.vOPTIONS_ID_DATEDEBUTSAISON);
        if (strMoisSaison.equals("none")) {
            strMoisSaison = "09";
        }
        Integer intMoisSaison = Integer.parseInt(strMoisSaison)-1;
        Calendar cal = new GregorianCalendar();
        if (cal.get(Calendar.MONTH) < intMoisSaison) {
            iSaison = Long.parseLong((cal.get(Calendar.YEAR)-1) + strMoisSaison + "01000000");
        }
        else {
            iSaison = Long.parseLong(cal.get(Calendar.YEAR) + strMoisSaison + "01000000");
        }
        return iSaison;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) refreshListView();
            if (resultCode == 11 ) {
                refreshListView();
                Snackbar snackbar = Snackbar.make(findViewById(R.id.pagePrincipaleLayout),
                        data.getStringExtra("SKI_NOM") +
                                " a été supprimé. Un export des actions a été fait dans le dossier de l'application !",
                        Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            Log.i("PERM","OK");
            return true;
        } else {
            Log.i("PERM","MUP");
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }


}
