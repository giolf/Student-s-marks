package myapps.studentgrades;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Gio on 24.11.2014.
 */
public class GestioneCorsi extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private FragmentTabHost tabHost;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static GestioneCorsi newInstance(int sectionNumber) {
        GestioneCorsi fragment = new GestioneCorsi();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public GestioneCorsi() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.fragment_student_marks, container, false);
        tabHost = new FragmentTabHost(getActivity());
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.gestione_corsi_container);

        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.tab_crea_corso)).setIndicator(getResources().getString(R.string.tab_crea_corso)), CreaCorso.class, null);
        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.tab_modifica_corso)).setIndicator(getResources().getString(R.string.tab_modifica_corso)), ModificaCorso.class, null);
        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.tab_elimina_corso)).setIndicator(getResources().getString(R.string.tab_elimina_corso)), EliminaCorso.class, null);

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
