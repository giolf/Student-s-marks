package myapps.studentgrades;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gio on 23.12.2014.
 */
public class AdapterListaVotiView extends ArrayAdapter<Voto> {
    Context context;
    int resource;
    List<Voto> listaVotiAnnuali = null;

    /* costruttore dell adapter che prende come parametri: un activity, l'id del layout e una lista */
    public AdapterListaVotiView(Context context, int resource, List<Voto> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listaVotiAnnuali = objects;
    }


    public void aggiornaListaVoti(ArrayList<Voto> listaVoti) {
        listaVotiAnnuali.clear();
        for (Voto voto : listaVoti)
            listaVotiAnnuali.add(voto);
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

        Voto voto = listaVotiAnnuali.get(position);
        NumberFormat formatter = new DecimalFormat("#0.00");

        TextView tvData      = (TextView)convertView.findViewById(R.id.tv_data_voto);
        TextView tvCorso     = (TextView)convertView.findViewById(R.id.tv_corso_voto);
        TextView tvPunteggio = (TextView)convertView.findViewById(R.id.tv_punteggio_voto);
        tvData.setText(voto.getData());
        tvCorso.setText(""+voto.getNomeCorso().toUpperCase());
        tvPunteggio.setText( ""+formatter.format( voto.getNota() ) );

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
