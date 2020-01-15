package com.webank.servicemanagement.utils;

import org.springframework.util.StringUtils;

public class FileNameUtils {

    public static String getFileName(String fullFileName) {
        String fileName = "";
        String[] strings = StringUtils.split(fullFileName, ".");
        if (strings.length < 2) {
            return fileName;
        }
        fileName = strings[0];
        for (int i = 1; i < strings.length - 1; i++) {
            fileName = fileName + strings[i];
        }
        return fileName;
    }
}
