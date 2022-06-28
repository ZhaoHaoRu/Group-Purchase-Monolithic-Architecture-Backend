package com.example.groupbuy.utils;



import java.net.ConnectException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class LocalDateTimeUtils {

    public enum LocalDateTimeFormat {
        //日期格式类型
        YYYY_MM_DD(DataConstant.YYYY_MM_DD),
        YYYYMMDD(DataConstant.YYYYMMDD),
        YYYY_MM_DD_HH_MM_SS(DataConstant.YYYY_MM_DD_HH_MM_SS),
        YYYY_MM_DD_HH_MM_SS2(DataConstant.YYYY_MM_DD_HH_MM_SS2),
        YYYY_MM_DD_HH_MM_SS_ZH(DataConstant.YYYY_MM_DD_HH_MM_SS_ZH);
        String localDateFormatName;

        LocalDateTimeFormat(String localDateTimeFormatName) {
            this.localDateFormatName = localDateTimeFormatName;
        }

        public String getLocalDateTimeFormatName() {
            return localDateFormatName;
        }
    }

    public enum LocalDateFormat {
        //日期格式类型
        YYYY_MM_DD(DataConstant.YYYY_MM_DD),
        YYYYMMDD(DataConstant.YYYYMMDD);
        String localDateTimeFormatName;

        LocalDateFormat(String localDateTimeFormatName) {
            this.localDateTimeFormatName = localDateTimeFormatName;
        }

        public String getLocalDateTimeFormatName() {
            return localDateTimeFormatName;
        }
    }

    public enum LocalDateTimeParse {
        //日期格式类型
        YYYY_MM_DD_HH_MM_SS(DataConstant.YYYY_MM_DD_HH_MM_SS),
        YYYY_MM_DD_HH_MM_SS_ZH(DataConstant.YYYY_MM_DD_HH_MM_SS_ZH);
        String localDateTimeParseName;

        LocalDateTimeParse(String localDateTimeParseName) {
            this.localDateTimeParseName = localDateTimeParseName;
        }

        public String getLocalDateTimeParseName() {
            return this.localDateTimeParseName;
        }
    }

    public enum DateTemplate {
        //日期格式
        WEEK_ZH(DataConstant.WEEK_ZH),
        WEEK_EN(DataConstant.WEEK_EN);
        String[] dateTemplateName;

        DateTemplate(String[] dateTemplateName) {
            this.dateTemplateName = dateTemplateName;
        }

        public String[] getDateTemplateName() {
            return this.dateTemplateName;
        }
    }

    /**
     * 转换
     *
     * @param date data
     * @return LocalDateTime
     */
    public LocalDateTime transformation(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 转换
     *
     * @param localDateTime localDateTime
     * @return date
     */
    public Date transformation(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 开始时间是否大于结束时间
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return true 开始时间大于结束时间 false 开始时间小于结束时间
     */
    public static boolean compare(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            return false;
        }
//        LocalDateTimeUtil.format()
        return startTime.isAfter(endTime);
    }

    /**
     * 获取当前系统时间
     *
     * @param format 时间格式
     * @return 文本时间
     */
    public static String getCurrentDateTime(LocalDateTimeFormat format) {
        Objects.requireNonNull(format, DataExceptionMessage.TIME_FORMAT_CHARACTER_CANNOT_BE_EMPTY);
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format.getLocalDateTimeFormatName()));
    }

    /**
     * 传入时间否在时间范围之类
     *
     * @param localDateTime 当前时间
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @return 状态 true 在区间之类 false 不在区间范围之类
     */
    public static boolean isInThisTime(LocalDateTime localDateTime, LocalDateTime startTime, LocalDateTime endTime) {
        Objects.requireNonNull(startTime, DataExceptionMessage.START_DATE_CANNOT_BE_BLANK);
        Objects.requireNonNull(endTime, DataExceptionMessage.END_TIME_CANNOT_BE_EMPTY);
        Objects.requireNonNull(localDateTime, DataExceptionMessage.DATE_CANNOT_BE_BLANK);
        return localDateTime.isAfter(startTime) && localDateTime.isBefore(endTime);
    }

    /**
     * LocalDateTime 转 文本
     *
     * @param localDateTime 时间
     * @param format        时间格式
     * @return 文本时间
     */
    public static String format(LocalDateTime localDateTime, LocalDateTimeFormat format) {
        Objects.requireNonNull(localDateTime, DataExceptionMessage.DATE_CANNOT_BE_BLANK);
        Objects.requireNonNull(format, DataExceptionMessage.TIME_FORMAT_CHARACTER_CANNOT_BE_EMPTY);
        return localDateTime.format(DateTimeFormatter.ofPattern(format.getLocalDateTimeFormatName()));
    }

    /**
     * localDate 转 文本
     *
     * @param localDate 时间
     * @param format    时间格式
     * @return 文本时间
     */
    public static String format(LocalDate localDate, LocalDateFormat format) {
        Objects.requireNonNull(localDate, DataExceptionMessage.DATE_CANNOT_BE_BLANK);
        Objects.requireNonNull(format, DataExceptionMessage.TIME_FORMAT_CHARACTER_CANNOT_BE_EMPTY);
        return localDate.format(DateTimeFormatter.ofPattern(format.getLocalDateTimeFormatName()));
    }

    /**
     * 文本 转 localDate
     *
     * @param localDate 时间
     * @param format    时间格式
     * @return 文本时间
     */
    public static LocalDate format(String localDate, LocalDateFormat format) {
        Objects.requireNonNull(localDate, DataExceptionMessage.DATE_CANNOT_BE_BLANK);
        Objects.requireNonNull(format, DataExceptionMessage.TIME_FORMAT_CHARACTER_CANNOT_BE_EMPTY);
        return LocalDate.parse(localDate, DateTimeFormatter.ofPattern(format.getLocalDateTimeFormatName()));
    }

    /**
     * 文本 转 LocalDateTime
     *
     * @param localDateTime 时间
     * @param parse         时间格式
     * @return LocalDateTime
     */
    public static LocalDateTime format(String localDateTime, LocalDateTimeParse parse) {
        Objects.requireNonNull(localDateTime, DataExceptionMessage.DATE_CANNOT_BE_BLANK);
        Objects.requireNonNull(parse, DataExceptionMessage.TIME_FORMAT_CHARACTER_CANNOT_BE_EMPTY);
        return LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern(parse.getLocalDateTimeParseName()));
    }



    /**
     * 获取周几
     *
     * @param localDateTime 当前时间
     * @param dateTemplate  模板
     * @return 周几
     */
    public static String getWeek(LocalDateTime localDateTime, DateTemplate dateTemplate) {
        Objects.requireNonNull(localDateTime, DataExceptionMessage.DATE_CANNOT_BE_BLANK);
        String[] dateTemplateName = dateTemplate.getDateTemplateName();
        int dayForWeek = localDateTime.getDayOfWeek().getValue();
        return dateTemplateName[dayForWeek - 1];
    }

    /**
     * 获取周几
     *
     * @param localDateTime 当前时间
     * @return 周几
     */
    public static String getWeek(LocalDateTime localDateTime) {
        Objects.requireNonNull(localDateTime, DataExceptionMessage.DATE_CANNOT_BE_BLANK);
        String[] dateTemplateName = DateTemplate.WEEK_ZH.getDateTemplateName();
        int dayForWeek = localDateTime.getDayOfWeek().getValue();
        return dateTemplateName[dayForWeek - 1];
    }

    public static void main(String[] args) {
        String s="2021-01-02 10:10:10";
        LocalDateTime format = LocalDateTimeUtils.format(s, LocalDateTimeParse.YYYY_MM_DD_HH_MM_SS);
        System.out.println(format);
    }

}
