package com.hlwd.validation.regex;

public class RegexRepository {
    public static final String notNull = "\\S+";
    public static final String email = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
    public static final String integer = "^[-]{0,1}[0-9]{1,}$";
    public static final String positiveInt = "^[1-9]\\d*$";
    public static final String chs = "^[Α-￥]+$";
    public static final String chineseName = "^[Α-￥]{2,4}$";
    public static final String zipcode = "^[1-9]\\d{5}$";
    public static final String date = "^\\d{4}-\\d{2}-\\d{2}$";
    public static final String phone = "^1(3|5|7|8{1})\\d{9}$";

    public RegexRepository() {
    }
}