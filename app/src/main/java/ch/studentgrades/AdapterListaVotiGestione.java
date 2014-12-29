package ch.studentgrades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gio on 01.12.2014.
 */
public class AdapterListaVotiGestione extends ArrayAdapter<Voto> {
    Context context;
    int resource;
    /*costruttore dell adapter che prende come parametri:
     *un activity, l'id del layout e una lista*/
    public AdapterListaVotiGestione(Context context, int resource, List<Voto> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @Override
	/*questo metodo viene chiamato dall'adapter per buttare fuori le view di ogni
	 *singola riga della lista*/
    public View getView(int position, View convertView, ViewGroup parent) {
		/*per questioni di efficienza è meglio creare e assegnare una sola volta il layout*/
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =  inflater.inflate(resource, null);
        }

        Voto voto = getItem(position);

        TextView tvData = (TextView)convertView.findViewById(R.id.tv_data);
        TextView tvNota = (TextView)convertView.findViewById(R.id.tv_nota);
        tvData.setText(voto.getData());
        tvNota.setText(""+voto.getNota());

        return convertView;
    }
}
