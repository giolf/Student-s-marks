package ch.studentgrades;

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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import static ch.studentgrades.DataSource.CreaArrayNomiAnni;
import static ch.studentgrades.DataSource.getAnno;
import static ch.studentgrades.DataSource.getListaAnni;
import static ch.studentgrades.Utility.customTitleDialog;
import static ch.studentgrades.Utility.makeFrameLWithNumPicker;

/**
 * Created by Gio on 16.11.2014.
 */
public class ModificaCorso extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ModificaCorso() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ModificaCorso newInstance(int sectionNumber) {
        ModificaCorso fragment = new ModificaCorso();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_modifica_corso, container, false);
        final StudentGrades activity = ((StudentGrades)getActivity());
        final Button btnSalva       = (Button)rootView.findViewById(R.id.salva);
        final Button btnAnno        = (Button)rootView.findViewById(R.id.anno);
        final Button btnCorso       = (Button)rootView.findViewById(R.id.corso);
        final Button btnCorsoDM     = (Button)rootView.findViewById(R.id.corso_dm);
        final TextView tvAnno       = (TextView)rootView.findViewById(R.id.tv_anno);
        final TextView tvCorso      = (TextView)rootView.findViewById(R.id.tv_corso);
        final TextView tvCorsoDM    = (TextView)rootView.findViewById(R.id.tv_corso_dm);
        final TextView tvlCorso     = (TextView)rootView.findViewById(R.id.tvl_corso);
        final TextView tvlCorsoDM   = (TextView)rootView.findViewById(R.id.tvl_corso_dm);

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
                            messaggio.setText(R.string.errore_msg8);
                            messaggio.setPadding(20, 50, 20, 50);
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

                        //Mostro il Dialog che permette la scelta dell'anno del corso da modificare
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

                        //listener bottone 'seleziona'
                        Dialog.OnClickListener seleziona = new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                 /*Se l'utente cambia anno DOPO aver gia scelto un corso, si resettano tutti i bottoni sotto l'anno
                                  *questo avviene perche non è detto che il corso selezionato nell'anno precedente ci sia
                                  *anche nell'anno appena selezionato
                                  */
                                if( !tvCorso.getText().equals(getResources().getString(R.string.layout_msg_mc)) ) {
                                    tvCorso.setText(getResources().getString(R.string.layout_msg_mc));
                                    tvCorsoDM.setText(getResources().getString(R.string.layout_msg_mc2));
                                    tvCorsoDM.setTextColor(Color.parseColor("#ffffff"));
                                    tvlCorsoDM.setTextColor(Color.parseColor("#ffffff"));
                                    tvlCorsoDM.setText(getResources().getString(R.string.layout_tvl_corso_dm));
                                    btnCorsoDM.setBackgroundColor(Color.parseColor("#dedede"));
                                    btnCorsoDM.setEnabled(false);
                                    btnSalva.setBackgroundColor(Color.parseColor("#d2d2d2"));
                                    btnSalva.setEnabled(false);
                                }

                                //mostro l'anno selezionato dall'utente nella TextView dell'anno
                                tvAnno.setText( ""+anniCreatiutente[picker.getValue()] );

                                //abilito il bottone corso
                                btnCorso.setBackgroundColor(Color.parseColor("#ffffff"));
                                tvCorso.setTextColor(Color.parseColor("#000000"));
                                tvlCorso.setTextColor(Color.parseColor("#000000"));
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

                        //controllo se l'anno selezionato ha almeno 1 corso
                        final Anno annoSelezionato = getAnno( (String)tvAnno.getText() );
                        if ( annoSelezionato.getListaCorsi().size() == 0 ) {
                            //mostro un messaggio che avvisa l'utente che nell'anno selezionato non ci sono corsi e poi interrompo la procedura
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setCancelable(false);
                            builder.setCustomTitle(Utility.customTitleDialog(activity, R.string.dialog_tit_avviso));
                            builder.setPositiveButton(R.string.dialog_btn_ok, null);
                            TextView messaggio = new TextView(activity);
                            messaggio.setText(R.string.errore_msg9);
                            messaggio.setPadding(20, 50, 20, 50);
                            messaggio.setGravity(Gravity.CENTER);
                            messaggio.setTextSize(18);
                            builder.setView(messaggio);
                            Dialog dialog = builder.create();
                            dialog.show();
                            //dopo che l'utente preme 'ok' interrompo la procedura
                            return false;
                        }

                        //l'anno selezionato contiene corsi, li recupero
                        final String[] lcAnnoSelezionato = annoSelezionato.creaArrayNomiCorsi();

                        //Mostro un dialog che permette la scelta del corso da modificare nell'anno selezionato
                        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        final NumberPicker picker = new NumberPicker(activity);
                        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                        picker.setMinValue(0);
                        picker.setMaxValue(lcAnnoSelezionato.length-1);
                        picker.setValue(lcAnnoSelezionato.length-1);
                        picker.setWrapSelectorWheel(false);
                        picker.setDisplayedValues(lcAnnoSelezionato);
                        FrameLayout frameLayout = makeFrameLWithNumPicker(picker, activity);
                        builder.setView(frameLayout);

                        //tiolo dialog
                        builder.setCustomTitle(customTitleDialog(activity, R.string.dialog_tit_mc));

                        //listeners dialog
                        builder.setPositiveButton(R.string.dialog_btn_seleziona, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //prendo il corso da modificare in base alla scelta dell'utente e lo metto nella TextView del corso
                                tvCorso.setText(""+lcAnnoSelezionato[picker.getValue()]);

                                //abilito il bottone 'modifica corso'
                                btnCorsoDM.setBackgroundColor(Color.parseColor("#ffffff"));
                                tvlCorsoDM.setText( getResources().getString(R.string.layout_tvl_corso_dm)+" - "+(tvCorso.getText()).toString().toUpperCase() );
                                tvCorsoDM.setTextColor(Color.parseColor("#000000"));
                                tvlCorsoDM.setTextColor(Color.parseColor("#000000"));
                                btnCorsoDM.setEnabled(true);
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
        btnCorsoDM.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnCorsoDM.setBackgroundColor(Color.parseColor("#e6e6e6"));
                        return true;
                    case MotionEvent.ACTION_UP:
                        btnCorsoDM.setBackgroundColor(Color.parseColor("#ffffff"));
                        //Apro un dialog che permette la modifica del corso selezionato
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        final EditText inputModificaCorso = new EditText(activity);
                        builder.setView(inputModificaCorso);

                        //titolo del Dialog
                        builder.setCustomTitle(customTitleDialog(activity, R.string.dialog_tit_mc2));
                        builder.setPositiveButton(R.string.dialog_btn_modifica, new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                /*se l'utente non inserisce nulla (lascia il campo vuoto premendo comunque 'modifica')
                                 *interrompo di netto il metodo (in pratica non succede nulla) obbligandolo a inserire qualcosa
                                 *per poter salvare la modifica del corso
                                 */
                                String pattern = "^ *$"; //stringhe vuote con o senza spazi
                                if( inputModificaCorso.getText().toString().matches(pattern) )
                                    return;
                                //Controllo se il corso immesso dall'utente è gia presente nell'anno selezionato
                                Anno annoSelezionato = getAnno( (String)tvAnno.getText() );
                                if ( annoSelezionato.corsoGiaEsistente(inputModificaCorso.getText().toString()) ) {
                                    //se ce gia un corso nell'anno selezionato con lo stesso nome avverto l'utente e interrompo la procedura
                                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                    builder.setCancelable(false);
                                    builder.setCustomTitle(Utility.customTitleDialog(activity, R.string.dialog_tit_avviso));
                                    builder.setPositiveButton(R.string.dialog_btn_ok, null);
                                    TextView messaggio = new TextView(activity);
                                    messaggio.setText(R.string.errore_msg7);
                                    messaggio.setPadding(20, 50, 20, 50);
                                    messaggio.setGravity(Gravity.CENTER);
                                    messaggio.setTextSize(18);
                                    builder.setView(messaggio);
                                    Dialog dialog = builder.create();
                                    dialog.show();
                                    //dopo che l'utente preme 'ok' interrompo la procedura
                                    return;
                                }
                                //Se invece l'utente digita qualcosa inserisco il nuovo nome del corso nella TextView del corso da modificare
                                tvCorsoDM.setText("" + inputModificaCorso.getText());

                                //abilito il bottone salva
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
                        //Salvo la modifica del corso
                        Anno annoSelezionato   = getAnno((String)tvAnno.getText());
                        Corso corsoSelezionato = annoSelezionato.getCorso( (String)tvCorso.getText() );
                        corsoSelezionato.modificaNomeCorso( tvCorsoDM.getText().toString() );

                        //inizio messaggio output - modifica corso
                        Toast toast = Toast.makeText(activity, getResources().getText(R.string.output_msg_mc), Toast.LENGTH_LONG);
                        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                        textView.setGravity(Gravity.CENTER);
                        toast.show();
                        //fine messaggio output - modifica corso

                        //resetto il fragment per renderlo disponibile per la modifica di un nuovo corso
                        tvAnno.setText(getResources().getText(R.string.layout_msg_cc));
                        tvCorso.setText(getResources().getString(R.string.layout_msg_mc));
                        tvCorso.setTextColor(Color.parseColor("#ffffff"));
                        tvlCorso.setTextColor(Color.parseColor("#ffffff"));
                        btnCorso.setBackgroundColor(Color.parseColor("#dedede"));
                        btnCorso.setEnabled(false);
                        tvCorsoDM.setText(getResources().getString(R.string.layout_msg_mc2));
                        tvCorsoDM.setTextColor(Color.parseColor("#ffffff"));
                        tvlCorsoDM.setTextColor(Color.parseColor("#ffffff"));
                        tvlCorsoDM.setText(getResources().getString(R.string.layout_tvl_corso_dm));
                        btnCorsoDM.setBackgroundColor(Color.parseColor("#dedede"));
                        btnCorsoDM.setEnabled(false);
                        btnSalva.setEnabled(false);
                        return true;
                }
                return false;
            }
        });
        return rootView;
    }
}
