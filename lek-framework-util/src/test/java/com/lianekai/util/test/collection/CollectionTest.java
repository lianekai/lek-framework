package com.lianekai.util.test.collection;

import com.lianekai.util.collection.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;


/**
 * CollectionTest
 *
 * @author lianekai
 * @version: 1.0
 * @date 2021/12/12 00:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest

public class CollectionTest {

    @Test
    public void testCollection(){
        Map map=new HashMap();
        boolean isEmpty=CollectionUtils.isEmpty(map);


    }


}
