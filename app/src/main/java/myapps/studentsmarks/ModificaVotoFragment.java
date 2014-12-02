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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static myapps.studentsmarks.Utility.customTitleDialog;
import static myapps.studentsmarks.Utility.monthFromIntToString;

/**
 * Created by Gio on 01.12.2014.
 */
public class ModificaVotoFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ModificaVotoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ModificaVotoFragment newInstance(int sectionNumber) {
        ModificaVotoFragment fragment = new ModificaVotoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView               = inflater.inflate(R.layout.fragment_modifica_voto, container, false);
        final StudentMarks activity = ((StudentMarks)getActivity());
        final Button btnSalva       = (Button)rootView.findViewById(R.id.salva);
        final Button btnAnno        = (Button)rootView.findViewById(R.id.anno);
        final Button btnCorso       = (Button)rootView.findViewById(R.id.corso);
        final Button btnVoto        = (Button)rootView.findViewById(R.id.voto);
        final Button btnData        = (Button)rootView.findViewById(R.id.data);
        final Button btnPunteggio   = (Button)rootView.findViewById(R.id.punteggio);
        final TextView tvAnno       = (TextView)rootView.findViewById(R.id.tv_anno);
        final TextView tvCorso      = (TextView)rootView.findViewById(R.id.tv_corso);
        final TextView tvVoto       = (TextView)rootView.findViewById(R.id.tv_voto);
        final TextView stvNota      = (TextView)rootView.findViewById(R.id.stv_nota);
        final TextView tvData       = (TextView)rootView.findViewById(R.id.tv_data);
        final TextView tvPunteggio  = (TextView)rootView.findViewById(R.id.tv_punteggio);
        final TextView tvlCorso     = (TextView)rootView.findViewById(R.id.tvl_corso);
        final TextView tvlVoto      = (TextView)rootView.findViewById(R.id.tvl_voto);
        final TextView tvlData      = (TextView)rootView.findViewById(R.id.tvl_data);
        final TextView tvlPunteggio = (TextView)rootView.findViewById(R.id.tvl_punteggio);

        btnAnno.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnAnno.setBackgroundColor(Color.parseColor("#e6e6e6"));
                        return true;
                    case MotionEvent.ACTION_UP:
                        btnAnno.setBackgroundColor(Color.parseColor("#ffffff"));

                        //Mostro il Dialog che permette la scelta dell'anno del corso
                        //setup del dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        final DatePicker picker = new DatePicker(activity);
                        picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
                        picker.setCalendarViewShown(false);
                        builder.setView(picker);

                        //titolo dialog
                        builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_cc) );

                        //listener bottone 'seleziona'
                        Dialog.OnClickListener seleziona = new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                 /*Se l'utente cambia anno DOPO aver gia scelto un corso, deve rifare tutta la procedura
                                  *questo avviene perche non è detto che il corso selezionato nell'anno precedente ci sia
                                  *anche nell'anno appena selezionato
                                  */
                                if( !tvCorso.getText().equals(getResources().getString(R.string.layout_msg_mv)) ) {
                                    //resetto il fragment per renderlo disponibile per la modifica di un nuovo voto
                                    tvAnno.setText(getResources().getText(R.string.layout_msg_cc));
                                    tvCorso.setText(getResources().getString(R.string.layout_msg_mv));
                                    tvCorso.setTextColor(Color.parseColor("#ffffff"));
                                    tvlCorso.setTextColor(Color.parseColor("#ffffff"));
                                    btnCorso.setBackgroundColor(Color.parseColor("#dedede"));
                                    btnCorso.setEnabled(false);
                                    tvVoto.setText(getResources().getString(R.string.layout_msg_mv2));
                                    tvVoto.setTextColor(Color.parseColor("#ffffff"));
                                    stvNota.setTextColor(Color.parseColor("#ffffff"));
                                    stvNota.setVisibility(View.INVISIBLE);
                                    tvlVoto.setTextColor(Color.parseColor("#ffffff"));
                                    btnVoto.setBackgroundColor(Color.parseColor("#dedede"));
                                    btnVoto.setEnabled(false);
                                    tvData.setText(getResources().getString(R.string.layout_msg_mv3));
                                    tvData.setTextColor(Color.parseColor("#ffffff"));
                                    tvlData.setTextColor(Color.parseColor("#ffffff"));
                                    btnData.setBackgroundColor(Color.parseColor("#dedede"));
                                    btnData.setEnabled(false);
                                    tvPunteggio.setText(getResources().getString(R.string.layout_msg_mv4));
                                    tvPunteggio.setTextColor(Color.parseColor("#ffffff"));
                                    tvlPunteggio.setTextColor(Color.parseColor("#ffffff"));
                                    btnPunteggio.setBackgroundColor(Color.parseColor("#dedede"));
                                    btnPunteggio.setEnabled(false);
                                    btnSalva.setBackgroundColor(Color.parseColor("#d2d2d2"));
                                    btnSalva.setEnabled(false);
                                    //mostro un messaggio che avverte l'utente
                                    Toast toast = Toast.makeText(activity, getResources().getString(R.string.errore_msg), Toast.LENGTH_LONG);
                                    TextView tv = (TextView)toast.getView().findViewById(android.R.id.message);
                                    tv.setGravity(Gravity.CENTER);
                                    toast.show();
                                    //esco dal metodo interrompendo il flusso del listener
                                    return;
                                }
                                //mostro l'anno selezionato dall'utente nella TextView dell'anno
                                tvAnno.setText(""+picker.getYear());

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
        btnCorso.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnCorso.setBackgroundColor(Color.parseColor("#e6e6e6"));
                        return true;
                    case MotionEvent.ACTION_UP:
                        btnCorso.setBackgroundColor(Color.parseColor("#ffffff"));

                        //qui prendo i corsi dell'anno selezionato
                        final String[] lcAnnoSelezionato = {"Italiano", "Matematica", "Francese", "Fisica", "Tedesco", "Storia", "Scienze", "Latino", "Storia", "Scienze", "Latino"};

                        //Mostro un dialog che permette la scelta del corso da selezionare nell'anno selezionato
                        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        builder.setCustomTitle(customTitleDialog(activity, R.string.dialog_tit_cv3));
                        builder.setItems(lcAnnoSelezionato, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, final int index) {
                                /*Se l'utente cambia corso DOPO aver gia scelto un voto, deve rifare tutta la procedura
                                  *questo avviene perche non è detto che il voto selezionato nel corso precedente ci sia
                                  *anche nel corso appena selezionato
                                  */
                                if( !tvVoto.getText().equals(getResources().getString(R.string.layout_msg_mv2)) ) {
                                    //resetto il fragment per renderlo disponibile per la modifica di un nuovo voto
                                    tvAnno.setText(getResources().getText(R.string.layout_msg_cc));
                                    tvCorso.setText(getResources().getString(R.string.layout_msg_mv));
                                    tvCorso.setTextColor(Color.parseColor("#ffffff"));
                                    tvlCorso.setTextColor(Color.parseColor("#ffffff"));
                                    btnCorso.setBackgroundColor(Color.parseColor("#dedede"));
                                    btnCorso.setEnabled(false);
                                    tvVoto.setText(getResources().getString(R.string.layout_msg_mv2));
                                    tvVoto.setTextColor(Color.parseColor("#ffffff"));
                                    stvNota.setTextColor(Color.parseColor("#ffffff"));
                                    stvNota.setVisibility(View.INVISIBLE);
                                    tvlVoto.setTextColor(Color.parseColor("#ffffff"));
                                    btnVoto.setBackgroundColor(Color.parseColor("#dedede"));
                                    btnVoto.setEnabled(false);
                                    tvData.setText(getResources().getString(R.string.layout_msg_mv3));
                                    tvData.setTextColor(Color.parseColor("#ffffff"));
                                    tvlData.setTextColor(Color.parseColor("#ffffff"));
                                    btnData.setBackgroundColor(Color.parseColor("#dedede"));
                                    btnData.setEnabled(false);
                                    tvPunteggio.setText(getResources().getString(R.string.layout_msg_mv4));
                                    tvPunteggio.setTextColor(Color.parseColor("#ffffff"));
                                    tvlPunteggio.setTextColor(Color.parseColor("#ffffff"));
                                    btnPunteggio.setBackgroundColor(Color.parseColor("#dedede"));
                                    btnPunteggio.setEnabled(false);
                                    btnSalva.setBackgroundColor(Color.parseColor("#d2d2d2"));
                                    btnSalva.setEnabled(false);
                                    //mostro un messaggio che avverte l'utente
                                    Toast toast = Toast.makeText(activity, getResources().getString(R.string.errore_msg2), Toast.LENGTH_LONG);
                                    TextView tv = (TextView)toast.getView().findViewById(android.R.id.message);
                                    tv.setGravity(Gravity.CENTER);
                                    toast.show();
                                    //esco dal metodo interrompendo il flusso del listener
                                    return;
                                }
                                //prendo il corso selezionato in base alla scelta dell'utente e lo metto nella TextView del corso
                                tvCorso.setText(""+lcAnnoSelezionato[index]);

                                //abilito il bottone 'voto'
                                btnVoto.setBackgroundColor(Color.parseColor("#ffffff"));
                                tvVoto.setTextColor(Color.parseColor("#000000"));
                                tvlVoto.setTextColor(Color.parseColor("#000000"));
                                btnVoto.setEnabled(true);
                            }
                        });

                        builder.setNegativeButton(R.string.dialog_btn_annulla, null);
                        Dialog dialog = builder.create();
                        dialog.show();
                        //imposto le dimensioni della finestra dell'alertDialog
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.height = 1000;
                        dialog.getWindow().setAttributes(lp);
                        return true;
                }
                return false;
            }
        });
        btnVoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnVoto.setBackgroundColor(Color.parseColor("#e6e6e6"));
                        return true;
                    case MotionEvent.ACTION_UP:
                        btnVoto.setBackgroundColor(Color.parseColor("#ffffff"));
                        //prendo i voti del corso selezionato
                        final ArrayList<Voto> listaVoti = new ArrayList<Voto>();
                        listaVoti.add(new Voto(1, 1, 2014, 4.5));
                        listaVoti.add(new Voto(2, 2, 2014, 5.5));

                        //creo l'adapter passandogli la lista dei voti del corso selezionato
                        AdapterModificaCorsoSV adapter = new AdapterModificaCorsoSV(activity, R.layout.row_voto, listaVoti);

                        //creo un nuovo dialog che si occuperà di mostrare e salvare i voti del corso selezionato
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //prendo il voto selezionato e lo mostro nella text view 'voto'
                                Voto votoSelezionato = listaVoti.get(i);
                                int giorno  = votoSelezionato.getGiornoData();
                                int mese    = votoSelezionato.getMeseData();
                                int anno    = votoSelezionato.getAnnoData();
                                double nota = votoSelezionato.getNota();

                                //mostro la sub-text view 'nota' e gli passo la nota del voto selezionato
                                stvNota.setVisibility(View.VISIBLE);
                                stvNota.setText(""+rootView.getResources().getString(R.string.layout_msg_mv5)+" "+nota);
                                stvNota.setTextColor(Color.parseColor("#000000"));
                                tvVoto.setText(rootView.getResources().getString(R.string.layout_msg_mv6)+" "+giorno+" "+monthFromIntToString(activity, mese)+" "+anno);

                                //abilito i bottoni 'modifica data' e 'modifica punteggio'
                                btnData.setBackgroundColor(Color.parseColor("#ffffff"));
                                tvData.setTextColor(Color.parseColor("#000000"));
                                tvlData.setTextColor(Color.parseColor("#000000"));
                                tvData.setText(""+giorno+" "+monthFromIntToString(activity, mese)+" "+anno);
                                btnData.setEnabled(true);
                                btnPunteggio.setBackgroundColor(Color.parseColor("#ffffff"));
                                tvPunteggio.setTextColor(Color.parseColor("#000000"));
                                tvPunteggio.setText(""+nota);
                                tvlPunteggio.setTextColor(Color.parseColor("#000000"));
                                btnPunteggio.setEnabled(true);
                            }
                        });

                        //titolo del dialog
                        builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_mv) );

                        builder.setNegativeButton(R.string.dialog_btn_annulla, null);

                        Dialog dialog = builder.create();
                        dialog.show();
                        //imposto le dimensioni della finestra dell'alertDialog
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.height = 1000;
                        dialog.getWindow().setAttributes(lp);
                        return true;
                }
                return false;
            }
        });

        return rootView;
    }
}
