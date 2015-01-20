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
 * Created by Gio on 10.12.2014.
 */
public class GestioneRiepilogoAnnuale extends Fragment {
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
    public static GestioneRiepilogoAnnuale newInstance(int sectionNumber) {
        GestioneRiepilogoAnnuale fragment = new GestioneRiepilogoAnnuale();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public GestioneRiepilogoAnnuale() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View rootView = inflater.inflate(R.layout.fragment_student_marks, container, false);
        tabHost = new FragmentTabHost(getActivity());
        tabHost.setup(getActivity(), getChildFragmentManager(), R.id.riepilogo_annuale_container);

        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.tab_media_annuale)).setIndicator(getResources().getString(R.string.tab_media_annuale)), MediaAnnuale.class, null);
        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.tab_corsi_annuali)).setIndicator(getResources().getString(R.string.tab_corsi_annuali)), CorsiAnnuali.class, null);
        tabHost.addTab(tabHost.newTabSpec(getResources().getString(R.string.tab_voti_annuali)).setIndicator(getResources().getString(R.string.tab_voti_annuali)), VotiAnnuali.class, null);

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
