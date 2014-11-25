package myapps.studentsmarks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;

import static myapps.studentsmarks.Utility.customTitleDialog;

/**
 * Created by Gio on 16.11.2014.
 */
public class EliminaCorsoFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public EliminaCorsoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EliminaCorsoFragment newInstance(int sectionNumber) {
        EliminaCorsoFragment fragment = new EliminaCorsoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_elimina_corso, container, false);
        final StudentMarks activity = (StudentMarks)getActivity();

        //setup del dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        final DatePicker picker = new DatePicker(activity);
        picker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        picker.setCalendarViewShown(false);
        builder.setView(picker);

        //titolo dialog
        //builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_ec, R.string.dialog_stit_ec, 0) );

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
                String[] lcAnnoSelezionato = {"Italiano", "Matematica", "Francese", "Fisica", "Tedesco", "Storia", "Scienze", "Latino", "Storia", "Scienze", "Latino"};

                //creo un nuovo Dialog per la selezione del corso da eliminare in base all anno scelto
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setCancelable(false);
                //builder.setCustomTitle(customTitleDialog(activity, R.string.dialog_tit_ec, R.string.dialog_stit_ecp2, 0));
                builder.setItems(lcAnnoSelezionato, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //qui elimino il corso selezionato in base all'anno scelto

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
