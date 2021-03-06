package ch.studentgrades;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabWidget;
import android.widget.TextView;


/**
 * Created by Gio on 24.11.2014.
 */
public class GestioneAnni extends Fragment {

    /*The fragment argument representing the section number for this fragment.*/
    private static final String ARG_SECTION_NUMBER = "section_number";

    /*Gestisce i fragments di creazione, modifica ed eliminazione anno*/
    private FragmentTabHost tabHost;

    public GestioneAnni() {
    }

    /*Returns a new instance of this fragment for the given section number.*/
    public static GestioneAnni newInstance(int sectionNumber) {
        GestioneAnni fragment = new GestioneAnni();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View rootView = inflater.inflate(R.layout.fragment_student_marks, container, false);
        tabHost = new FragmentTabHost(getActivity());
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.gestione_anni_container);

        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.tab_crea_anno)).setIndicator(getResources().getString(R.string.tab_crea_anno)), CreaAnno.class, null);
        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.tab_modifica_anno)).setIndicator(getResources().getString(R.string.tab_modifica_anno)), ModificaAnno.class, null);
        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.tab_elimina_anno)).setIndicator(getResources().getString(R.string.tab_elimina_anno)), EliminaAnno.class, null);

        //INIZIO setup colore verde TabHost
        TabWidget widget = tabHost.getTabWidget();
        for(int i = 0; i < widget.getChildCount(); i++) {
            View v = widget.getChildAt(i);

            // Look for the title view to ensure this is an indicator and not a divider.
            TextView tv = (TextView)v.findViewById(android.R.id.title);
            if(tv == null) {
                continue;
            }
            v.setBackgroundResource(R.drawable.tab_indicator_ab_green);
        }
        //FINE setup colore verde TabHost

        return tabHost;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tabHost = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((StudentGrades) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}