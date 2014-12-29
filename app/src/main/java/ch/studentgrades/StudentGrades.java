package ch.studentgrades;

import android.app.Activity;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import static ch.studentgrades.DataSource.inizializzazioneStrutDati;

public class StudentGrades extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    public NavigationDrawerFragment getMNavigationDrawerFragment() {
        return mNavigationDrawerFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_marks);

        // Recupero dati dal DB e inserimento degli stessi nella struttura dati
        inizializzazioneStrutDati(this);

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
                    .replace(R.id.container, GestioneRiepilogoAnnuale.newInstance(position + 1))
                    .commit();
        }

        //istanzia il fragment-container per la voce 'Gestione anni'
        else if (position == 1) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, GestioneAnni.newInstance(position + 1))
                    .commit();
        }
        //istanzia il fragment-container per la voce 'Gestione corsi'
        else if (position == 2) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, GestioneCorsi.newInstance(position + 1))
                    .commit();
        }
        //istanzia il fragment per la voce 'Gestione voti'
        else if (position == 3) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, GestioneVoti.newInstance(position + 1))
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

            //reimposto l'actionBar impostandogli il titolo
            restoreActionBar();

            //dopo aver settato il titolo controllo in quale pagina l'utente si trova
            //se non sono in 'riepilogo annuale' nascondo i 2 bottoni nell'actionbar
            if ( !mTitle.equals(getResources().getStringArray(R.array.menu)[0]) ) {
                menu.findItem(R.id.action_selected_year).setVisible(false);
                menu.findItem(R.id.action_settings).setVisible(false);
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
