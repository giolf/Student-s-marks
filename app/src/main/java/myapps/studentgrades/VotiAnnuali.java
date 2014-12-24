package myapps.studentgrades;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

import static myapps.studentgrades.DataSource.CreaArrayNomiAnni;
import static myapps.studentgrades.DataSource.getAnno;
import static myapps.studentgrades.DataSource.getListaAnni;
import static myapps.studentgrades.DataSource.getPosizioneAnno;
import static myapps.studentgrades.DataSource.setNomeAnnoSelezionato;
import static myapps.studentgrades.Utility.customTitleDialog;
import static myapps.studentgrades.Utility.makeFrameLWithNumPicker;
import static myapps.studentgrades.Utility.setupMenuRiepilogoAnnuale;

/**
 * Created by Gio on 10.12.2014.
 */
public class VotiAnnuali extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public VotiAnnuali() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static VotiAnnuali newInstance(int sectionNumber) {
        VotiAnnuali fragment = new VotiAnnuali();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_voti_annuali, container, false);

        //mi permette di chiamare il metodo onCreateOptionMenu() di questa classe dopo il termine di questo metodo
        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        StudentGrades activity = (StudentGrades)getActivity();

        if ( !activity.getMNavigationDrawerFragment().isDrawerOpen() ) {

            setupMenuRiepilogoAnnuale(menu, this);

             /*INIZIO impostazioni view della pagina 'media annuale'*/
            View rootView = getView();
            String nomeAnnoSelezionato = (String)menu.findItem(R.id.action_selected_year).getTitle();
            Anno anno = getAnno(nomeAnnoSelezionato);

            //se non ce selezionato nessun anno avverto l'utente che non è possibile visualizzare la lista dei voti
            if ( nomeAnnoSelezionato.equals("-") ) {
                //trovarsi dentro a questo blocco significa, indirettamente, a non aver creato nessun anno
                rootView.findViewById(R.id.lwVotiAnnuali).setVisibility(View.INVISIBLE);
                rootView.findViewById(R.id.msgListaVotiNonDisp).setVisibility(View.VISIBLE);
                //dopo di che termino qui il metodo
                return;
            }
            //se l'anno selezionato non ha voti avverto l'utente che non è possibile visualizzare la media dei voti
            else if (anno.numeroVotiAnnuali() == 0) {
                rootView.findViewById(R.id.lwVotiAnnuali).setVisibility(View.INVISIBLE);
                rootView.findViewById(R.id.msgListaVotiNonDisp).setVisibility(View.VISIBLE);
                //dopo di che termino qui il metodo
                return;
            }
            else {
                //recupero la lista dei voti annuali
                ArrayList<Voto> listaVoti = anno.creaListaVotiAnnuali();

                //nascondo il messaggio che indica che non ci sono voti disponibili per l'anno selezionato
                rootView.findViewById(R.id.msgListaVotiNonDisp).setVisibility(View.INVISIBLE);

                //setto l'adapter alla listView con la lista dei voti dell'anno selezionato
                ListView lw = (ListView)rootView.findViewById(R.id.lwVotiAnnuali);
                lw.setAdapter(new AdapterListaVotiView(activity, R.layout.row_voti_annuali, listaVoti));
            }
            /*FINE impostazioni view della pagina 'media annuale'*/
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        StudentGrades activity = (StudentGrades)getActivity();
        final View rootView    = getView();

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

                //recupero la posizione dell'anno creato (quando il dialog verrà aperto, il cursore sarà sull'anno attualmente selezionato)
                int posizioneAnno = getPosizioneAnno( anniCreatiutente, (String)item.getTitle() );

                //mostro il dialog che permette la selezione dell'anno scolastico
                //setup del dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setCancelable(false);
                final NumberPicker picker = new NumberPicker(activity);
                picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                picker.setMinValue(0);
                picker.setMaxValue(anniCreatiutente.length-1);
                picker.setValue(posizioneAnno);
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
                        String nomeAnnoSelezionato = anniCreatiutente[picker.getValue()];

                        //salvo la selezione dell'anno
                        setNomeAnnoSelezionato(nomeAnnoSelezionato);

                        //mostro l'anno selezionato nel bottone del menu
                        item.setTitle(nomeAnnoSelezionato);

                        //recupero l'anno selezionato
                        Anno annoSelezionato = getAnno(nomeAnnoSelezionato);

                        //se l'anno selezionato non ha voti avverto l'utente che non è possibile visualizzare la lista dei voti
                        if (annoSelezionato.numeroVotiAnnuali() == 0) {
                            rootView.findViewById(R.id.lwVotiAnnuali).setVisibility(View.INVISIBLE);
                            rootView.findViewById(R.id.msgListaVotiNonDisp).setVisibility(View.VISIBLE);
                            //dopo di che termino qui il metodo
                            return;
                        }
                        else {
                            //l'anno selezionato ha voti: recupero i suoi voti annuali
                            ArrayList<Voto> listaVoti = getAnno(nomeAnnoSelezionato).creaListaVotiAnnuali();

                            //passo la lista dei voti dell'anno selezionato all'adapter
                            ListView lw = (ListView)rootView.findViewById(R.id.lwVotiAnnuali);
                            AdapterListaVotiView adapter =(AdapterListaVotiView)lw.getAdapter();
                            adapter.aggiornaListaVoti(listaVoti);

                            //infine rendo visibile la listView che visualizzerà la lista dei voti dell'anno selezionato
                            //e per sicurezza nascondo la textview del messaggio 'lista voti non disponibile' (in quanto potrebbe essere ancora visibile)
                            rootView.findViewById(R.id.msgListaVotiNonDisp).setVisibility(View.INVISIBLE);
                            rootView.findViewById(R.id.lwVotiAnnuali).setVisibility(View.VISIBLE);
                        }
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
