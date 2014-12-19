package myapps.studentgrades;

/**
 * Created by Gio on 10.11.2014.
 */

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

import static myapps.studentgrades.DataSource.CreaArrayNomiAnni;
import static myapps.studentgrades.DataSource.annoGiaEsistente;
import static myapps.studentgrades.DataSource.getAnno;
import static myapps.studentgrades.DataSource.getListaAnni;
import static myapps.studentgrades.Utility.customTitleDialog;
import static myapps.studentgrades.Utility.makeFrameLWithNumPicker;
import static myapps.studentgrades.Utility.makeSchoolYearList;


/**
 * Created by Giovanni far on 10.11.2014.
 */
public class ModificaAnno extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ModificaAnno() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ModificaAnno newInstance(int sectionNumber) {
        ModificaAnno fragment = new ModificaAnno();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_modifica_anno, container, false);
        final StudentGrades activity = ((StudentGrades)getActivity());
        final Button btnSalva       = (Button)rootView.findViewById(R.id.salva);
        final Button btnAnno        = (Button)rootView.findViewById(R.id.anno);
        final Button btnAnnoDM      = (Button)rootView.findViewById(R.id.anno_dm);
        final TextView tvAnno       = (TextView)rootView.findViewById(R.id.tv_anno);
        final TextView tvAnnoDM     = (TextView)rootView.findViewById(R.id.tv_anno_dm);
        final TextView tvlAnnoDM    = (TextView)rootView.findViewById(R.id.tvl_anno_dm);
        final String msgAnnoDM      = (String)getResources().getText(R.string.layout_tvl_anno_dm);

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
                            messaggio.setText(R.string.errore_msg4);
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

                        //mostro il dialog che permette la selezione dell'anno da modificare
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
                        builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_ma) );

                        //listener bottone 'seleziona'
                        Dialog.OnClickListener seleziona = new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //prendo l'anno selezionato e lo mostro nella TextView relativa al bottone anno
                                tvAnno.setText( ""+anniCreatiutente[picker.getValue()] );
                                //abilito il bottone della modifica anno e le sue varie impostazioni
                                btnAnnoDM.setBackgroundColor(Color.parseColor("#ffffff"));
                                btnAnnoDM.setEnabled(true);
                                //permette di vedere nella label textView del bottone di modifica anno,
                                //la selezione dell'anno da modificare fatta dall'utente
                                tvlAnnoDM.setText( msgAnnoDM+" "+anniCreatiutente[picker.getValue()] );
                                tvlAnnoDM.setTextColor(Color.parseColor("#000000"));
                                tvAnnoDM.setTextColor(Color.parseColor("#000000"));
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
        btnAnnoDM.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnAnnoDM.setBackgroundColor(Color.parseColor("#e6e6e6"));
                        return true;

                    case MotionEvent.ACTION_UP:
                        btnAnnoDM.setBackgroundColor(Color.parseColor("#ffffff"));

                        //Calcolo l'anno corrente
                        Calendar calendar = Calendar.getInstance();
                        int annoCorrente  = calendar.get(Calendar.YEAR);

                        //creo una lista di anni, da: annocorrente-10 a: annocorrente
                        final String[] listaAnniScolastici = makeSchoolYearList(annoCorrente);

                        //creo un nuovo Dialog per la modifica dell'anno selezionato
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

                        //titolo del dialog
                        builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_ma2) );

                        builder.setPositiveButton(R.string.dialog_btn_seleziona, new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //verifico se l'anno che vuole modificare esiste gia
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
                                //prendo il nuovo anno selezionato dall'utente e lo inserisco nella textView della modifica dell'anno
                                tvAnnoDM.setText("" + listaAnniScolastici[picker.getValue()]);

                                //abilito e preparo il setup del bottone 'salva'
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
        //listener bottone 'salva'
        btnSalva.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btnSalva.setBackgroundColor(Color.parseColor("#537719"));
                        return true;
                    case MotionEvent.ACTION_UP:
                        //salvo i dati relativi alla modifica dell'anno
                        //recupero l'anno che ha subito la modifica
                        Anno annoDaModificare = getAnno( (String)tvAnno.getText() );

                        //infine modifico l'anno
                        annoDaModificare.setNomeAnnoScolastico( (String)tvAnnoDM.getText() );

                        //inizio messaggio output - modifica anno
                        Toast toast = Toast.makeText(activity, getResources().getText(R.string.output_msg_ma), Toast.LENGTH_LONG);
                        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                        textView.setGravity(Gravity.CENTER);
                        toast.show();
                        //fine messaggio output - modifica anno

                        //e poi resetto il fragment per renderlo disponibile per la modifica di un nuovo anno
                        btnAnnoDM.setBackgroundColor(Color.parseColor("#dedede"));
                        tvlAnnoDM.setText(getResources().getText(R.string.layout_tvl_anno_dm));
                        tvAnnoDM.setText(getResources().getText(R.string.layout_msg_ma2));
                        tvlAnnoDM.setTextColor(Color.parseColor("#ffffff"));
                        tvAnnoDM.setTextColor(Color.parseColor("#ffffff"));
                        btnAnnoDM.setEnabled(false);
                        tvAnno.setText(getResources().getText(R.string.layout_msg_ma));
                        btnSalva.setBackgroundColor(Color.parseColor("#d2d2d2"));
                        btnSalva.setEnabled(false);
                        return true;
                }
                return false;
            }
        });
        return rootView;
    }
}