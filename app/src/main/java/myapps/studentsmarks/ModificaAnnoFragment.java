package myapps.studentsmarks;

/**
 * Created by Gio on 10.11.2014.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.TestCase;

import java.util.Calendar;

import static myapps.studentsmarks.Utility.customTitleDialog;


/**
 * Created by Giovanni far on 10.11.2014.
 */
public class ModificaAnnoFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ModificaAnnoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ModificaAnnoFragment newInstance(int sectionNumber) {
        ModificaAnnoFragment fragment = new ModificaAnnoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_modifica_anno, container, false);
        final StudentMarks activity = ((StudentMarks)getActivity());
        final Button btnSalva       = (Button)rootView.findViewById(R.id.salva);
        final Button btnAnno        = (Button)rootView.findViewById(R.id.anno);
        final Button btnAnnoDM      = (Button)rootView.findViewById(R.id.anno_dm);
        final TextView tvAnno       = (TextView)rootView.findViewById(R.id.tv_anno);
        final TextView tvAnnoDM     = (TextView)rootView.findViewById(R.id.tv_anno_dm);
        final TextView tvlAnnoDM    = (TextView)rootView.findViewById(R.id.tvl_anno_dm);
        final String msgAnnoDM      = (String)getResources().getText(R.string.layout_tvl_anno_dm);



        btnAnno.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnAnno.setBackgroundColor(Color.parseColor("#e6e6e6"));
                        return true;
                    case MotionEvent.ACTION_UP:
                        btnAnno.setBackgroundColor(Color.parseColor("#ffffff"));
                        //mostro il dialog che permette la selezione dell'anno a modificare
                        //acquizisione dell'anno piu piccolo e piu grande creati dall'utente
                        int[] anniCreatiutente = {2010, 2011, 2012, 2013};
                        int annoPiuPiccolo = anniCreatiutente[0];
                        int annoPiuGrande = anniCreatiutente[3];
                        Calendar dataAnnoPiuPiccolo = Calendar.getInstance();
                        dataAnnoPiuPiccolo.set(annoPiuPiccolo, Calendar.JANUARY, 1);
                        Calendar dataAnnoPiuGrande = Calendar.getInstance();
                        dataAnnoPiuGrande.set(annoPiuGrande, Calendar.JANUARY, 1);

                        //setup del dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        final DatePicker picker = new DatePicker(activity);
                        picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
                        picker.setCalendarViewShown(false);
                        picker.setMinDate(dataAnnoPiuPiccolo.getTimeInMillis());
                        picker.setMaxDate(dataAnnoPiuGrande.getTimeInMillis());
                        builder.setView(picker);
                        builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_ma) );

                        //listener bottone 'seleziona'
                        Dialog.OnClickListener seleziona = new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //prendo l'anno selezionato e lo mostro nella TextView relativa al bottone anno
                                tvAnno.setText(""+picker.getYear());
                                //abilito il bottone della modifica anno e le sue varie impostazioni
                                btnAnnoDM.setBackgroundColor(Color.parseColor("#ffffff"));
                                btnAnnoDM.setEnabled(true);
                                //permette di vedere nella label textView del bottone di modifica anno,
                                //la selezione dell'anno da modificare fatta dall'utente
                                tvlAnnoDM.setText(msgAnnoDM+" - "+picker.getYear());
                                tvlAnnoDM.setTextColor(Color.parseColor("#000000"));
                                tvAnnoDM.setTextColor(Color.parseColor("#000000"));
                            }
                        };
                        //set bottoni dialog
                        builder.setPositiveButton(R.string.dialog_btn_seleziona, seleziona);
                        builder.setNegativeButton(R.string.dialog_btn_annulla, null);

                        //mostro solo l'anno del datepicker
                        Utility.showJustYear(picker);

                        //visualizzo il dialog
                        Dialog dialog = builder.create();
                        dialog.show();
                        return true;
                }
                return false;
            }
        });
        btnAnnoDM.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnAnnoDM.setBackgroundColor(Color.parseColor("#e6e6e6"));
                        return true;

                    case MotionEvent.ACTION_UP:
                        btnAnnoDM.setBackgroundColor(Color.parseColor("#ffffff"));

                        //creo un nuovo Dialog per la modifica dell'anno selezionato
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        final DatePicker picker = new DatePicker(activity);
                        picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
                        picker.setCalendarViewShown(false);
                        builder.setView(picker);
                        builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_ma2) );

                        builder.setPositiveButton(R.string.dialog_btn_seleziona, new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //prendo il nuovo anno selezionato dall'utente e lo inserisco nella textView della modifica dell'anno
                                tvAnnoDM.setText("" + picker.getYear());

                                //abilito e preparo il setup del bottone 'salva'
                                btnSalva.setBackgroundColor(Color.parseColor("#87a914"));
                                btnSalva.setEnabled(true);
                            }
                        });
                        builder.setNegativeButton(R.string.dialog_btn_annulla, null);
                        Utility.showJustYear(picker);
                        Dialog dialog = builder.create();
                        dialog.show();
                        return true;
                }
                return false;
            }
        });
        //listener bottone 'salva'
        btnSalva.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnSalva.setBackgroundColor(Color.parseColor("#537719"));
                        return true;
                    case MotionEvent.ACTION_UP:
                        //salvo i dati relativi alla modifica dell'anno
                        Toast.makeText(activity, "hai modificato l'anno: "+tvAnno.getText()+"\nora è diventato: "+tvAnnoDM.getText(), Toast.LENGTH_LONG).show();

                        //e poi resetto il fragment per renderlo disponibile per la modifica di un nuovo anno
                        btnAnnoDM.setBackgroundColor(Color.parseColor("#dedede"));
                        tvlAnnoDM.setText(getResources().getText(R.string.layout_tvl_anno_dm));
                        tvAnnoDM.setText(getResources().getText(R.string.layout_msg_ma2));
                        tvlAnnoDM.setTextColor(Color.parseColor("#ffffff"));
                        tvAnnoDM.setTextColor(Color.parseColor("#ffffff"));
                        btnAnnoDM.setEnabled(false);
                        tvAnno.setText(getResources().getText(R.string.layout_msg_ma));
                        btnSalva.setBackgroundColor(Color.parseColor("#d2d2d2"));
                        btnSalva.setEnabled(false);
                        return true;
                }
                return false;
            }
        });
        return rootView;
    }
}
