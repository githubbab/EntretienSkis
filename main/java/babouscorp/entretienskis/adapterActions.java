package babouscorp.entretienskis;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class adapterActions extends ArrayAdapter<metaRow4Actions_PageAjoutActionsSkis> {

    private final Context context;
    private final ArrayList<metaRow4Actions_PageAjoutActionsSkis> itemsArrayList;

    public adapterActions(Context context, ArrayList<metaRow4Actions_PageAjoutActionsSkis> itemsArrayList) {
        super(context, R.layout.row4actions_pageajoutactionsskis, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.row4actions_pageajoutactionsskis, parent, false);

        // 3. Get the two text view from the rowView
        TextView date = rowView.findViewById(R.id.date);
        TextView type = rowView.findViewById(R.id.type);
        Button suppr = rowView.findViewById(R.id.btnsuppr);

        // 4. Set the text for textView
        date.setText(itemsArrayList.get(position).getDate());
        type.setText(itemsArrayList.get(position).getType());

        suppr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long iSaison = getSaison();
                daoSkisActions daoSkisActions = new daoSkisActions(context);
                daoSkisActions.open();
                String idSkis = daoSkisActions.getIdSkis4Id(itemsArrayList.get(position).getId());
                daoSkisActions.supprSkisAction(itemsArrayList.get(position).getId());
                itemsArrayList.remove(position);
                notifyDataSetChanged();
                TextView nbsortie = parent.getRootView().findViewById(R.id.txtsorties);
                TextView nbfartage = parent.getRootView().findViewById(R.id.txtnbfartage);
                TextView nbaffutage = parent.getRootView().findViewById(R.id.txtnbaffutage);

                nbsortie.setText(Integer.toString(daoSkisActions.getNbSortie4Saison(iSaison,idSkis)));
                nbaffutage.setText(Integer.toString(daoSkisActions.getNbAffutage4Saison(iSaison,idSkis)));
                nbfartage.setText(Integer.toString(daoSkisActions.getNbFartage4Saison(iSaison,idSkis)));

            }
        });
        // 5. retrn rowView
        return rowView;
    }

    private long getSaison() {
        long iSaison;
        daoOptions daoOptions = new daoOptions(context);
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


    @Nullable
    @Override
    public metaRow4Actions_PageAjoutActionsSkis getItem(int position) {
        return super.getItem(position);
    }


}
