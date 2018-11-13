package com.jiarj.conf;

import com.jiarj.service.TokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * Created by jiarj on 2018-11-13.
 * 定时获取accessToken
 */
@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Resource(name = "tokenService")
    private TokenService ts;

//    每一小时刷新一次token
    @Scheduled(cron = "0 0 */1 * * ?")
    private void flushToken(){
        ts.getToken();
    }

}
