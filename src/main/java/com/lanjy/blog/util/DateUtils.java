package com.lanjy.blog.util;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author：lanjy
 * @date：2020/9/13
 * @description：
 */
public class DateUtils {


    public static final String DEFAULT_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";// 时间格式
    public static final String DEFAULT_FORMATS = "yyyy-MM-dd";
    public static final String DATE_FOMATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_FOMATE_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FOMATE_YYYY = "yyyy";



    public static String formatyyyy(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FOMATE_YYYY);
        return sdf.format(date);
    }
    /**
     * 格式化时间(Date 转换成String)
     *
     * @param date
     *            时间
     * @param format
     *            时间格式 如
     * @return 字符串
     */
    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String formatyyyyMMdd(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FOMATE_YYYYMMDD);
        return sdf.format(date);
    }

    public static String formatyyyy_MM_dd_hh_mm_ss(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_YYYY_MM_DD_HH_MM_SS);
        return sdf.format(date);
    }
    /**
     * 字符串格式化为时间
     *
     * @param dateStr
     *            时间字符串
     * @param format
     *            时间格式 如
     * @return
     */
    public static Date parseDate(String dateStr, String format) {
        Date date = null;
        if (!StringUtils.isEmpty(dateStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e) {

            }
        }
        return date;
    }


    /**
     * 字符串格式化为时间
     *
     * @param dateStr
     *            时间字符串
     * @param format
     *            时间格式 如
     * @return
     */
    public static String parseString(String dateStr, String format) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (!StringUtils.isEmpty(dateStr)) {
            sdf = new SimpleDateFormat(format);
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e) {

            }
        }

        return sdf.format(date);
    }

}
