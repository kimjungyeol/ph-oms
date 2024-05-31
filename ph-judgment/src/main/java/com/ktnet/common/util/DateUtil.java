package com.ktnet.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

public class DateUtil {

    private static final int MS = 1000;

    public static final int SUN = 1;
    public static final int MON = 2;
    public static final int TUE = 3;
    public static final int WED = 4;
    public static final int THU = 5;
    public static final int FRI = 6;
    public static final int SAT = 7;

    public static final int YEAR = 1;
    public static final int MONTH = 2;
    public static final int WEEK = 3;
    public static final int DAY = 4;

    private static final String DATE_REGEX = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";

    private static final DateTimeFormatter[] DATE_FORMATS = { DateTimeFormatter.ofPattern("yyyyMMdd"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"), DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("M-d-yy"), };
    private static final DateTimeFormatter STRING_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    public final static String getFirstOfMonth(String date) {
        return date.substring(0, 7) + "-01";
    }

    public final static String getFirstOfWeek(String date) throws ParseException {
        Date orginal = DateUtil.parseDate(date, "yyyyMMdd");
        int dayOfWeek = DateUtil.getDayOfWeek(orginal);
        Date result = DateUtil.addDay(orginal, SUN - dayOfWeek);
        return DateUtil.parseString(result, "yyyyMMdd");
    }

    public final static String getLastOfWeek(String date) throws ParseException {
        Date orginal = DateUtil.parseDate(date, "yyyyMMdd");
        int dayOfWeek = DateUtil.getDayOfWeek(orginal);
        Date result = DateUtil.addDay(orginal, SAT - dayOfWeek);
        return DateUtil.parseString(result, "yyyyMMdd");
    }

    public final static String getYear(Date date) throws Exception {
        FastDateFormat fdf = FastDateFormat.getInstance("yyyy");
        return fdf.format(date);
    }

    public final static String getMonth(Date date) throws Exception {
        FastDateFormat fdf = FastDateFormat.getInstance("MM");
        return fdf.format(date);
    }

    public final static String getDay(Date date) throws Exception {
        FastDateFormat fdf = FastDateFormat.getInstance("dd");
        return fdf.format(date);
    }

    public final static String getLastOfMonth(String dateStr) throws ParseException {
        Calendar base = Calendar.getInstance();
        base.setTime(parseDate(dateStr, "yyyyMMdd"));
        int maxday = base.getActualMaximum(Calendar.DATE);

        dateStr = dateStr.substring(0, 6) + maxday;
        return dateStr;
    }

    public static String parseString(Date date, String format, Locale locale) {
        if (date == null) {
            return "";
        }
        FastDateFormat fdf = FastDateFormat.getInstance(format, locale);
        return fdf.format(date);
    }

    public final static String parseString(Date date, String format) {
        if (date == null) {
            return "";
        }
        FastDateFormat fdf = FastDateFormat.getInstance(format);
        return fdf.format(date);
    }

    public final static String parseString(String date, String format) throws ParseException {
        if (date == null) {
            return "";
        }
        FastDateFormat fdf = FastDateFormat.getInstance(format);
        return fdf.format(parseDate(date, "yyyyMMdd"));
    }

    public final static Date parseDate(String date, String format) throws ParseException {
        if (date == null || "".equals(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(date);
    }

    public final static int diffDays(Date date1, Date date2) {
        return (int) (Math.abs(date1.getTime() - date2.getTime()) / MS);
    }

    public final static Date addTimes(Date date, int times, int base) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(base, times);

        return cal.getTime();
    }

    public final static Date addHours(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);

        return cal.getTime();
    }

    public final static Date addMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);

        return cal.getTime();
    }

    public final static Date addSecond(Date date, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, second);

        return cal.getTime();
    }

    public final static Date addDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);

        return cal.getTime();
    }

    public final static Date addMonth(Date date, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);

        return cal.getTime();
    }

    public final static Date addYear(Date date, int year) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, year);

        return cal.getTime();
    }

    public final static boolean equals(Date date1, Date date2, String format) {
        String currentTime1 = parseString(date1, format);
        String currentTime2 = parseString(date2, format);
        return currentTime1.equals(currentTime2);
    }

    public final static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    public final static Date getDateFromDayOfWeek(Date date, int dayOfWeek) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        return cal.getTime();
    }

    public final static boolean isDayOfWeek(Date date, int dayOfWeek) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == dayOfWeek;
    }

    public final static String minutesToHour(Long minute) {
        int hours = (int) (minute / 60);
        int minutes = (int) (minute % 60);

        if (minute == 0) {
            return "00:00";
        } else {
            return String.format("%02d:%02d", Math.abs(hours), Math.abs(minutes));
        }
    }

    public final static LocalDateTime parseDateTime(String date, String format) {
        if (date == null || "".equals(date)) {
            return null;
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(format);
        DateTimeFormatter dateFormater = new DateTimeFormatterBuilder().append(dateFormat)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0).parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0).toFormatter();

        return LocalDateTime.parse(date, dateFormater);
    }

    public final static LocalDateTime minDateTime(String yyyyMMdd) {
        LocalDate ld = null;
        if (StringUtils.isBlank(yyyyMMdd)) {
            ld = LocalDate.now();
        } else {
            ld = LocalDate.parse(yyyyMMdd, DateTimeFormatter.ofPattern("yyyyMMdd"));
        }
        LocalDateTime ldt = LocalDateTime.of(ld, LocalDateTime.MIN.toLocalTime());
        return ldt;
    }

    public final static LocalDateTime maxDateTime(String yyyyMMdd) {
        LocalDate ld = null;
        if (StringUtils.isBlank(yyyyMMdd)) {
            ld = LocalDate.now();
        } else {
            ld = LocalDate.parse(yyyyMMdd, DateTimeFormatter.ofPattern("yyyyMMdd"));
        }
        LocalDateTime ldt = LocalDateTime.of(ld, LocalDateTime.MAX.toLocalTime());
        return ldt;
    }

    public final static String getWeekNm(int dayOfWeek) {
        switch (dayOfWeek) {
        case DateUtil.SUN:
            return "(일)";
        case DateUtil.MON:
            return "(월)";
        case DateUtil.TUE:
            return "(화)";
        case DateUtil.WED:
            return "(수)";
        case DateUtil.THU:
            return "(목)";
        case DateUtil.FRI:
            return "(금)";
        case DateUtil.SAT:
            return "(토)";
        }
        return "";
    }

    public static LocalDate toLocalDate(String date) {
        return toLocalDate(date, "yyyyMMdd");
    }

    public static LocalDate toLocalDate(String date, String pattern) {
        if (StringUtils.isNotBlank(date)) {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
        }
        return LocalDate.now();
    }

    public static String now() {
        return toLocalDateString(LocalDate.now(), "yyyyMMdd");
    }

    public static String nowtime() {
        return toLocalDateString(LocalDateTime.now(), "yyyyMMddHHmmss");
    }

    public static String toLocalDateString(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String toLocalDateString(LocalDateTime date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime minLocalDateTime(String date) {
        return LocalDateTime.of(toLocalDate(date), LocalDateTime.MIN.toLocalTime());
    }

    public static LocalDateTime maxLocalDateTime(String date) {
        return LocalDateTime.of(toLocalDate(date), LocalDateTime.MAX.toLocalTime());
    }

    public static LocalDateTime toLocalDateTime(String date, DateTimeFormatter formatter) {
        if (StringUtils.isNotBlank(date)) {
            return LocalDateTime.parse(date, formatter);
        }
        LocalDate localDate = toLocalDate(date);
        return LocalDateTime.of(localDate, LocalDateTime.MIN.toLocalTime());
    }

//    public static String convert(String date) {
//      if (StringUtils.isBlank(date)) {
//        return null;
//      }
//      // 슬러시, . 등 대시로 변환
//      date = date.replaceAll(DATE_REGEX, "-");
//      if (date.length() == 6) {
//        // 6자리 날짜는 01일로 변환
//        date = date + "01";
//      }
//      LocalDate parseDate = null;
//      String parseString = null;
//      for (DateTimeFormatter dtf : DATE_FORMATS) {
//        try {
//          parseDate = LocalDate.parse(date, dtf);
//          parseString = parseDate.format(STRING_FORMAT);
//          if (parseString.startsWith("20")) {
//            //log.debug(">>> date [" + date + "] " + dtf.toString());
//            return parseString;
//          }
//        } catch (Exception e) {
//        }
//      }
//      throw new ValidationException("excelUpload.validation.date", null);
//    }
}
