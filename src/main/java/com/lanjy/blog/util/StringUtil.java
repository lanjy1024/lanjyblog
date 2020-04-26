package com.lanjy.blog.util;


import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.util
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/2/1
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNull(String str) {
        return str == null || str.length() == 0 || str.equals("null");
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String[] trimNull(String[] str) {
        if (str == null)
            return null;
        for (int i = 0; i < str.length; i++) {
            if (StringUtil.isNull(str[i]))
                str[i] = "";
        }
        return str;
    }

    public static String trimNull(String str) {
        if (str == null)
            str = "";
        if (str.equals("null"))
            str = "";
        str = str.trim();
        return str;
    }

    public static String cutString(String str, int length) {
        if (str == null)
            return " ";
        if (length > 1 && length < str.length()) {
            // length--;
            // str = str.substring(0,length)+"...";
            str = str.substring(0, length);
        }
        return str;
    }

    public static String ISO2GBK(String str) {
        return changeEncode(str, "ISO-8859-1", "GBK");
    }

    public static String changeEncode(String str, String sourceEncode,
                                      String destEncode) {
        try {
            str = new String(str.getBytes(sourceEncode), destEncode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String escapeForSql(String str) {
        if (str == null)
            str = " ";
        if (str.equals("null"))
            str = " ";
        str = str.trim().replaceAll("'", "''");
        return str;
    }

    public static String escapeRegex(String original) {
        StringBuffer buffer = new StringBuffer(original.length());
        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);
            if (c == '\\' || c == '$') {
                buffer.append("\\").append(c);
            } else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    public static String escapeBackslash(String original) {
        StringBuffer buffer = new StringBuffer(original.length());
        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);
            if (c == '\\') {
                buffer.append(" ");
            } else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    public static int String2Int(String str) {
        if (str == null || "-".equals(str.trim())) {
            return 0;
        } else {
            int m;
            try {
                m = Integer.parseInt(str);
            } catch (Exception e) {
                m = 0;
            }
            return m;
        }
    }

    public static boolean isNotEmpty(String str) {
        if(str != null) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 将异常堆栈打印到一行
     * */
    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

}
