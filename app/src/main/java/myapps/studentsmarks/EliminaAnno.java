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

import static myapps.studentsmarks.GestioneAnni.CreaArrayNomiAnni;
import static myapps.studentsmarks.GestioneAnni.getListaAnni;
import static myapps.studentsmarks.GestioneAnni.rimuoviAnno;
import static myapps.studentsmarks.Utility.customTitleDialog;
import static myapps.studentsmarks.Utility.makeFrameLWithNumPicker;

/**
 * Created by Gio on 11.11.2014.
 */
public class EliminaAnno extends Fragment {
    /**
            * The fragment argument representing the section number for this
            * fragment.
    */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public EliminaAnno() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EliminaAnno newInstance(int sectionNumber) {
        EliminaAnno fragment = new EliminaAnno();
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

                        //Controllo se ci sono anni creati
                        if (getListaAnni().size() == 0 ) {
                            //se non ci sono anni creati avviso l'utente e poi interrompo la procedura
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setCancelable(false);
                            builder.setCustomTitle(Utility.customTitleDialog(activity, R.string.dialog_tit_avviso));
                            builder.setPositiveButton(R.string.dialog_btn_ok, null);
                            TextView messaggio = new TextView(activity);
                            messaggio.setText(R.string.errore_msg5);
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
                        rimuoviAnno( (String)tvAnno.getText() );

                        //inizio messaggio output - elimina anno
                        Toast toast = Toast.makeText(activity, getResources().getText(R.string.output_msg_ea), Toast.LENGTH_LONG);
                        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                        textView.setGravity(Gravity.CENTER);
                        toast.show();
                        //fine messaggio output - elimina anno

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
