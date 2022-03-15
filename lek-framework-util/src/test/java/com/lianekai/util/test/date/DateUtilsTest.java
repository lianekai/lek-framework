package com.lianekai.util.test.date;

import com.lianekai.util.date.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Date;

/**
 * 日期工具类测试
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/16 00:04
 */
@Slf4j
public class DateUtilsTest {

    @Test
    public void testGetYearMonthDay(){
        Date date=new Date();
        Integer year=DateUtils.getYear(date);
        log.info("当前的年份是："+year+"年");
        Integer month=DateUtils.getMonth(date);
        log.info("当前的月份是："+month+"月");
        Integer day=DateUtils.getDay(date);
        log.info("当前的日数是："+day+"日");
        Integer hour=DateUtils.getHour(date);
        log.info("当前的时间是（12小时制）："+hour+"小时");
        Integer hourOfDay=DateUtils.getHourOfDay(date);
        log.info("当前的时间是（24小时制）："+hourOfDay+"小时");
    }

}
