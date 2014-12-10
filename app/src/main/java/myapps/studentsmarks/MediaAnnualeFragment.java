package myapps.studentsmarks;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Gio on 10.12.2014.
 */
public class MediaAnnualeFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public MediaAnnualeFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MediaAnnualeFragment newInstance(int sectionNumber) {
        MediaAnnualeFragment fragment = new MediaAnnualeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_media_annuale, container, false);

        return rootView;
    }
}
