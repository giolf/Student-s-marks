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
import java.util.Calendar;

import static myapps.studentsmarks.Utility.customTitleDialog;

/**
 * Created by Gio on 11.11.2014.
 */
public class EliminaAnnoFragment extends Fragment {
    /**
            * The fragment argument representing the section number for this
            * fragment.
    */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public EliminaAnnoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EliminaAnnoFragment newInstance(int sectionNumber) {
        EliminaAnnoFragment fragment = new EliminaAnnoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView               = inflater.inflate(R.layout.fragment_elimina_anno, container, false);
        final StudentMarks activity = ((StudentMarks)getActivity());
        final Button btnAnno        = (Button)rootView.findViewById(R.id.anno);
        final Button btnElimina     = (Button)rootView.findViewById(R.id.salva);
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
                        builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_ea) );

                        //listeners bottone 'seleziona'
                        Dialog.OnClickListener seleziona = new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //inserisco nella TextView dell'anno l'anno selezionato dall'utente (cioe quello da eliminare)
                                tvAnno.setText(""+picker.getYear());
                                //abilito e preparo il setuo del bottone 'elimina'
                                btnElimina.setBackgroundColor(Color.parseColor("#87a914"));
                                btnElimina.setEnabled(true);
                            }
                        };

                        //setup dei bottoni del dialog
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
        btnElimina.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnElimina.setBackgroundColor(Color.parseColor("#537719"));
                        return true;
                    case MotionEvent.ACTION_UP:
                        //elimino l'anno selezionato dall'utente
                        Toast.makeText(activity, "hai eliminato l'anno: "+tvAnno.getText(), Toast.LENGTH_LONG).show();
                        //e poi resetto il fragment per renderlo disponibile per l'eliminazione di un nuovo anno
                        tvAnno.setText(getResources().getText(R.string.layout_msg_ea));
                        btnElimina.setEnabled(false);
                        btnElimina.setBackgroundColor(Color.parseColor("#d2d2d2"));
                        return true;
                }
                return false;
            }
        });

        return rootView;
    }
}
