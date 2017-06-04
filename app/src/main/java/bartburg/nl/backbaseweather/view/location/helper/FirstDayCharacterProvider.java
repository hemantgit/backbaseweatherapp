package bartburg.nl.backbaseweather.view.location.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Bart on 6/4/2017.
 */

public class FirstDayCharacterProvider {

    private static final DateFormat dateFormat = new SimpleDateFormat("EEEEE", Locale.ENGLISH);

    public static String getFirstDayCharacter(int daysFromNow) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.DATE, daysFromNow);
        dateFormat.setCalendar(gc);
        return dateFormat.format(gc.getTime());
    }

}
