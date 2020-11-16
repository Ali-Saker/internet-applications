package com.internetapplications.helper;

public class StringHelper {

    public static String getCode(String s) {
        if(s == null || s.isEmpty()) return null;

        return s.toLowerCase().replaceAll(" ", "_");
    }
}
