package com.lianekai.util.date;

import cn.hutool.core.date.DateUtil;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 重写DateFormat 实现特俗的时间解析
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/16 01:25
 */
public class DateFormatUtils extends SimpleDateFormat {
    private DateFormat target;

    public DateFormatUtils(DateFormat dateFormat){this.target=dateFormat;}

    @Override
    public Date parse(String dateStr){
        try {
            return target.parse(dateStr);
        }catch (ParseException ex){
            return DateUtil.parseDate(dateStr);
        }
    }

    @Override
    public  StringBuffer format(Date date, StringBuffer toAppendTo,
                                FieldPosition fieldPosition){
        return target.format(date, toAppendTo, fieldPosition);
    }


    @Override
    public DateFormat clone() {
        DateFormatUtils other = (DateFormatUtils)super.clone();
        other.target = (DateFormat)this.target.clone();
        return other;
    }
}
