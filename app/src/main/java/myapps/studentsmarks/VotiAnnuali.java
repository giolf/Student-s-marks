package myapps.studentsmarks;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        return rootView;
    }
}