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

import java.util.Calendar;

import static myapps.studentsmarks.Utility.customTitleDialog;
import static myapps.studentsmarks.Utility.monthFromIntToString;

/**
 * Created by Gio on 19.11.2014.
 */
public class CreaVotoFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CreaVotoFragment newInstance(int sectionNumber) {
        CreaVotoFragment fragment = new CreaVotoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public CreaVotoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_crea_voto, container, false);
        final StudentMarks activity = ((StudentMarks)getActivity());
        final Button btnSalva       = (Button)rootView.findViewById(R.id.salva);
        final Button btnAnno        = (Button)rootView.findViewById(R.id.anno);
        final Button btnCorso       = (Button)rootView.findViewById(R.id.corso);
        final Button btnData        = (Button)rootView.findViewById(R.id.data);
        final Button btnVoto        = (Button)rootView.findViewById(R.id.voto);
        final TextView tvAnno       = (TextView)rootView.findViewById(R.id.tv_anno);
        final TextView tvCorso      = (TextView)rootView.findViewById(R.id.tv_corso);
        final TextView tvData       = (TextView)rootView.findViewById(R.id.tv_data);
        final TextView tvVoto       = (TextView)rootView.findViewById(R.id.tv_voto);
        final TextView tvlCorso     = (TextView)rootView.findViewById(R.id.tvl_corso);
        final TextView tvlData      = (TextView)rootView.findViewById(R.id.tvl_data);
        final TextView tvlVoto      = (TextView)rootView.findViewById(R.id.tvl_voto);

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
                                if( !tvCorso.getText().equals(getResources().getString(R.string.layout_msg_cv)) ) {
                                    //resetto il fragment per renderlo disponibile per la modifica di un nuovo corso
                                    tvAnno.setText(getResources().getText(R.string.layout_msg_cc));
                                    tvCorso.setText(getResources().getString(R.string.layout_msg_cv));
                                    tvCorso.setTextColor(Color.parseColor("#ffffff"));
                                    tvlCorso.setTextColor(Color.parseColor("#ffffff"));
                                    btnCorso.setBackgroundColor(Color.parseColor("#dedede"));
                                    btnCorso.setEnabled(false);
                                    tvData.setText(getResources().getString(R.string.layout_msg_cv2));
                                    tvData.setTextColor(Color.parseColor("#ffffff"));
                                    tvlData.setTextColor(Color.parseColor("#ffffff"));
                                    btnData.setBackgroundColor(Color.parseColor("#dedede"));
                                    btnData.setEnabled(false);
                                    tvVoto.setText(getResources().getString(R.string.layout_msg_cv3));
                                    tvVoto.setTextColor(Color.parseColor("#ffffff"));
                                    tvlVoto.setTextColor(Color.parseColor("#ffffff"));
                                    btnVoto.setBackgroundColor(Color.parseColor("#dedede"));
                                    btnVoto.setEnabled(false);
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
                                //prendo il corso selezionato in base alla scelta dell'utente e lo metto nella TextView del corso
                                tvCorso.setText(""+lcAnnoSelezionato[index]);

                                //abilito il bottone 'data del corso'
                                btnData.setBackgroundColor(Color.parseColor("#ffffff"));
                                tvData.setTextColor(Color.parseColor("#000000"));
                                tvlData.setTextColor(Color.parseColor("#000000"));
                                btnData.setEnabled(true);
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
        btnData.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnData.setBackgroundColor(Color.parseColor("#e6e6e6"));
                        return true;
                    case MotionEvent.ACTION_UP:
                        btnData.setBackgroundColor(Color.parseColor("#ffffff"));

                        //creo un nuovo dialog che si occuperà di mostrare e salvare la data del voto
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        final DatePicker picker = new DatePicker(activity);
                        picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
                        picker.setCalendarViewShown(false);
                        builder.setView(picker);

                        //titolo del dialog
                        builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_cv) );

                        //set dei listener
                        builder.setPositiveButton(R.string.dialog_btn_seleziona, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //metto la data selezionata nella textView del bottone 'data'
                                String meseSelezionato  = monthFromIntToString(activity, picker.getMonth());
                                int giornoSelezionato   = picker.getDayOfMonth();
                                tvData.setText(""+giornoSelezionato+" "+meseSelezionato);

                                //abilito il bottone 'punteggio'
                                btnVoto.setBackgroundColor(Color.parseColor("#ffffff"));
                                tvVoto.setTextColor(Color.parseColor("#000000"));
                                tvlVoto.setTextColor(Color.parseColor("#000000"));
                                btnVoto.setEnabled(true);
                            }
                        });
                        builder.setNegativeButton(R.string.dialog_btn_annulla, null);

                        //visualizzo solo il giorno e il mese della data
                        Utility.showJustDayAndMonth(picker);
                        //visualizzo il dialog
                        Dialog dialog = builder.create();
                        dialog.show();
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

                        //Mostro un dialog che si occupa di scegliere e salvare il voto della nota
                        Calendar votoMinore = Calendar.getInstance();
                        votoMinore.set(2014, Calendar.JANUARY, 1);
                        Calendar votoMaggiore = Calendar.getInstance();
                        votoMaggiore.set(2014, Calendar.JANUARY, 10);
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        final DatePicker picker = new DatePicker(activity);
                        picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
                        picker.setCalendarViewShown(false);
                        picker.setMinDate(votoMinore.getTimeInMillis());
                        picker.setMaxDate(votoMaggiore.getTimeInMillis());
                        builder.setView(picker);
                        builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_cv2) );

                        //setup dei listener sul bottone 'seleziona'
                        builder.setNegativeButton(R.string.dialog_btn_annulla, null);
                        builder.setPositiveButton(R.string.dialog_btn_seleziona, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //salvo il voto nella TextView del voto
                                tvVoto.setText(""+picker.getDayOfMonth());

                                //abilito il bottone 'salva'
                                btnSalva.setBackgroundColor(Color.parseColor("#87a914"));
                                btnSalva.setEnabled(true);
                            }
                        });

                        //mostro solo i giorni del datepicker (1-10 che corrisponderebbero al voto)
                        Utility.showJustDay(picker);

                        //visualizzo il dialog
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

                        //Salvo la creazione del voto (per ora ci metto solo un messaggio)
                        //inizio output messaggio di modifica corso
                        Toast toast = Toast.makeText(activity, "Hai creato un voto nell'anno: "+tvAnno.getText()+
                                "\nnel corso: "+tvCorso.getText()+"\nla cui nota è: "+tvVoto.getText(), Toast.LENGTH_LONG);
                        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                        textView.setGravity(Gravity.CENTER);
                        toast.show();
                        //fine output messaggio di modifica

                        //resetto il fragment per renderlo disponibile per la modifica di un nuovo corso
                        tvAnno.setText(getResources().getText(R.string.layout_msg_cc));
                        tvCorso.setText(getResources().getString(R.string.layout_msg_cv));
                        tvCorso.setTextColor(Color.parseColor("#ffffff"));
                        tvlCorso.setTextColor(Color.parseColor("#ffffff"));
                        btnCorso.setBackgroundColor(Color.parseColor("#dedede"));
                        btnCorso.setEnabled(false);
                        tvData.setText(getResources().getString(R.string.layout_msg_cv2));
                        tvData.setTextColor(Color.parseColor("#ffffff"));
                        tvlData.setTextColor(Color.parseColor("#ffffff"));
                        btnData.setBackgroundColor(Color.parseColor("#dedede"));
                        btnData.setEnabled(false);
                        tvVoto.setText(getResources().getString(R.string.layout_msg_cv3));
                        tvVoto.setTextColor(Color.parseColor("#ffffff"));
                        tvlVoto.setTextColor(Color.parseColor("#ffffff"));
                        btnVoto.setBackgroundColor(Color.parseColor("#dedede"));
                        btnVoto.setEnabled(false);
                        btnSalva.setEnabled(false);
                        return true;
                }
                return false;
            }
        });
        return rootView;
    }
}