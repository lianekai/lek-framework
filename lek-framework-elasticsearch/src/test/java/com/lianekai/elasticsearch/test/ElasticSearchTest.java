package com.lianekai.elasticsearch.test;

import org.elasticsearch.client.RestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ElasticSearchTest
 *
 * @author lianekai
 * @version: 1.0
 * @date 2021/12/13 01:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticSearchTest {
    @Autowired
    private RestClient restClient;

    @Test
    public void contentLoads(){
        System.out.println(restClient);
    }
}
