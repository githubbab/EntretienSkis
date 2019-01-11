package babouscorp.entretienskis;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class pageOptions extends AppCompatActivity {

    private Spinner spnDebutSaison;
    private daoOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_options);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        options = new daoOptions(this);
        options.open();

        String strMoisDebutSaison = options.getValeur(dbEntretienSkis.vOPTIONS_ID_DATEDEBUTSAISON);
        if (strMoisDebutSaison == "none") strMoisDebutSaison = "9";
        int intMoisDebutSaison = Integer.parseInt(strMoisDebutSaison);

        spnDebutSaison = findViewById(R.id.spnMoisDebutSaison);

        spnDebutSaison.setSelection(intMoisDebutSaison-1);

        spnDebutSaison.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                metaOptions metaOptions = new metaOptions();
                metaOptions.setId(dbEntretienSkis.vOPTIONS_ID_DATEDEBUTSAISON);
                metaOptions.setValeur(Integer.toString(i+1));
                if (metaOptions.getValeur().length() == 1) metaOptions.setValeur("0"+ metaOptions.getValeur());
                options.insereOption(metaOptions);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
