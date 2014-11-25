package myapps.studentsmarks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

import static myapps.studentsmarks.Utility.customTitleDialog;

/**
 * Created by Gio on 11.11.2014.
 */
public class EliminaAnnoFragment extends Fragment {
    /**
            * The fragment argument representing the section number for this
            * fragment.
    */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public EliminaAnnoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EliminaAnnoFragment newInstance(int sectionNumber) {
        EliminaAnnoFragment fragment = new EliminaAnnoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView           = inflater.inflate(R.layout.fragment_elimina_anno, container, false);
        final Button btnAnno    = (Button)rootView.findViewById(R.id.anno);
        final Button btnElimina = (Button)rootView.findViewById(R.id.salva);
        final TextView tvAnno   = (TextView)rootView.findViewById(R.id.tv_anno);




        return rootView;
    }
}
