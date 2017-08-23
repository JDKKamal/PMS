package com.jdkgroup.customviews.materialcalendar.Interface;

import com.jdkgroup.customviews.materialcalendar.Util.CalendarDay;

import java.util.ArrayList;
import java.util.Date;

public interface CalendarCallback {
    Date getDateSelected();

    ArrayList<CalendarDay> getEvents();

    boolean getIndicatorsVisible();
}
