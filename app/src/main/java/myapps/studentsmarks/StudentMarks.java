package myapps.studentsmarks;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TabHost;

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

        //istanzia il fragment per la voce 'creaAnno'
        if (position == 1) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, GestioneAnniFragment.newInstance(position + 1))
                    .commit();
        }
        //istanzia il fragment per la voce 'modificaAnno'
        else if (position == 2) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ModificaAnnoFragment.newInstance(position + 1))
                    .commit();
        }
        //istanzia il fragment per la voce 'eliminaAnno'
        else if (position == 3) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, EliminaAnnoFragment.newInstance(position + 1))
                    .commit();
        }
        //istanzia il fragment per la voce 'creaCorso'
        else if (position == 4) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, CreaCorsoFragment.newInstance(position + 1))
                    .commit();
        }
        //istanzia il fragment per la voce 'modificaCorso'
        else if (position == 5) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ModificaCorsoFragment.newInstance(position + 1))
                    .commit();
        }
        //istanzia il fragment per la voce 'eliminaCorso'
        else if (position == 6) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, EliminaCorsoFragment.newInstance(position + 1))
                    .commit();
        }
        //istanzia il fragment per la voce 'creaVoto'
        else if (position == 7) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, CreaVotoFragment.newInstance(position + 1))
                    .commit();
        }
        else {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
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

            // se l'utente ha selezionato un 'anno corrente'
            // lo faccio visualizzare sempre sull'action bar
            /*if (ce un anno corrente selezionato)
                menu.findItem(R.id.action_selected_year).setTitle(getAnnoCorrenteSelezionato());*/
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        FragmentTabHost mTabHost;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_student_marks, container, false);

           // mTabHost = new FragmentTabHost(getActivity());
           // mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.fragment1);


            //mTabHost.addTab(mTabHost.newTabSpec("simple").setIndicator("Simple"),
            //        FragmentTest.class, null);
           //mTabHost.addTab(mTabHost.newTabSpec("contacts").setIndicator("Contacts"),
            //        FragmentTest2.class, null);
           // mTabHost.addTab(mTabHost.newTabSpec("throttle").setIndicator("Throttle"),
           //         FragmentTest.class, null);

          /* mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
               public void onTabChanged(String tabId) {

                    View currentView = mTabHost.getCurrentView();
                   Animation animation = inFromRightAnimation();
                   //currentView.setAnimation(animation);
                   currentView.startAnimation(animation);
               }
               public Animation inFromRightAnimation()
               {
                   Animation inFromRight = new TranslateAnimation(
                           Animation.RELATIVE_TO_PARENT, +1.0f,
                           Animation.RELATIVE_TO_PARENT, 0.0f,
                           Animation.RELATIVE_TO_PARENT, 0.0f,
                           Animation.RELATIVE_TO_PARENT, 0.0f);
                   inFromRight.setDuration(240);
                   inFromRight.setInterpolator(new AccelerateInterpolator());
                   return inFromRight;
               }
           });

            return mTabHost;*/
            return rootView;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            mTabHost = null;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((StudentMarks) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
