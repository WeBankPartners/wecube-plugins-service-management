package com.webank.servicemanagement.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.common.base.Strings;

public class DateUtils {

    public static java.util.Date convertToTimestamp(String value) throws Exception {
        if (Strings.isNullOrEmpty(value))
            return null;

        String timestampPattern = "yyyy-MM-dd HH:mm:ss";
        String datePattern = "yyyy-MM-dd";

        String parsePattern = null;
        java.util.Date date = null;
        if (value.length() == timestampPattern.length()) {
            parsePattern = timestampPattern;
        } else if (value.length() == datePattern.length()) {
            parsePattern = datePattern;
        }

        if (!Strings.isNullOrEmpty(parsePattern)) {
            SimpleDateFormat dateFmt = new SimpleDateFormat(parsePattern);
            dateFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                date = dateFmt.parse(value);
            } catch (ParseException e) {
                throw new Exception(String.format("Failed to parse date string [%s].", value), e);
            }
        } else {
            throw new Exception("Only support 'yyyy-MM-dd HH:mm:ss' and 'yyyy-MM-dd' for datetime.");
        }

        return date;
    }

    public static String formatDateToString(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getDefault());
        return df.format(date);
    }

    public static Date formatStringToDate(String dateString) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.parse(dateString);
    }

    public static String formatDurationToReadableValue(long originalSecond) {
        String DateTimes = null;
        long days = originalSecond / (60 * 60 * 24);
        long hours = (originalSecond % (60 * 60 * 24)) / (60 * 60);
        long minutes = (originalSecond % (60 * 60)) / 60;
        long seconds = originalSecond % 60;

        if (days > 0) {
            DateTimes = days + " Days " + hours + " Hours " + minutes + " Minutes " + seconds + " Seconds";
        } else if (hours > 0) {
            DateTimes = hours + " Hours " + minutes + " Minutes " + seconds + " Seconds";
        } else if (minutes > 0) {
            DateTimes = minutes + " Minutes " + seconds + " Seconds";
        } else {
            DateTimes = seconds + " Seconds";
        }
        return DateTimes;
    }

    // "3d2h5m" -> 3 days 2 hours 5 minutes -> returns 4445
    public static long parseDurationToMinutes(String duration) {
        int dayIndex = duration.indexOf("d");
        int hourIndex = duration.indexOf("h");
        int minuteIndex = duration.indexOf("m");
        String dayString = duration.substring(0, dayIndex);
        String hourString = duration.substring(dayIndex + 1, hourIndex);
        String minuteString = duration.substring(hourIndex + 1, minuteIndex);

        return (long) Integer.valueOf(dayString) * 24 * 60 + Integer.valueOf(hourString) * 60
                + Integer.valueOf(minuteString);
    }
}
