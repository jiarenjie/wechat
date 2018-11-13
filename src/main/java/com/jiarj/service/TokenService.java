package com.jiarj.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiarj.entity.SingleAccessToken;
import com.jiarj.utils.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by jiarj on 2018-11-13.
 */
@Service(value = "tokenService")
public class TokenService {

    private static Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

    @Value("${wechat.appid}")
    private String appid;
    @Value("${wechat.secret}")
    private String secret;

    public void getToken(){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String temp = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%1$s&secret=%2$s";
//            String str = restTemplate.getForObject(String.format(temp,appid,secret), String.class);
            String str = RestUtils.get(String.format(temp,appid,secret));
            ObjectMapper objectMapper = new ObjectMapper();
            Map map = objectMapper.readValue(str, Map.class);
            LOGGER.debug("token = "+map.toString());
            if(map.get("access_token")!=null){
                SingleAccessToken instance = SingleAccessToken.getInstance();
                instance.setAccesstoken(String.valueOf(map.get("access_token")));
            }else{
                LOGGER.error("get token fail:"+ map.toString());
            }

        }catch (Exception e){
            LOGGER.error("get token fail:"+ e.getMessage());
        }

    }
}
