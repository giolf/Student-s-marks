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
import android.widget.TextView;
import android.widget.Toast;


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

                        //appare il dialog della selezione dell'anno da creare
                        //setup del dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        final DatePicker picker = new DatePicker(activity);
                        picker.setCalendarViewShown(false);
                        picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
                        builder.setView(picker);

                        //titolo dialog
                        builder.setCustomTitle(Utility.customTitleDialog(activity, R.string.dialog_tit_ca));

                        //listeners bottoni 'annulla' e 'salva'
                        Dialog.OnClickListener salva = new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //prendo i l'anno selezionato e lo mostro nel bottone dell'anno
                                tvAnno.setText(""+picker.getYear());

                                //abilito il bottone salva
                                btnSalva.setBackgroundColor(Color.parseColor("#87a914"));
                                btnSalva.setEnabled(true);
                            }
                        };
                        //bottoni dialog
                        builder.setPositiveButton(R.string.dialog_btn_salva, salva);
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
                        Toast.makeText(activity, "hai creato l'anno: "+tvAnno.getText(), Toast.LENGTH_LONG).show();

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
