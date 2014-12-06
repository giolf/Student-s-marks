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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static myapps.studentsmarks.GestioneAnniFragment.CreaArrayNomiAnni;
import static myapps.studentsmarks.GestioneAnniFragment.getAnno;
import static myapps.studentsmarks.GestioneAnniFragment.getListaAnni;
import static myapps.studentsmarks.Utility.customTitleDialog;
import static myapps.studentsmarks.Utility.makeFrameLWithNumPicker;

/**
 * Created by Gio on 15.11.2014.
 */
public class CreaCorsoFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public CreaCorsoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CreaCorsoFragment newInstance(int sectionNumber) {
        CreaCorsoFragment fragment = new CreaCorsoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_crea_corso, container, false);
        final StudentMarks activity = ((StudentMarks)getActivity());
        final Button btnSalva       = (Button)rootView.findViewById(R.id.salva);
        final Button btnCorso       = (Button)rootView.findViewById(R.id.corso);
        final Button btnAnno        = (Button)rootView.findViewById(R.id.anno);
        final TextView tvAnno       = (TextView)rootView.findViewById(R.id.tv_anno);
        final TextView tvCorso      = (TextView)rootView.findViewById(R.id.tv_corso);
        final TextView tvlCorso     = (TextView)rootView.findViewById(R.id.tvl_corso);

        btnAnno.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnAnno.setBackgroundColor(Color.parseColor("#e6e6e6"));
                        return true;
                    case MotionEvent.ACTION_UP:
                        btnAnno.setBackgroundColor(Color.parseColor("#ffffff"));

                        //Controllo se ci sono anni creati
                        if (getListaAnni().size() == 0) {
                            //se non ci sono anni creati avviso l'utente e poi interrompo la procedura
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setCancelable(false);
                            builder.setCustomTitle(Utility.customTitleDialog(activity, R.string.dialog_tit_avviso));
                            builder.setPositiveButton(R.string.dialog_btn_ok, null);
                            TextView messaggio = new TextView(activity);
                            messaggio.setText(R.string.errore_msg6);
                            messaggio.setPadding(0, 50, 0, 50);
                            messaggio.setGravity(Gravity.CENTER);
                            messaggio.setTextSize(18);
                            builder.setView(messaggio);
                            Dialog dialog = builder.create();
                            dialog.show();
                            //dopo che l'utente preme 'ok' interrompo la procedura
                            return false;
                        }

                        //recupero la lista degli anni creati dall'utente
                        final String[] anniCreatiutente = CreaArrayNomiAnni();

                        //Mostro il Dialog che permette la scelta dell'anno del corso da creare
                        //setup del dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        final NumberPicker picker = new NumberPicker(activity);
                        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                        picker.setMinValue(0);
                        picker.setMaxValue(anniCreatiutente.length-1);
                        picker.setValue(anniCreatiutente.length-1);
                        picker.setWrapSelectorWheel(false);
                        picker.setDisplayedValues(anniCreatiutente);
                        FrameLayout frameLayout = makeFrameLWithNumPicker(picker, activity);
                        builder.setView(frameLayout);

                        //titolo dialog
                        builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_cc) );

                        //listeners bottone seleziona'
                        Dialog.OnClickListener seleziona = new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //salvo l'anno selezionato dall'utente e lo mostro nella TextView dell'anno
                                tvAnno.setText( ""+anniCreatiutente[picker.getValue()] );

                                //abilito il bottone corso
                                btnCorso.setBackgroundColor(Color.parseColor("#ffffff"));
                                tvlCorso.setTextColor(Color.parseColor("#000000"));
                                tvCorso.setTextColor(Color.parseColor("#000000"));
                                btnCorso.setEnabled(true);
                            }
                        };

                        //set bottoni dialog
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
        btnCorso.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnCorso.setBackgroundColor(Color.parseColor("#e6e6e6"));
                        return true;
                    case MotionEvent.ACTION_UP:
                        btnCorso.setBackgroundColor(Color.parseColor("#ffffff"));
                        //creo un nuovo Dialog per la creazione del corso nell'anno selezionato
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        final DatePicker picker = new DatePicker(activity);
                        picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
                        final EditText inputCreaCorso = new EditText(activity);
                        builder.setView(inputCreaCorso);

                        //titolo del dialog
                        builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_cc2) );

                        builder.setPositiveButton(R.string.dialog_btn_inserisci, new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                 /*se l'utente non inserisce nulla (lascia il campo vuoto premendo comunque 'inserisci')
                                 *interrompo di netto il metodo (in pratica non succede nulla) obbligandolo a inserire qualcosa
                                 *per poter salvare la creazione del corso
                                 */
                                String pattern = "^ *$"; //stringhe vuote con o senza spazi
                                if( inputCreaCorso.getText().toString().matches(pattern) )
                                    return;

                                //controllo se l'anno selezionato ha almeno 1 corso
                                Anno annoSelezionato = getAnno( (String)tvAnno.getText() );
                                if ( annoSelezionato.getListaCorsi().size() > 0 )
                                    //in questo anno ci sono corsi, controllo se il corso immesso è gia presente nell'anno selezionato
                                    if (annoSelezionato.corsoGiaEsistente( inputCreaCorso.getText().toString() ) ) {
                                        //l'utente sta inserendo un corso gia presente nell'anno selezionato
                                        //quindi lo avverto e interrompo la procedura di creazione
                                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                        builder.setCancelable(false);
                                        builder.setCustomTitle(Utility.customTitleDialog(activity, R.string.dialog_tit_avviso));
                                        builder.setPositiveButton(R.string.dialog_btn_ok, null);
                                        TextView messaggio = new TextView(activity);
                                        messaggio.setText(R.string.errore_msg7);
                                        messaggio.setPadding(0, 50, 0, 50);
                                        messaggio.setGravity(Gravity.CENTER);
                                        messaggio.setTextSize(18);
                                        builder.setView(messaggio);
                                        Dialog dialog = builder.create();
                                        dialog.show();
                                        //dopo che l'utente preme 'ok' interrompo la procedura
                                        return;
                                    }

                                //se invece l'utente digita qualcosa di valido e che non è gia esistente inserisco il corso immesso nella TextView del corso
                                tvCorso.setText(""+inputCreaCorso.getText());

                                //inserito l'anno e il corso abilito il bottone salva
                                btnSalva.setBackgroundColor(Color.parseColor("#87a914"));
                                btnSalva.setEnabled(true);
                            }
                        });
                        builder.setNegativeButton(R.string.dialog_btn_annulla, null);
                        Dialog dialog = builder.create();
                        dialog.show();
                        return true;
                }
                return false;
            }
        });
        btnSalva.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnSalva.setBackgroundColor(Color.parseColor("#537719"));
                        return true;
                    case MotionEvent.ACTION_UP:
                        btnSalva.setBackgroundColor(Color.parseColor("#d2d2d2"));

                        //salvo il corso nell'anno selezionato
                        Anno annoSelezionato = getAnno( (String)tvAnno.getText() );
                        annoSelezionato.getListaCorsi().add( new Corso((String)tvCorso.getText()) );

                        Toast.makeText(activity, "Hai creato il corso: "+tvCorso.getText()+" nell'anno: "+tvAnno.getText(), Toast.LENGTH_LONG).show();

                        //e poi resetto il fragment per renderlo disponibile per la creazione di un nuovo corso
                        btnCorso.setEnabled(false);
                        btnSalva.setEnabled(false);
                        btnCorso.setBackgroundColor(Color.parseColor("#dedede"));
                        btnSalva.setBackgroundColor(Color.parseColor("#d2d2d2"));
                        tvlCorso.setTextColor(Color.parseColor("#ffffff"));
                        tvCorso.setTextColor(Color.parseColor("#ffffff"));
                        tvAnno.setText(getResources().getText(R.string.layout_msg_cc));
                        tvCorso.setText(getResources().getText(R.string.layout_msg_cc2));
                        return true;
                }
                return false;
            }
        });

        return rootView;
    }
}

