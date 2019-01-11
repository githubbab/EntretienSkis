package babouscorp.entretienskis;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class pageAjoutSkis extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_ajout_skis);


        Button btnValidation = findViewById(R.id.btnAjoutSki);
        btnValidation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText etNomSki = findViewById(R.id.txtnomskis);
                String nom =  etNomSki.getText().toString().trim();
                if (nom.equals("")) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.AjoutSkisLayout),"Veuillez mettre un nom à vos skis !", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }


                daoSkis skis = new daoSkis(getApplicationContext());
                skis.open();

                skis.insereSki(nom);

                skis.close();

                Intent returnIntent = new Intent();
                setResult(RESULT_OK,returnIntent);

                finish();
            }
        });

        Button btnAnnulation = findViewById(R.id.btnAnnulerAjoutSki);
        btnAnnulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED,returnIntent);
                finish();
            }
        });
    }
}
