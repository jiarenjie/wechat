package com.jiarj.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jiarj on 2018-11-13.
 */
public class RestUtils {
    public static String post(String url,String str){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(str, headers);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(url, formEntity, String.class);
        return result;
    }

    public static String get(String url){
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }


}
