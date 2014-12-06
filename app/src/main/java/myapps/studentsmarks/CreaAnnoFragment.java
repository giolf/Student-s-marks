package myapps.studentsmarks;

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
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

import static myapps.studentsmarks.GestioneAnniFragment.annoGiaEsistente;
import static myapps.studentsmarks.GestioneAnniFragment.getListaAnni;
import static myapps.studentsmarks.Utility.makeSchoolYearList;
import static myapps.studentsmarks.Utility.makeFrameLWithNumPicker;



/**
 * Created by Giovanni far on 10.11.2014.
 */
public class CreaAnnoFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public CreaAnnoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CreaAnnoFragment newInstance(int sectionNumber) {
        CreaAnnoFragment fragment = new CreaAnnoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView               = inflater.inflate(R.layout.fragment_crea_anno, container, false);
        final StudentMarks activity = ((StudentMarks)getActivity());
        final Button btnSalva       = (Button)rootView.findViewById(R.id.salva);
        final Button btnAnno        = (Button)rootView.findViewById(R.id.anno);
        final TextView tvAnno       = (TextView)rootView.findViewById(R.id.tv_anno);

        btnAnno.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnAnno.setBackgroundColor(Color.parseColor("#e6e6e6"));
                        return true;
                    case MotionEvent.ACTION_UP:
                        btnAnno.setBackgroundColor(Color.parseColor("#ffffff"));

                        //Calcolo l'anno corrente
                        Calendar calendar = Calendar.getInstance();
                        int annoCorrente  = calendar.get(Calendar.YEAR);

                        //creo una lista di anni, da: annocorrente-10 a: annocorrente
                        final String[] listaAnniScolastici = makeSchoolYearList(annoCorrente);

                        //appare il dialog della selezione dell'anno da creare
                        //setup del dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        final NumberPicker picker = new NumberPicker(activity);
                        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                        picker.setMinValue(0);
                        picker.setMaxValue(listaAnniScolastici.length-1);
                        picker.setValue(listaAnniScolastici.length-1);
                        picker.setWrapSelectorWheel(false);
                        picker.setDisplayedValues(listaAnniScolastici);
                        FrameLayout frameLayout = makeFrameLWithNumPicker(picker, activity);
                        builder.setView(frameLayout);

                        //titolo dialog
                        builder.setCustomTitle(Utility.customTitleDialog(activity, R.string.dialog_tit_ca));

                        //listeners bottoni 'annulla' e 'salva'
                        Dialog.OnClickListener seleziona = new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Verifico se l'utente ha almeno creato un anno
                                if (getListaAnni().size() > 0)
                                    //l'utente ha gia creato almeno un anno, verifico se l'anno che vuole creare esiste gia
                                    if ( annoGiaEsistente(listaAnniScolastici[picker.getValue()]) ) {
                                        //l'utente sta creando un anno gia esistente, lo avviso con un dialog ed interrompo la procedura
                                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                        builder.setCancelable(false);
                                        builder.setCustomTitle(Utility.customTitleDialog(activity, R.string.dialog_tit_avviso));
                                        builder.setPositiveButton(R.string.dialog_btn_ok, null);
                                        TextView messaggio = new TextView(activity);
                                        messaggio.setText(R.string.errore_msg3);
                                        messaggio.setPadding(20, 50, 20, 50);
                                        messaggio.setGravity(Gravity.CENTER);
                                        messaggio.setTextSize(18);
                                        builder.setView(messaggio);
                                        Dialog dialog = builder.create();
                                        dialog.show();
                                        //dopo che l'utente preme 'ok' interrompo la procedura
                                        return;
                                    }

                                //prendo i l'anno selezionato e lo mostro nel bottone dell'anno
                                tvAnno.setText( ""+listaAnniScolastici[picker.getValue()] );

                                //abilito il bottone salva
                                btnSalva.setBackgroundColor(Color.parseColor("#87a914"));
                                btnSalva.setEnabled(true);
                            }
                        };
                        //bottoni dialog
                        builder.setPositiveButton(R.string.dialog_btn_seleziona, seleziona);
                        builder.setNegativeButton(R.string.dialog_btn_annulla, null);

                        //visualizzo il dialog
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
                        //salvo i dati relativi all anno creato
                        getListaAnni().add( new Anno((String) tvAnno.getText()) );
                        Toast.makeText(activity, "hai creato l'anno scolastico: "+tvAnno.getText(), Toast.LENGTH_LONG).show();

                        //e poi resetto il fragment per renderlo disponibile per la creazione di un nuovo anno
                        tvAnno.setText(getResources().getText(R.string.layout_msg_ca));
                        btnSalva.setEnabled(false);
                        btnSalva.setBackgroundColor(Color.parseColor("#d2d2d2"));
                        return true;
                }
                return false;
            }
        });

        return rootView;
    }
}
