package com.lianekai.util.math;

import com.lianekai.util.unique.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数工具类
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/20 13:52
 */
@Slf4j
public class RandomUtils {

    private RandomUtils() throws NoSuchAlgorithmException {}

    /*用于随机选的数字*/
    private static final String BASE_NUMBER = "0123456789";

    /*用于随机选的字符*/
    private static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";

    /* 用于随机选的字符和数字*/
    private static final String RANDOM_STRING_RANGE = "abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYXZ";
    private static final String RANDOM_NUM_RANGE = "123456789";
    private static final String RANDOM_STRING_AND_NUM_RANGE = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYXZ";
    private static final String BASE_CHAR_NUMBER = BASE_CHAR + BASE_NUMBER;

    /*随机策略*/
    private static final String RANDOM_STRING_AND_NUM_RANGE_STRATEGY = "RANDOM_STRING_AND_NUM_RANGE";
    private static final String RANDOM_STRING_RANGE_STRATEGY = "RANDOM_STRING_RANGE";
    private static final String RANDOM_NUM_RANGE_STRATEGY = "RANDOM_NUM_RANGE";

    /*随机策略枚举*/
    public enum RandomStrategy {

        RANDOM_STRING_AND_NUM_RANGE("RANDOM_STRING_AND_NUM_RANGE", "字母和数字的随机策略"),
        RANDOM_STRING_RANGE("RANDOM_STRING_RANGE", "纯字母组合策略"),
        RANDOM_NUM_RANGE("RANDOM_NUM_RANGE", "纯数字组合策略");


        RandomStrategy(String code, String description) {
            this.code = code;
            this.description = description;
        }

        String code;
        String description;

        public String getCode() {
            return code;
        }
    }

    /**
     * 通过策略生成随机code
     *
     * @param num
     * @return
     */
    public static String getRandomCode(int num, RandomStrategy randomStrategy) {
        switch (randomStrategy.getCode()) {
            case RANDOM_STRING_AND_NUM_RANGE_STRATEGY:
                return getRandomString(num, RANDOM_STRING_AND_NUM_RANGE);
            case RANDOM_STRING_RANGE_STRATEGY:
                return getRandomString(num, RANDOM_STRING_RANGE);
            case RANDOM_NUM_RANGE_STRATEGY:
                return getRandomString(num, RANDOM_NUM_RANGE);
            default:
                return getRandomString(num, RANDOM_STRING_AND_NUM_RANGE);
        }
    }

    /**
     * 生成随机指定位数的字母和数字组合
     *
     * @param num
     * @return
     */
    public static String getRandomCode(int num) {
        return getRandomString(num, RANDOM_STRING_AND_NUM_RANGE);
    }

    /**
     * 生成指定位数纯字母组合，区分大小写
     *
     * @param num
     * @return
     */
    public static String getRandomString(int num) {
        return getRandomString(num, RANDOM_STRING_RANGE);
    }

    /**
     * 生成指定位数纯数字组合
     *
     * @param num
     * @return
     */
    public static String getRandomNum(int num) {
        return getRandomString(num, RANDOM_NUM_RANGE);
    }

    /**
     * 随机生成固定7位密码，数字和字母组合
     *
     * @return
     */
    public static String getRandomPassword() {
        return getRandomString(3) + getRandomNum(4);
    }

    public static String getRandomString(int num, String range) {
        if (num < 1 || StringUtils.isBlank(range)) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        Random random = ThreadLocalRandom.current();
        int maxIndex = range.length();
        for (int i = 0; i < num; i++) {
            result.append(range.charAt(random.nextInt(maxIndex)));
        }
        return result.toString();
    }

    /**
     * getRandomCode
     *
     * @return
     */
    public static String getRandomCode() {
        return String.valueOf(SnowflakeIdWorker.nextId());
    }

    /**
     * getHexadecimalRandomCode
     *
     * @return
     */
    public static String getHexadecimalRandomCode() {
        return Long.toHexString(SnowflakeIdWorker.nextId()).toUpperCase();
    }

    /**
     * 蓄水池抽样算法，从k数组里面随机抽出m个数据，k的长度>m
     *
     * @param k
     * @param m
     * @return
     */
    public static int[] ReservoirSampling(int k[], int m) throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();
        // b为水池
        int b[] = new int[m];
        if (k.length <= m) {
            return new int[0];
        } else if (k.length > m) {
            for (int j = 0; j < k.length; j++) {
                if (j < m) {
                    // 将前m个数据存入数组
                    b[j] = k[j];
                } else if (j >= m) {
                    // 从0-j中随机出一个数
                    int r = random.nextInt(j + 1);
                    // 如果随机出的r<水池大小 ，则进行替换
                    if (r < m) {
                        b[r] = k[j];
                    }
                }
            }
        }
        return b;
    }
}
