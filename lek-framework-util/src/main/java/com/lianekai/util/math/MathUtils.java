package com.lianekai.util.math;

/**
 * MathUtils
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/20 13:34
 */
public class MathUtils {
    private MathUtils(){}

    private static final String BYTE="B";
    private static final String KB="KB";
    private static final String MB="MB";
    private static final String GB="GB";


    public static String countBitSize(int size) {

        StringBuilder strBuilder=new StringBuilder();
        //基本单位大小
        int unitSize = 1024;
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < unitSize) {
            return strBuilder.append(size).append(BYTE).toString() ;
        } else {
            size = size / unitSize;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < unitSize) {
            return strBuilder.append(size).append(KB).toString();
        } else {
            size = size / unitSize;
        }
        if (size < unitSize) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return strBuilder.append((size / 100)).append(".").append((size % 100)).append(MB).toString();

        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / unitSize;
            return strBuilder.append((size / 100)).append(".").append((size % 100)).append(GB).toString();
        }
    }
}
