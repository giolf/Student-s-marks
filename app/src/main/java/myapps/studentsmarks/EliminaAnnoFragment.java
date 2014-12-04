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
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import static myapps.studentsmarks.Utility.customTitleDialog;
import static myapps.studentsmarks.Utility.makeFrameLWithNumPicker;

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

                        //recupero la lista degli anni creati dall'utente
                        //qui dovro recuperarla da un arraylist di anni (attraverso un metodo implementato nella classe container degli anni)
                        final String[] anniCreatiutente = Utility.makeSchoolYearList(2014);

                        //setup del dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        final NumberPicker picker = new NumberPicker(activity);
                        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                        picker.setMinValue(0);
                        picker.setMaxValue(anniCreatiutente.length - 1);
                        picker.setValue(anniCreatiutente.length - 1);
                        picker.setWrapSelectorWheel(false);
                        picker.setDisplayedValues(anniCreatiutente);
                        FrameLayout frameLayout = makeFrameLWithNumPicker(picker, activity);
                        builder.setView(frameLayout);
                        builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_ea) );

                        //listeners bottone 'seleziona'
                        Dialog.OnClickListener seleziona = new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //inserisco nella TextView dell'anno l'anno selezionato dall'utente (cioe quello da eliminare)
                                tvAnno.setText(""+anniCreatiutente[picker.getValue()]);
                                //abilito e preparo il setuo del bottone 'elimina'
                                btnElimina.setBackgroundColor(Color.parseColor("#87a914"));
                                btnElimina.setEnabled(true);
                            }
                        };

                        //setup dei bottoni del dialog
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
