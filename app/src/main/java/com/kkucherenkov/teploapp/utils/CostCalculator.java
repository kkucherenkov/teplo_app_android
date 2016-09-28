package com.kkucherenkov.teploapp.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kirillkucherenkov on 28/09/2016.
 */

public class CostCalculator {
    private static final int STOP_HOUR = 3;
    private static final int AM_RUB_PER_MIN = 1;
    private static final int PM_RUB_PER_MIN = 2;
    private static final int MAX_PM_COST = STOP_HOUR * PM_RUB_PER_MIN * 60;
    private static final int MAX_AM_COST = STOP_HOUR * AM_RUB_PER_MIN * 60;

    public static int getTotalCost(Date startDate, Date endDate) {
        long startMillis = startDate.getTime() / (1000 * 60);
        long endMillis = endDate.getTime() / (1000 * 60);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 14);

        int totalMins = (int) (endMillis - startMillis);
        int totalCost = 0;
        if (startCalendar.getTime().getTime() > calendar.getTime().getTime()) { // start after mid of day
            totalCost = Math.min(MAX_PM_COST, totalMins * PM_RUB_PER_MIN);
        } else if (endCalendar.getTime().getTime() < calendar.getTime().getTime()) { // end before mid of day
            totalCost = Math.min(MAX_AM_COST, totalMins * AM_RUB_PER_MIN);
        } else {
            endCalendar.setTime(startDate);
            endCalendar.add(Calendar.HOUR_OF_DAY, 3);
            int amMinutes = (int) ((calendar.getTime().getTime() - startCalendar.getTime().getTime()) / (1000 * 60));
            int pmMinutes = (int) ((endCalendar.getTime().getTime() - calendar.getTime().getTime()) / (1000 * 60));
            totalCost = amMinutes * AM_RUB_PER_MIN + pmMinutes * PM_RUB_PER_MIN;
        }

        return totalCost;

    }
}