package com.bluexiii.jwh.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class StringUtil {

    /**
     * 格式化时间
     *
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        return (date == null) ? "" : sdf.format(date);
    }

    /**
     * 格式化Object[]
     *
     * @param obj
     * @return
     */
    public static Object[] convertObjects(Object[] obj) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int j = 0; j < obj.length; j++) {
            if (obj[j] == null) {
                obj[j] = "";
            } else if (obj[j] instanceof java.sql.Timestamp) {
                obj[j] = sdf.format(obj[j]).toString();
            } else {
                obj[j] = obj[j].toString().trim();
            }
        }
        return obj;
    }

    /**
     * 格式化Object[]
     *
     * @param obj
     * @return
     */
    public static Object convertObjectToInt(Object obj) {
        Object o;
        if (obj.toString().length() == 0) {
            o = new String("0");

        } else {
            o = obj;
        }
        return o;
    }

    /**
     * 格式化str
     *
     * @param str
     * @return
     */
    public static String convertNullStr(String str) {

        if (str == null || str.equalsIgnoreCase("null")) {
            str = "";
        }
        return str;
    }

    public static String getSequence() {
        UUID uuid = UUID.randomUUID();
        String sequence = uuid.toString();
        return sequence;
    }

    public static String getBornDate(String cardNum) {
        if (cardNum == null || cardNum.length() != 18) {
            return "";
        }
        cardNum = cardNum.substring(6, 14);
        return cardNum;
    }

}
