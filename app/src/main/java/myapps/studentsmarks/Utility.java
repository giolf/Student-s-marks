package myapps.studentsmarks;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by Gio on 10.11.2014.
 */
public class Utility {

    public static void showJustYear(DatePicker picker) {
        try {
            Field f[] = picker.getClass().getDeclaredFields();
            for (Field field : f) {
                if (field.getName().equals("mDaySpinner") || field.getName().equals("mMonthSpinner")) {
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

    public static void showJustDay(DatePicker picker) {
        try {
            Field f[] = picker.getClass().getDeclaredFields();
            for (Field field : f) {
                if (field.getName().equals("mYearSpinner") || field.getName().equals("mMonthSpinner")) {
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

    public static TextView customTitleDialog(StudentMarks activity, int title) {
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
    public static String monthFromIntToString(StudentMarks activity, int month) {
        return activity.getResources().getStringArray(R.array.mesi)[month];
    }
}
