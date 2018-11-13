package com.jiarj.entity;

/**
 * Created by jiarj on 2018-11-13.
 * 单例类，用于存放全局变量accessToken
 */
public class SingleAccessToken {
    private static SingleAccessToken instance;
    private String accesstoken;
    private SingleAccessToken(){

    }

    public static SingleAccessToken getInstance(){
        if(instance == null){
            instance = new SingleAccessToken();
        }
        return instance;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
