package com.lianekai.util.unique;

import com.lianekai.util.system.SystemUtils;

/**
 * Twitter_Snowflake
 * SnowFlake的结构如下(每部分用-分开):
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0
 * 41位时间戳(毫秒级)，注意，41位时间戳不是存储当前时间的时间戳，而是存储时间戳的差值（当前时间戳 - 开始时间戳)
 * 得到的值），这里的的开始时间戳，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下面程序SnowflakeIdWorker类的startTime属性）。41位的时间戳，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间戳)产生4096个ID序号
 * 加起来刚好64位，为一个Long型。
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/20 14:10
 */
public class SnowflakeIdWorker {
    private SnowflakeIdWorker(){}

    private static final String WORKER_ID_EVN="snow_flake_workder_id";
    private static final String DATA_CENTER_ID_ENV="snow_flake_center_id";
    /** 开始时间戳 (2015-01-01) */
    private static final long TWEPOCH = 1420041600000L;

    /** 机器id所占的位数 */
    private static final long WORKER_ID_BITS = 5L;

    /** 数据标识id所占的位数 */
    private static  final long DATACENTER_ID_BITS = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     private static  final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);*/

    /** 支持的最大数据标识id，结果是31
     private static  final long MAX_DATACENTER_ID = -1L ^ (-1L << DATACENTER_ID_BITS);*/

    /** 序列在id中占的位数 */
    private static  final long SEQUENCE_BITS = 12L;

    /** 机器ID向左移12位 */
    private static  final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /** 数据标识id向左移17位(12+5) */
    private static  final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /** 时间戳向左移22位(5+5+12) */
    private static  final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private static  final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);

    /** 工作机器ID(0~31) */
    private static  long workerId;

    /** 数据中心ID(0~31) */
    private static  long datacenterId;

    /** 毫秒内序列(0~4095) */
    private  static long sequence = 0L;

    /** 上次生成ID的时间戳 */
    private  static long lastTimestamp = -1L;



    static {
        String wokerIdEnv= SystemUtils.getEnv(WORKER_ID_EVN,"1");
        String dataCenterIdEnv= SystemUtils.getEnv(DATA_CENTER_ID_ENV,"1");
        workerId=Long.parseLong(wokerIdEnv);
        datacenterId=Long.parseLong(dataCenterIdEnv);
    }

    // ==============================Methods==========================================
    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public static synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间戳
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT) //
                | (datacenterId << DATACENTER_ID_SHIFT) //
                | (workerId << WORKER_ID_SHIFT) //
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间戳
     * @return 当前时间戳
     */
    protected  static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    protected  static long timeGen() {
        return System.currentTimeMillis();
    }

    /** 测试
     public static void main(String[] args) {
     System.out.println("开始："+System.currentTimeMillis());
     //        SnowflakeIdWorker idWorker = new  static (0, 0);
     for (int i = 0; i < 50; i++) {
     long id = SnowflakeIdWorker.nextId();
     System.out.println(id);
     //            System.out.println(Long.toBinaryString(id));
     }
     System.out.println("结束："+System.currentTimeMillis());
     }
     */
}
