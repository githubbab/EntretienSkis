package babouscorp.entretienskis;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class pageAjoutActionsSkis extends AppCompatActivity {

    private String idSkis;
    private daoSkis daoSkis;
    private daoSkisActions daoSkisActions;
    private metaSkis metaSkis;
    private int resultCode = RESULT_OK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_ajout_actions_skis);

        Intent intent = this.getIntent();
        idSkis = intent.getStringExtra("SKI_ID");

        daoSkis = new daoSkis(this);
        daoSkis.open();
        metaSkis = daoSkis.getOne(idSkis);

        this.daoSkisActions = new daoSkisActions(this);
        this.daoSkisActions.open();

        final TextView nom = this.findViewById(R.id.txtnomskis);
        nom.setText(metaSkis.getNom());

        nom.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                LayoutInflater li = LayoutInflater.from(pageAjoutActionsSkis.this);
                View promptsView = li.inflate(R.layout.dialog_modif_nom_skis, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        pageAjoutActionsSkis.this);

                alertDialogBuilder.setView(promptsView);

                final EditText userInput = promptsView.findViewById(R.id.etNomSkis);

                alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            nom.setText(userInput.getText());
                                            daoSkis.setNomSkis(idSkis, userInput.getText().toString());
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                    return false;
            }
        });

        Button btnAjoutSortie = this.findViewById(R.id.btnsortie);
        btnAjoutSortie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Long date = Long.parseLong(sdf.format(new Date()));
                daoSkisActions.insereSkisAction(idSkis,date,dbEntretienSkis.vACTIONSSKIS_TYPE_SORTIE);
                refreshList();
            }
        });

        Button btnAjoutAffutage = this.findViewById(R.id.btnaffutage);
        btnAjoutAffutage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Long date = Long.parseLong(sdf.format(new Date()));
                daoSkisActions.insereSkisAction(idSkis,date,dbEntretienSkis.vACTIONSSKIS_TYPE_AFFUTAGE);
                refreshList();
            }
        });

        Button btnAjoutAffutagePierre = this.findViewById(R.id.btnaffutagepierre);
        btnAjoutAffutagePierre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Long date = Long.parseLong(sdf.format(new Date()));
                daoSkisActions.insereSkisAction(idSkis,date,dbEntretienSkis.vACTIONSSKIS_TYPE_PIERRE);
                refreshList();
            }
        });

        Button btnAjoutFartageGraph = this.findViewById(R.id.btnfartagegraph);
        btnAjoutFartageGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Long date = Long.parseLong(sdf.format(new Date()));
                daoSkisActions.insereSkisAction(idSkis,date,dbEntretienSkis.vACTIONSSKIS_TYPE_GRAPHITE);
                refreshList();
            }
        });

        Button btnAjoutFartage = this.findViewById(R.id.btnfartage);
        btnAjoutFartage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Long date = Long.parseLong(sdf.format(new Date()));
                daoSkisActions.insereSkisAction(idSkis,date,dbEntretienSkis.vACTIONSSKIS_TYPE_FARTAGE);
                refreshList();
            }
        });

        Button btnAjoutReparationMan = this.findViewById(R.id.btnreparationman);
        btnAjoutReparationMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Long date = Long.parseLong(sdf.format(new Date()));
                daoSkisActions.insereSkisAction(idSkis,date,dbEntretienSkis.vACTIONSSKIS_TYPE_REPARATION);
                refreshList();
            }
        });

        Button btnAjoutReparationMag = this.findViewById(R.id.btnreparationmag);
        btnAjoutReparationMag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Long date = Long.parseLong(sdf.format(new Date()));
                daoSkisActions.insereSkisAction(idSkis,date,dbEntretienSkis.vACTIONSSKIS_TYPE_MAGASIN);
                refreshList();
            }
        });

        Switch swActiveSuppr = findViewById(R.id.swdelete);
        swActiveSuppr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Button btnSuppr = findViewById(R.id.btndelete);
                btnSuppr.setEnabled(b);
            }
        });

        Button btnSupprSkis = this.findViewById(R.id.btndelete);
        btnSupprSkis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daoSkisActions.exportSkis(idSkis,metaSkis.getNom())) {
                    daoSkisActions.supprSkisActionbyIdSkis(idSkis);
                    daoSkis.supprSki(idSkis);
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.ajoutActionsSkisLayout),"Un export des actions a été fait !", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                resultCode = 11;
                finish();
            }
        });

        refreshList();
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

    public void refreshList () {
        ListView mListView;
        long iSaison = getSaison();


        TextView nbsortie = this.findViewById(R.id.txtsorties);
        TextView nbfartage = this.findViewById(R.id.txtnbfartage);
        TextView nbaffutage = this.findViewById(R.id.txtnbaffutage);
        TextView lblreparation = this.findViewById(R.id.lblreparation);
        TextView reparation = this.findViewById(R.id.reparation);
        TextView lblreparationfin = this.findViewById(R.id.lblreparationfin);

        nbsortie.setText(Integer.toString(daoSkisActions.getNbSortie4Saison(iSaison,idSkis)));
        nbaffutage.setText(Integer.toString(daoSkisActions.getNbAffutage4Saison(iSaison,idSkis)));
        nbfartage.setText(Integer.toString(daoSkisActions.getNbFartage4Saison(iSaison,idSkis)));

        int nbreparation = daoSkisActions.getNbReparation(idSkis);
        if ( nbreparation == 0 ) {
            lblreparation.setText("");
            lblreparationfin.setText("");
        }
        else {
            reparation.setText(Integer.toString(nbreparation) + "(" + Integer.toString(daoSkisActions.getNbReparation4Saison(iSaison,idSkis) )+ ")");
        }
        List<metaSkisActions> actionsList = daoSkisActions.getAll4Saison(iSaison,idSkis);

        ArrayList<metaRow4Actions_PageAjoutActionsSkis> listActionsSkis = new ArrayList<>();

        for (metaSkisActions action : actionsList) {
            listActionsSkis.add(new metaRow4Actions_PageAjoutActionsSkis(action.getId(),
                    action.getDate(),action.getType()));
        }

        mListView = findViewById(R.id.lvActions);

        final adapterActions adapter = new adapterActions(this,listActionsSkis);
        mListView.setAdapter(adapter);

    }

    @Override
    public void finish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("SKI_NOM", metaSkis.getNom());
        setResult(resultCode,returnIntent);
        super.finish();
    }
}

