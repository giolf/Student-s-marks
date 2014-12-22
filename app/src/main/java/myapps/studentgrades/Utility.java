package myapps.studentgrades;

import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import java.lang.reflect.Field;
import java.util.HashMap;

import static myapps.studentgrades.DataSource.getAnno;
import static myapps.studentgrades.DataSource.getListaAnni;
import static myapps.studentgrades.DataSource.getNomeAnnoSelezionato;
import static myapps.studentgrades.DataSource.getNomeUltimoAnnoCreato;
import static myapps.studentgrades.DataSource.setNomeAnnoSelezionato;

/**
 * Created by Gio on 10.11.2014.
 */
public abstract class Utility {

    public static void setupMenuRiepilogoAnnuale(Menu menu, Fragment fragment) {

        /*INIZIO impostazione bottone del selettore dell'anno scolastico*/
        // se l'utente non ha creato nessun anno, per default visualizzo un '-'
        if ( getListaAnni().size() == 0 )
            menu.findItem(R.id.action_selected_year).setTitle(fragment.getResources().getString(R.string.action_selected_year));

        // se l'utente ha almeno 1 anno creato, MA non ha ancora selezionato un anno per il quale
        // visualizzarne le relative statistiche, per default imposto l'ultimo anno creato
        else if ( getListaAnni().size() > 0 && getNomeAnnoSelezionato() == null ) {
            String nomeUltimoAnnoCreato = getNomeUltimoAnnoCreato();
            setNomeAnnoSelezionato(nomeUltimoAnnoCreato);
            menu.findItem(R.id.action_selected_year).setTitle(nomeUltimoAnnoCreato);
        }

        // se l'utente ha almeno 1 anno creato e ha selezionato un anno per il quale
        // visualizzarne le relative statistiche, imposto la selezione fatta dall utente
        else if ( getListaAnni().size() > 0 && getNomeAnnoSelezionato() != null ) {
            //controllo se l'anno selezionato esiste ancora (non è stato rimosso)
            Anno annoSelezionato = getAnno(getNomeAnnoSelezionato());
            if ( annoSelezionato != null )
                menu.findItem(R.id.action_selected_year).setTitle(getNomeAnnoSelezionato());
            else {
                //l'anno selezionato non c'è piu (quindi è stato eliminato), imposto quindi l'ultimo anno creato
                String NomeultimoAnnoCreato = getNomeUltimoAnnoCreato();
                setNomeAnnoSelezionato(NomeultimoAnnoCreato);
                menu.findItem(R.id.action_selected_year).setTitle(NomeultimoAnnoCreato);
            }
        }
        /*FINE impostazione bottone del selettore dell'anno scolastico*/
    }

    public static void showJustDayAndMonth(DatePicker picker) {
        try {
            Field f[] = picker.getClass().getDeclaredFields();
            for (Field field : f) {
                if (field.getName().equals("mYearSpinner")) {
                    field.setAccessible(true);
                    Object yearPicker = new Object();
                    yearPicker = field.get(picker);
                    ((View) yearPicker).setVisibility(View.GONE);
                }
            }
        }
        catch (SecurityException e) {
            Log.d("ERROR", e.getMessage());
        }
        catch (IllegalArgumentException e) {
            Log.d("ERROR", e.getMessage());
        }
        catch (IllegalAccessException e) {
            Log.d("ERROR", e.getMessage());
        }
    }

    public static TextView customTitleDialog(StudentGrades activity, int title) {
        //setup del titolo
        TextView tv = new TextView(activity);
        tv.setText(title);
        tv.setPaddingRelative(0, 70, 0, 70);
        tv.setTextSize(20);
        tv.setTextColor(0xff33b5e5);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    //forse in futuro avrà piu senso inserirlo nella classe voto
    public static String monthFromIntToString(StudentGrades activity, int month) {
        return activity.getResources().getStringArray(R.array.mesi)[month];
    }

    public static String[] makeSchoolYearList(int currentYear) {
        String[] schoolYearList = new String[11];
        int index = 0;
        for(int year=currentYear-10; year<=currentYear; year++, index++)
            schoolYearList[index] = ""+year+"-"+(year+1);
        return schoolYearList;
    }

    public static FrameLayout makeFrameLWithNumPicker(NumberPicker picker, StudentGrades activity) {
        final FrameLayout parent = new FrameLayout(activity);
        parent.addView(picker, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));
        return parent;
    }

    public static int[] convertiData(String data, StudentGrades activity) {
        String[] mesi = activity.getResources().getStringArray(R.array.mesi);
        HashMap<String, Integer> mappaMesi = new HashMap<String, Integer>();
        mappaMesi.put(mesi[0], 1);
        mappaMesi.put(mesi[1], 2);
        mappaMesi.put(mesi[2], 3);
        mappaMesi.put(mesi[3], 4);
        mappaMesi.put(mesi[4], 5);
        mappaMesi.put(mesi[5], 6);
        mappaMesi.put(mesi[6], 7);
        mappaMesi.put(mesi[7], 8);
        mappaMesi.put(mesi[8], 9);
        mappaMesi.put(mesi[9], 10);
        mappaMesi.put(mesi[10], 11);
        mappaMesi.put(mesi[11], 12);

        int[] dataNumerica = new int[2];
        String buffer = new String();
        short numSpazi = 1;

        for (int i = 0; i<data.length(); i++) {
            if (data.charAt(i) != ' ' && data.length() - i > 1) {
                buffer += data.charAt(i);
                continue;
            }
            else {
                if (numSpazi == 1) {
                    dataNumerica[0] = Integer.parseInt(buffer);
                    buffer = new String();
                    numSpazi++;
                    continue;
                }
                else if (numSpazi == 2) {
                    buffer += data.charAt(i);
                    dataNumerica[1] = mappaMesi.get(buffer);
                    break;
                }
            }
        }
        return dataNumerica;
    }
}
