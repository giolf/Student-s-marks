package myapps.studentgrades;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import static myapps.studentgrades.GestioneAnni.CreaArrayNomiAnni;
import static myapps.studentgrades.GestioneAnni.getAnno;
import static myapps.studentgrades.GestioneAnni.getListaAnni;
import static myapps.studentgrades.GestioneAnni.getNomeAnnoSelezionato;
import static myapps.studentgrades.GestioneAnni.setNomeAnnoSelezionatoo;
import static myapps.studentgrades.Utility.customTitleDialog;
import static myapps.studentgrades.Utility.makeFrameLWithNumPicker;

/**
 * Created by Gio on 10.12.2014.
 */
public class MediaAnnuale extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public MediaAnnuale() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MediaAnnuale newInstance(int sectionNumber) {
        MediaAnnuale fragment = new MediaAnnuale();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_media_annuale, container, false);
        //mi permette di chiamare onCreateOptionMenu() in questa classe subito dopo il termine di questo metodo
        setHasOptionsMenu(true);


        return rootView;
    }

    /**
     * in realta il menu è gia stato creato attraverso il metodo onCreateOptionMenu dell'activity.
     * uso questo metodo semplicemente per il fatto che onCreateView viene chiamato PRIMA del metodo onCreateOptionMenu dell'activity
     * quindi in tale metodo non potrei fare i controlli necessari con il bottone di selezione dell'anno del menu in quanto non è ancora stato settato il menu
     * quindi li faccio all'interno di questo metodo che mi da a dispozione il menu che è lo stesso di quello che ha settato l'activity
     * QUI DENTRO IL MENU E' GIA SETTATO NON HO BISOGNO DI SETTARLO UN ALTRA VOLTA MA LO USO PER FARE DEI CONTROLLI DI GESTIONE
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        StudentGrades activity = (StudentGrades)getActivity();

        if ( !activity.getMNavigationDrawerFragment().isDrawerOpen() ) {
            View rootView = getView();
            String AnnoSelezionato = (String)menu.findItem(R.id.action_selected_year).getTitle();
            Anno anno = GestioneAnni.getAnno(AnnoSelezionato);
            //se non ce selezionato nessun anno avverto l'utente che non è possibile visualizzare la media
            if ( AnnoSelezionato.equals("-") ) {
                //trovarsi dentro a questo blocco significa, indirettamente, a non aver creato nessun anno
                rootView.findViewById(R.id.mediaAnnuale).setVisibility(View.INVISIBLE);
                rootView.findViewById(R.id.varMediaAnnuale).setVisibility(View.INVISIBLE);
                rootView.findViewById(R.id.msgMediaNonDisp).setVisibility(View.VISIBLE);
                //dopo di che termino qui il metodo
                return;
            }
            //se l'anno selezionato non ha voti avverto l'utente che non è possibile visualizzare la media
            else if (anno.numeroVotiAnnuali() == 0) {
                rootView.findViewById(R.id.mediaAnnuale).setVisibility(View.INVISIBLE);
                rootView.findViewById(R.id.varMediaAnnuale).setVisibility(View.INVISIBLE);
                rootView.findViewById(R.id.msgMediaNonDisp).setVisibility(View.VISIBLE);
                //dopo di che termino qui il metodo
                return;
            }
            else {
                //recupero il valore della media attuale annuale
                double mediaAttuale = anno.getMediaAttuale();

                //calcolo il valore della differenza della media annuale (mediaAttuale - mediaPrecedente)
                double diffMedia    = anno.getDifferenzaMediaAttualePrecedente();

                //mostro le media e la differenza di media sulle relative textView
                ((TextView)rootView.findViewById(R.id.mediaAnnuale)).setText(""+mediaAttuale);
                ((TextView)rootView.findViewById(R.id.varMediaAnnuale)).setText(""+diffMedia);
                rootView.findViewById(R.id.msgMediaNonDisp).setVisibility(View.INVISIBLE);
                rootView.findViewById(R.id.mediaAnnuale).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.varMediaAnnuale).setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        StudentGrades activity = (StudentGrades)getActivity();

        //se il bottone del menu cliccato è il selettore dell'anno
        if (item.getItemId() == R.id.action_selected_year) {
            //per prima cosa controllo se ci sono anni creati
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
                return true;
            }
            else {
                //se ce almeno un anno scolastico appare un dialog che permette all utente di selezionarne uno
                //recupero la lista degli anni creati dall'utente
                final String[] anniCreatiutente = CreaArrayNomiAnni();

                //mostro il dialog che permette la selezione dell'anno scolastico
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
                builder.setCustomTitle( customTitleDialog(activity, R.string.dialog_tit_menu_sa) );

                //listener bottone 'seleziona'
                Dialog.OnClickListener seleziona = new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //recupero la selezione dell'anno fatta tramite il dialog
                        String annoSelezionato = anniCreatiutente[picker.getValue()];

                        //salvo la selezione dell'anno
                        setNomeAnnoSelezionatoo(annoSelezionato);

                        //mostro l'anno selezionato nel bottone del menu
                        item.setTitle(annoSelezionato);

                        //recupero la media e la differenza di media dell'anno selezionato
                        double mediaAttuale = getAnno(annoSelezionato).getMediaAttuale();
                        double diffMedia    = getAnno(annoSelezionato).getDifferenzaMediaAttualePrecedente();

                        //mostro la media e la differenza di media nelle TextView 'mediaAnnuale', 'varMediaAnnuale'
                        ((TextView)getView().findViewById(R.id.mediaAnnuale)).setText(""+mediaAttuale);
                        ((TextView)getView().findViewById(R.id.varMediaAnnuale)).setText(""+diffMedia);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
