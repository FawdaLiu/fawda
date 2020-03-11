package fawda.util;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public enum DF {
    RFC3339("RFC3339", "yyyy-MM-dd'T'HH:mm:ss"), RFC3339_("RFC3339_", "yyyy-MM-dd HH:mm:ss"), FMS("日期加毫秒级时间", "yyyyMMddHHmmssSSS"), FS("日期加秒级时间",
            "yyyyMMddHHmmss"), yyyy$MM$dd("减号连接模式", "yyyy-MM-dd"), XX("斜线模式", "M/d/yy"),

    FULL("本地全格式", 0), LONG("本地长格式", 1), MEDIUM("本地中格式", 2), SHORT("本地短格式", 3),

    yyyy_MM_dd("下划线连接模式", "yyyy_MM_dd"), yyyyMMdd("无连接模式", "yyyyMMdd"),

    yyyyMM("年份月份", "yyyyMM"), yyyy("年份", "yyyy"), MM("月份", "MM"), dd("日期", "dd"), HMS("时分秒", "HHmmss");

    private String description;
    private String patten;
    private SimpleDateFormat format;

    private DF(String description, int style) {
        this.description = description;
        this.format = ((SimpleDateFormat) DateFormat.getDateInstance(style));
        this.patten = this.format.toPattern();
    }

    private DF(String description, String patten) {
        this.description = description;
        this.patten = patten;
        this.format = new SimpleDateFormat(patten);
    }

    public String getPatten() {
        return this.patten;
    }

    public String getDescription() {
        return this.description;
    }

    public String format(Date date) {
        synchronized (this.format) {
            return this.format.format(date);
        }
    }

    public Date parse(String date) {
        synchronized (this.format) {
            return this.format.parse(date, new ParsePosition(0));
        }
    }

    public static Date parseCheck(String date) {
        return parseCheck(date, values());
    }

    public static Date parseCheck(String date, DF[] dfs) {
        Date result = null;
        for (DF df : dfs) {
            result = df.parse(date);
            if (result != null) {
                break;
            }
        }
        return result;
    }

    public static String format(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public String toString() {
        return this.toString();
    }

    public static void main(String[] args) {
        DF[] dfs = values();
        Date now = new Date();
        for (DF df : dfs) {
            System.out.println(df.ordinal());
            System.out.println(df.format(now));
        }

        System.out.println(parseCheck("2012-03-18 06:33"));
    }
}