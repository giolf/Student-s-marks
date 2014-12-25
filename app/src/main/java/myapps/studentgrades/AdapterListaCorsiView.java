package myapps.studentgrades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gio on 21.12.2014.
 */
public class AdapterListaCorsiView extends ArrayAdapter<Corso> {
    Context context;
    int resource;
    List<Corso> listaCorsiAnnuali = null;

    /* costruttore dell adapter che prende come parametri: un activity, l'id del layout e una lista */
    public AdapterListaCorsiView(Context context, int resource, List<Corso> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listaCorsiAnnuali = objects;
    }


    public void aggiornaListaCorsi(ArrayList<Corso> listaCorsi) {
        listaCorsiAnnuali.clear();
        for (Corso corso : listaCorsi)
            listaCorsiAnnuali.add(corso);
        notifyDataSetChanged();
    }

    /* questo metodo viene chiamato dall'adapter per buttare fuori le view di ogni singola riga della lista */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
		/*per questioni di efficienza è meglio creare e assegnare una sola volta il layout*/
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =  inflater.inflate(resource, null);
        }

        Corso corso = listaCorsiAnnuali.get(position);

        TextView tvCorso = (TextView)convertView.findViewById(R.id.tv_corso);
        TextView tvMedia = (TextView)convertView.findViewById(R.id.tv_media);
        tvCorso.setText(corso.getNomeCorso());
        tvMedia.setText(""+corso.getMediaAttuale());

        return convertView;
    }
}
