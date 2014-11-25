package myapps.studentsmarks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_crea_corso, container, false);
        final StudentMarks activity = (StudentMarks)getActivity();

        //setup del dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        final DatePicker picker = new DatePicker(activity);
        picker.setCalendarViewShown(false);
        picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        builder.setView(picker);

        //titolo dialog
        //builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_cc, R.string.dialog_stit_cc, 0) );

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

                //creo un nuovo Dialog per la creazione del corso nell'anno selezionato
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setCancelable(false);
                picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
                final EditText inputCreaCorso = new EditText(activity);
                builder.setView(inputCreaCorso);
                //builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_cc, R.string.dialog_stit_ccp2, 0) );

                builder.setPositiveButton(R.string.dialog_btn_salva, new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //controllo l'input del corso immesso e poi lo inserisco nell anno selezionato

                        //inizio output messaggio dicreazione corso
                        Toast toast = Toast.makeText(activity, "Hai creato il corso: " + inputCreaCorso.getText() + "\nnell'anno: " + annoSelezionato, Toast.LENGTH_LONG);
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((StudentMarks) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}

