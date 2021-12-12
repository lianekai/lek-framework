package com.lianekai.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ElasticSearchConfig
 *
 * @author lianekai
 * @version: 1.0
 * @date 2021/12/13 00:47
 */
@Configuration
public class ElasticSearchConfig {

      /**这种是没有添加安全校验的*/
//    @Bean
//    public RestClient restClient(){
//        RestClient restClient = RestClient.builder(
//                new HttpHost("http://81.71.135.210/", 9200, "http")).build();
//        return restClient;
//    }





}
