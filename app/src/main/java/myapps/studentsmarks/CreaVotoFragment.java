package myapps.studentsmarks;

import android.app.Activity;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

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
        //creo una nuova variabile che referenzia la main actitivity
        final StudentMarks activity = (StudentMarks)getActivity();
        //recupero dei bottoni 'annulla' e 'salva'
        final Button btnAnnulla = (Button)rootView.findViewById(R.id.annulla);
        final Button btnSalva = (Button)rootView.findViewById(R.id.salva);

        //setup del dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        final DatePicker picker = new DatePicker(activity);
        picker.setCalendarViewShown(false);
        picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        builder.setView(picker);

        //titolo dialog
        //builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_cv, R.string.dialog_stit_cv, 0) );

        //listeners bottoni 'annulla' e 'seleziona'
        final Dialog.OnClickListener annulla = new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //riporto l'utente alla voce del menu 'riepilogo annuale'
                activity.getMNavigationDrawerFragment().selectItem(0);
                activity.onSectionAttached(1);
                activity.restoreActionBar();
                activity.getMenu().findItem(R.id.action_selected_year).setVisible(true);
                activity.getMenu().findItem(R.id.action_settings).setVisible(true);
            }
        };
        Dialog.OnClickListener seleziona = new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //salvo l'anno selezionato dall'utente
                final int annoSelezionato = picker.getYear();

                //qui prendo i corsi dell'anno selezionato
                final String[] lcAnnoSelezionato = {"Italiano", "Matematica", "Francese", "Fisica", "Tedesco", "Storia", "Scienze", "Latino", "Storia", "Scienze", "Latino"};

                //creo un nuovo Dialog per la selezione del corso dove sarà inserito il voto
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setCancelable(false);
                //builder.setCustomTitle(customTitleDialog(activity, R.string.dialog_tit_cv, R.string.dialog_stit_cvp2, 0));
                builder.setItems(lcAnnoSelezionato, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //qui prendo il corso scelto
                        final String corsoScelto = lcAnnoSelezionato[i];

                        //creo un nuovo dialog che si occuperà di mostrar e salvare la data del voto
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        final DatePicker picker = new DatePicker(activity);
                        picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
                        picker.setCalendarViewShown(false);
                        builder.setView(picker);

                        //titolo del dialog
                        //builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_cv, R.string.dialog_stit_cvp3, 0) );

                        //set dei listener
                        builder.setNegativeButton(R.string.dialog_btn_annulla, annulla);
                        builder.setPositiveButton(R.string.dialog_btn_seleziona, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //qui salvo la data selezionata e mostro un dialog che si occupera di scegliere il voto della nota
                                final int meseSelezionato = picker.getMonth() + 1;
                                final int giornoSelezionato = picker.getDayOfMonth();
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
                                //builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_cv, R.string.dialog_stit_cvp4, 0) );

                                //setup dei listener su i bottoni 'annulla' e 'seleziona'
                                builder.setNegativeButton(R.string.dialog_btn_annulla, annulla);
                                builder.setPositiveButton(R.string.dialog_btn_seleziona, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //salvo il voto selezionato
                                        int votoSelezionato = picker.getDayOfMonth();

                                        //rendo visibili tutte le view dell layout
                                        Button btnAnno = (Button)rootView.findViewById(R.id.anno);
                                        btnAnno.setVisibility(View.VISIBLE);
                                        rootView.findViewById(R.id.tvl_anno).setVisibility(View.VISIBLE);
                                        TextView tvAnno = (TextView)rootView.findViewById(R.id.tv_anno);
                                        tvAnno.setVisibility(View.VISIBLE);
                                        Button btnCorso = (Button)rootView.findViewById(R.id.corso);
                                        btnCorso.setVisibility(View.VISIBLE);
                                        rootView.findViewById(R.id.tvl_corso).setVisibility(View.VISIBLE);
                                        TextView tvCorso = (TextView)rootView.findViewById(R.id.tv_corso);
                                        tvCorso.setVisibility(View.VISIBLE);
                                        Button btnData = (Button)rootView.findViewById(R.id.anno_dm);
                                        btnData.setVisibility(View.VISIBLE);
                                        rootView.findViewById(R.id.tvl_anno_dm).setVisibility(View.VISIBLE);
                                        TextView tvData = (TextView)rootView.findViewById(R.id.tv_anno_dm);
                                        tvData.setVisibility(View.VISIBLE);
                                        Button btnVoto = (Button)rootView.findViewById(R.id.voto);
                                        btnVoto.setVisibility(View.VISIBLE);
                                        rootView.findViewById(R.id.tvl_voto).setVisibility(View.VISIBLE);
                                        TextView tvVoto = (TextView)rootView.findViewById(R.id.tv_voto);
                                        tvVoto.setVisibility(View.VISIBLE);

                                        //mostro i dati selezionati dall utente sulle varie views
                                        tvAnno.setText(""+annoSelezionato);
                                        tvCorso.setText(corsoScelto);
                                        tvData.setText(giornoSelezionato+"/"+meseSelezionato);
                                        tvVoto.setText(""+votoSelezionato);

                                        //rendo visibili i bottoni dell layout 'annulla' e 'salva'
                                        btnAnnulla.setVisibility(View.VISIBLE);
                                        btnSalva.setVisibility(View.VISIBLE);

                                        //set listener dei bottoni 'annulla' e 'salva'
                                        btnSalva.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                                switch(motionEvent.getAction()) {
                                                    case MotionEvent.ACTION_DOWN:
                                                        btnSalva.setBackgroundColor(Color.parseColor("#537719"));
                                                        return true; // if you want to handle the touch event
                                                    case MotionEvent.ACTION_UP:
                                                        btnSalva.setBackgroundColor(Color.parseColor("#87a914"));

                                                        //qui salvo tutti i dati relativi al voto immessi dall user

                                                        //riporto alla prima voce del menu 'riepilogo annuale'
                                                        activity.getMNavigationDrawerFragment().selectItem(0);
                                                        activity.onSectionAttached(1);
                                                        activity.restoreActionBar();
                                                        activity.getMenu().findItem(R.id.action_selected_year).setVisible(true);
                                                        activity.getMenu().findItem(R.id.action_settings).setVisible(true);
                                                        return true; // if you want to handle the touch event
                                                }
                                                return false;
                                            }
                                        });
                                        btnAnnulla.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                                switch(motionEvent.getAction()) {
                                                    case MotionEvent.ACTION_DOWN:
                                                        btnAnnulla.setBackgroundColor(Color.parseColor("#537719"));
                                                        return true; // if you want to handle the touch event
                                                    case MotionEvent.ACTION_UP:
                                                        btnAnnulla.setBackgroundColor(Color.parseColor("#87a914"));
                                                        //riporto alla prima voce del menu 'riepilogo annuale'
                                                        activity.getMNavigationDrawerFragment().selectItem(0);
                                                        activity.onSectionAttached(1);
                                                        activity.restoreActionBar();
                                                        activity.getMenu().findItem(R.id.action_selected_year).setVisible(true);
                                                        activity.getMenu().findItem(R.id.action_settings).setVisible(true);
                                                        return true; // if you want to handle the touch event
                                                }
                                                return false;
                                            }
                                        });
                                    }
                                });
                                //mostro solo i giorni del datepicker (1-10 che corrisponderebbero al voto)
                                Utility.showJustDay(picker);

                                //visualizzo il dialog
                                Dialog dialog = builder.create();
                                dialog.show();
                            }
                        });
                        //visualizzo solo il giorno e il mese della data
                        Utility.showJustDayAndMonth(picker);
                        //visualizzo il dialog
                        Dialog dialog = builder.create();
                        dialog.show();
                    }
                });

                builder.setNegativeButton(R.string.dialog_btn_annulla, annulla);

                Dialog dialog = builder.create();
                dialog.show();
                //imposto le dimensioni della finestra dell'alertDialog
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.height = 1000;
                dialog.getWindow().setAttributes(lp);
            }
        };

        //set dei bottoni del dialog
        builder.setPositiveButton(R.string.dialog_btn_seleziona, seleziona);
        builder.setNegativeButton(R.string.dialog_btn_annulla, annulla);

        //mostro solo l'anno del datepicker
        Utility.showJustYear(picker);

        //visualizzo il dialog
        Dialog dialog = builder.create();
        dialog.show();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((StudentMarks) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}