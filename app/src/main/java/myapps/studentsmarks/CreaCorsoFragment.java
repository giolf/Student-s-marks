package myapps.studentsmarks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static myapps.studentsmarks.Utility.customTitleDialog;

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
                        //mostro un dialog che consente di selezionare l'anno in cui si vuole creare il corso
                        //setup del dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        final DatePicker picker = new DatePicker(activity);
                        picker.setCalendarViewShown(false);
                        picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
                        builder.setView(picker);

                        //titolo dialog
                        builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_cc) );

                        //listeners bottone seleziona'
                        Dialog.OnClickListener seleziona = new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //salvo l'anno selezionato dall'utente e lo mostro nella TextView dell'anno
                                tvAnno.setText(""+picker.getYear());

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

                                //inserisco il corso immesso nella TextView del corso
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
                        //controllo se l'input è corretto e se il corso può essere inserito nell'anno selezionato

                        //se tutto è ok, salvo il corso nell'anno selezionato
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

