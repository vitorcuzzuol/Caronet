package br.uff.caronet.util;

import android.content.Context;
import android.util.Log;
import android.util.TimeUtils;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utils{

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static Date intToDate (int year, int month, int day, int hour, int minutes){
        try {
            return new SimpleDateFormat("yyyy/MM/dd-HH:mm")
                    .parse(year+"/"+month+"/"+day+"-"+hour+":"+minutes);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Calendar.getInstance().getTime();
    }

    public static String dateToString (int month, int day, int hour, int minutes) {
        return day+"/"+month+" - "+hour+":"+minutes;
    }

    public static Long diffInMinutes(Date date) {

        return TimeUnit.MILLISECONDS.toMinutes(date.getTime() - Calendar.getInstance().getTime().getTime());
    }
}
