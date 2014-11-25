package myapps.studentsmarks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Gio on 16.11.2014.
 */
public class ModificaCorsoFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ModificaCorsoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ModificaCorsoFragment newInstance(int sectionNumber) {
        ModificaCorsoFragment fragment = new ModificaCorsoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_modifica_corso, container, false);
        final StudentMarks activity = (StudentMarks)getActivity();

        //setup del dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        final DatePicker picker = new DatePicker(activity);
        picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        picker.setCalendarViewShown(false);
        builder.setView(picker);

        //titolo dialog
        //builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_mc, R.string.dialog_stit_mc, 0) );

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

                //creo un nuovo Dialog per la selezione del corso da modificare in base all anno scelto
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setCancelable(false);
                //builder.setCustomTitle(customTitleDialog(activity, R.string.dialog_tit_mc, R.string.dialog_stit_mcp2, 0));
                builder.setItems(lcAnnoSelezionato, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, final int index) {
                        //prendo il corso da modificare in base alla scelta dell'utente

                        //attraverso un nuovo dialog faccio inserire all user il nome del corso da modificare
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        final EditText inputModificaCorso = new EditText(activity);
                        builder.setView(inputModificaCorso);
                        //builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_mc, R.string.dialog_stit_mcp3, 0) );
                        builder.setPositiveButton(R.string.dialog_btn_modifica, new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //controllo l'input del corso immesso e poi lo sostituisco con il vecchio nome del corso scelto dall utente

                                //inizio output messaggio di modifica corso
                                Toast toast = Toast.makeText(activity, "Hai modificato il corso: " + lcAnnoSelezionato[index] + "\n ora si chiama: " + inputModificaCorso.getText(), Toast.LENGTH_LONG);
                                TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                                textView.setGravity(Gravity.CENTER);
                                toast.show();
                                //fine output messaggio di modifica

                                //e poi lo riporto alla prima voce del menu 'riepilogo annuale'
                                activity.getMNavigationDrawerFragment().selectItem(0);
                                activity.onSectionAttached(1);
                                activity.restoreActionBar();
                                activity.getMenu().findItem(R.id.action_selected_year).setVisible(true);
                                activity.getMenu().findItem(R.id.action_settings).setVisible(true);
                            }
                        });
                        builder.setNegativeButton(R.string.dialog_btn_annulla, annulla);
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

        //set bottoni dialog
        builder.setPositiveButton(R.string.dialog_btn_seleziona, seleziona);
        builder.setNegativeButton(R.string.dialog_btn_annulla, annulla);

        //mostro solo l'anno del datepicker
        Utility.showJustYear(picker);

        //visualizzo il dialog
        Dialog dialog = builder.create();
        dialog.show();

        return rootView;
    }
}
