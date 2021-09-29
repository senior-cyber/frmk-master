package com.senior.cyber.frmk.otp.internal;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Clock {

    private final int interval;
    private Calendar calendar;

    public Clock() {
        interval = 30;
    }

    public Clock(int interval) {
        this.interval = interval;
    }

    public long getCurrentInterval() {
        this.calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
        long currentTimeSeconds = calendar.getTimeInMillis() / 1000;
        return currentTimeSeconds / interval;
    }
}