package com.lianekai.util.test.batch;

import com.google.common.collect.Lists;
import com.lianekai.util.batch.BatchExecutor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 批量执行器测试
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/15 00:07
 */
@Slf4j
public class BatchExecutorTest {
    //推荐使用java.security.SecureRandom代替Random 不要需要一个随机数就new一个Random对象浪费资源
    //1.
    // 现在所谓的随机函数Random都是伪随机，不是真正意义上的随机，他们都是使用一个初始值（比如：当前时间），然后把初始值放入随机算法生成随机数。
    //2.如果每次都new Random()可能会影响随机性
    private final Random random = SecureRandom.getInstanceStrong();

    public BatchExecutorTest() throws NoSuchAlgorithmException {
    }

    @Test
    public void testDefaultBatchExecutor(){
        List<Integer> list = Lists.newArrayList();
        //预期执行总数
        int count = random.nextInt(2000);
        for (int i = 0; i < count; i++) {
            list.add(i);
        }
        //预期执行次数
        int expectExecuteTimes = count%200==0?count/200:count/200+1;

        //实际执行次数和总数，lambda不能直接引用基本类型，用list代替
        List<Integer> actualExecuteTimes = new ArrayList<>();
        List<Integer> actualExecuteCount=new ArrayList<>();

        int i=1;
        BatchExecutor.execute(list, list1 -> {
            actualExecuteCount.add(list1.size());
            actualExecuteTimes.add(1);
        });

        //声明预期情况和实际情况相同
//        Assertions.assertEquals(count,sum(actualExecuteCount));
//        Assertions.assertEquals(expectExecuteTimes,sum(actualExecuteTimes));
    }

    @Test
    public void testBatchExecutor(){
        List<Integer> list = Lists.newArrayList();
        //预期执行总数
        int count = random.nextInt(2000);
        for (int i = 0; i < count; i++) {
            list.add(i);
        }
        //每批执行的数量
        int batch = 500;
        //预期执行次数
        int expectExecuteTimes = count%batch==0?count/batch:count/batch+1;

        //实际执行次数和总数，lambda不能直接引用基本类型，用list代替
        List<Integer> actualExecuteTimes = new ArrayList<>();
        List<Integer> actualExecuteCount=new ArrayList<>();

        BatchExecutor.execute(list, batch, (list1) -> {
            actualExecuteCount.add(list1.size());
            actualExecuteTimes.add(1);
        });

        //声明预期情况和实际情况相同
//        Assertions.assertEquals(count,sum(actualExecuteCount));
//        Assertions.assertEquals(expectExecuteTimes,sum(actualExecuteTimes));
    }

    //List<Integer>中所有元素相加求和
    private int sum(List<Integer> list){
        return list.stream().mapToInt(Integer::intValue).sum();
    }

}
