package myapps.studentsmarks;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Gio on 24.11.2014.
 */
public class GestioneAnniFragment extends Fragment {

    /*The fragment argument representing the section number for this fragment.*/
    private static final String ARG_SECTION_NUMBER = "section_number";

    /*Gestisce i fragments di Creazione, modifica ed eliminazione anno*/
    FragmentTabHost tabHost;

    /*Contiene la lista degli anni creati*/
    private static ArrayList<Anno> listaAnni;

    public GestioneAnniFragment() {
    }

    public static ArrayList<Anno> getListaAnni() {
        return listaAnni;
    }

    public static void setListaAnni(ArrayList<Anno> listaAnni) {
        GestioneAnniFragment.listaAnni = listaAnni;
    }

    public static boolean annoGiaEsistente(String annoSelezionato) {
        for (Anno annoCreato : listaAnni) {
            if (annoCreato.getNome().equals(annoSelezionato))
                return true;
        }
        return false;
    }

    /*Returns a new instance of this fragment for the given section number.*/
    public static GestioneAnniFragment newInstance(int sectionNumber) {
        GestioneAnniFragment fragment = new GestioneAnniFragment();
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

        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.tab_crea_anno)).setIndicator(getResources().getString(R.string.tab_crea_anno)), CreaAnnoFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.tab_modifica_anno)).setIndicator(getResources().getString(R.string.tab_modifica_anno)), ModificaAnnoFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.tab_elimina_anno)).setIndicator(getResources().getString(R.string.tab_elimina_anno)), EliminaAnnoFragment.class, null);

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
        ((StudentMarks) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}