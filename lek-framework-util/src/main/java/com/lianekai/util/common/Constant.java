package com.lianekai.util.common;

/**
 * 常量类
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/18 22:21
 */
public class Constant {
    private Constant(){}

    public  static final String COMMA_EN=",";
    public  static final String POINT_EN=".";
    public  static final String LEFT_SLASH="/";
    public  static final String RIGHT_SLASH="\\";
    public static final String DASH = "-";
    public  static final char POINT_EN_CHAR='.';
    public  static final char LEFT_SLASH_CHAR='/';
    public  static final char RIGHT_SLASH_CHAR='\\';

    public  static final String EMPTY_STRING="";


    /**insert操作，bean copy时需要忽略的字段*/
    public static final String[] INSERT_IGNORE_FIELD = {"id", "createdTime", "createdBy", "updatedTime", "updatedBy", "isDeleted", "bid"};
    /**update操作，bean copy时需要忽略的字段*/
    public static final String[] UPDATE_IGNORE_FIELD = {"id", "createdTime", "createdBy", "updatedTime", "updatedBy", "isDeleted"};
}
