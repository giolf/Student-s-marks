package myapps.studentsmarks;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import static myapps.studentsmarks.GestioneAnniFragment.getNomeAnnoSelezionato;
import static myapps.studentsmarks.GestioneAnniFragment.getListaAnni;

public class StudentMarks extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    /**
     * Usato per gestire il menu, nello specifico i bottoni di selezione anno e delle impostazioni
     */
    private Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public NavigationDrawerFragment getMNavigationDrawerFragment() {
        return mNavigationDrawerFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_marks);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        //istanzia il fragment-container per la voce 'Riepilogo annuale'
        if (position == 0) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, GestioneRiepilogoAnnualeFragment.newInstance(position + 1))
                    .commit();
        }

        //istanzia il fragment-container per la voce 'Gestione anni'
        else if (position == 1) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, GestioneAnniFragment.newInstance(position + 1))
                    .commit();
        }
        //istanzia il fragment-container per la voce 'Gestione corsi'
        else if (position == 2) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, GestioneCorsiFragment.newInstance(position + 1))
                    .commit();
        }
        //istanzia il fragment per la voce 'Gestione voti'
        else if (position == 3) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, GestioneVotiFragment.newInstance(position + 1))
                    .commit();
        }
    }


    public void onSectionAttached(int number) {
        //setta il titolo nell actionbar della voce di menu selezionata
        mTitle = getResources().getStringArray(R.array.menu)[number-1];
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if ( !mNavigationDrawerFragment.isDrawerOpen() ) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.student_marks, menu);
            //salvo il menu nella variabile d'istanza dell'activity
            this.menu = menu;

            // se l'utente non ha creato nessun anno, per default visualizzo un '-'
            if ( getListaAnni().size() == 0 )
                menu.findItem(R.id.action_selected_year).setTitle(getResources().getString(R.string.action_selected_year));

            // se l'utente ha almeno 1 anno creato, MA non ha ancora selezionato un anno per il quale
            // visualizzarne le relative statistiche, per default imposto l'ultimo anno creato
            else if ( getListaAnni().size() > 0 && getNomeAnnoSelezionato() == null ) {
                int numeroAnniCreati        = getListaAnni().size();
                Anno ultimoAnnoCreato       = getListaAnni().get(numeroAnniCreati-1);
                String nomeUltimoAnnoCreato = ultimoAnnoCreato.getNomeAnnoScolastico();

                menu.findItem(R.id.action_selected_year).setTitle(nomeUltimoAnnoCreato);
            }

            // se l'utente ha almeno 1 anno creato e ha selezionato un anno per il quale
            // visualizzarne le relative statistiche, imposto la selezione fatta dall utente
            else if ( getListaAnni().size() > 0 && getNomeAnnoSelezionato() != null )
                menu.findItem(R.id.action_selected_year).setTitle(getNomeAnnoSelezionato());

            //reimposto l'actionBar impostandogli il titolo
            restoreActionBar();

            //dopo aver settato il titolo controllo in quale pagina l'utente si trova
            //se non sono in 'riepilogo annuale' nascondo i 2 bottoni nell'actionbar
            if ( !mTitle.equals(getResources().getStringArray(R.array.menu)[0]) ) {
                getMenu().findItem(R.id.action_selected_year).setVisible(false);
                getMenu().findItem(R.id.action_settings).setVisible(false);
            }
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
