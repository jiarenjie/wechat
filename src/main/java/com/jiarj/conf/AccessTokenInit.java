package com.jiarj.conf;

import com.jiarj.service.TokenService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by jiarj on 2018-11-13.
 * 微信项目自动获取token
 */
@Component
public class AccessTokenInit implements ApplicationRunner {

    @Resource(name = "tokenService")
    private TokenService ts;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        ts.getToken();
    }
}
