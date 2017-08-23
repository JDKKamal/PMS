package com.jdkgroup.customviews.materialcalendar.Interface;

import com.jdkgroup.customviews.materialcalendar.DayView;
import com.jdkgroup.customviews.materialcalendar.Util.CalendarDay;

public interface DayViewDecorator {
    boolean shouldDecorate(CalendarDay day);

    void decorate(DayView view);
}
