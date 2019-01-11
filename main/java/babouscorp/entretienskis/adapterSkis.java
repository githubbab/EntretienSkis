package babouscorp.entretienskis;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class adapterSkis extends ArrayAdapter<metaRow4Skis_PagePrincipale> {
    private final Context context;
    private final ArrayList<metaRow4Skis_PagePrincipale> itemsArrayList;


    public adapterSkis(Context context, ArrayList<metaRow4Skis_PagePrincipale> itemsArrayList) {

        super(context, R.layout.row4skis_pageprincipale, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.row4skis_pageprincipale, parent, false);

        // 3. Get the two text view from the rowView
        TextView nom = rowView.findViewById(R.id.nom);
        TextView sorties = rowView.findViewById(R.id.sorties);
        TextView affutages = rowView.findViewById(R.id.affutage);
        TextView fartages = rowView.findViewById(R.id.fartage);
        TextView lblreparation = rowView.findViewById(R.id.lblreparation);
        TextView reparation = rowView.findViewById(R.id.reparation);
        TextView lblreparationfin = rowView.findViewById(R.id.lblreparationfin);

        // 4. Set the text for textView
        nom.setText(itemsArrayList.get(position).getNom());
        sorties.setText(itemsArrayList.get(position).getNbSortieSaison());
        affutages.setText(itemsArrayList.get(position).getNbSortieAffutage());
        if (Integer.parseInt(itemsArrayList.get(position).getNbSortieAffutage()) > 3) {
            affutages.setTextColor(Color.RED);
        }
        fartages.setText(itemsArrayList.get(position).getNbSortieFartage());
        if (Integer.parseInt(itemsArrayList.get(position).getNbSortieFartage()) > 10) {
            fartages.setTextColor(Color.RED);
        }
        String nbreparation = itemsArrayList.get(position).getNbReparation();
        if ( nbreparation.equals("0") ) {
            lblreparation.setText("");
            lblreparationfin.setText("");
        }
        else {
            reparation.setText(nbreparation + "(" + itemsArrayList.get(position).getNbReparationSaison() + ")");
        }

        // 5. retrn rowView
        return rowView;
    }

    @Nullable
    @Override
    public metaRow4Skis_PagePrincipale getItem(int position) {
        return super.getItem(position);
    }
}
