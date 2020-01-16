package com.webank.servicemanagement.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.google.common.base.Strings;

public class DateUtils {

    public static java.util.Date convertToTimestamp(String value) throws Exception {
        if(Strings.isNullOrEmpty(value))
            return null;
        
        String timestampPattern =  "yyyy-MM-dd HH:mm:ss";
        String datePattern = "yyyy-MM-dd";

        String parsePattern=null;
        java.util.Date date = null;
        if(value.length() == timestampPattern.length()) {
            parsePattern = timestampPattern;
        }else if(value.length() == datePattern.length()) {
            parsePattern = datePattern;
        }
        
        if(!Strings.isNullOrEmpty(parsePattern)) {
            SimpleDateFormat dateFmt = new SimpleDateFormat(parsePattern);
            dateFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                date = dateFmt.parse(value);
            } catch (ParseException e) {
                throw new Exception(String.format("Failed to parse date string [%s].",value),e);
            }
        }else {
            throw new Exception("Only support 'yyyy-MM-dd HH:mm:ss' and 'yyyy-MM-dd' for datetime.");
        }
        
        return date;
    }
}
