package com.ggu.gguri;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {

    public final String getCurTime(String dateType) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        String curYears, curMonths, curDays, curHours, curMinutes,
            curYear, curMonth, curDay, curHour, curMinute;

        curYears = new SimpleDateFormat("yyyy").format(date);
        curMonths = new SimpleDateFormat("MM").format(date);
        curDays = new SimpleDateFormat("dd").format(date);
        curYear = new SimpleDateFormat("yy").format(date);
        curMonth = new SimpleDateFormat("M").format(date);
        curDay = new SimpleDateFormat("d").format(date);

        curHours = new SimpleDateFormat("HH").format(date);
        curMinutes = new SimpleDateFormat("mm").format(date);
        curHour = new SimpleDateFormat("H").format(date);
        curMinute = new SimpleDateFormat("m").format(date);

        switch(dateType) {
            case "YYYY":
                return curYears;
            case "yy":
                return curYear;
            case "MM":
                return curMonths;
            case "M":
                return curMonth;
            case "dd":
                return curDays;
            case "d":
                return curDay;
            case "HH":
                return curHours;
            case "mm":
                return curMinutes;
            case "H":
                return curHour;
            case "m":
                return curMinute;
            default:
                return null;
        }
    }

    public final void setText(final Activity activity, final TextView text, final String value) {
        if(activity == null)
            return;

        activity.runOnUiThread(() -> {
            if(value == null)
                text.setText(R.string.error);
            else
                text.setText(value);
        });
    }

    public final void showAlert(Context context, String title, String msg, Boolean toast) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        if(!toast) {
            builder.setNegativeButton("아니오", (dialog, which) -> {

            });
        }
        builder.setPositiveButton("예", (dialog, which) -> {

        });
        builder.show();

    }

    // 날짜 한국어로 변경
    public final String transKorWeek() {
        String[] week = {"일", "월", "화", "수", "목", "금", "토"};

        Calendar cal = Calendar.getInstance();

        return week[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }

}
