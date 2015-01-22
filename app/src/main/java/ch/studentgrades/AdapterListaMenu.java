package ch.studentgrades;

/**
 * Created by Gio on 20.01.2015.
 */
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gio on 01.12.2014.
 */
public class AdapterListaMenu extends ArrayAdapter<String> {
    Context context;
    int resource;
    int posizioneCorrente = 0;

    /*costruttore dell adapter che prende come parametri:
     *un activity, l'id del layout e una lista*/
    public AdapterListaMenu(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @Override
	/*questo metodo viene chiamato dall'adapter per buttare fuori le view di ogni
	 *singola riga della lista*/
    public View getView(int position, View convertView, ViewGroup parent) {
		/*per questioni di efficienza è meglio creare e assegnare una sola volta il layout*/
        //if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =  inflater.inflate(resource, null);
        //}

        String voceMenu = getItem(position);

        TextView tvVoceMenu = (TextView)convertView.findViewById(R.id.voceMenu);
        ImageView icona     = (ImageView)convertView.findViewById(R.id.icona);
        tvVoceMenu.setText(voceMenu);

        Typeface font = Typeface.createFromAsset(context.getAssets(),"fonts/GOTHIC.TTF");
        tvVoceMenu.setTypeface(font);

        switch (position) {
            case 0:
                icona.setImageResource(R.drawable.ra_icon);
                break;
            case 1:
                icona.setImageResource(R.drawable.ga_icon);
                break;
            case 2:
                icona.setImageResource(R.drawable.gc_icon);
                break;
            case 3:
                icona.setImageResource(R.drawable.gv_icon);
                break;
        }

        if (position == posizioneCorrente)
            convertView.setBackgroundColor(Color.parseColor("#f6f6f6"));

        return convertView;
    }

    public void setPosizioneCorrente(int posizione) {
        posizioneCorrente = posizione;
    }
}
