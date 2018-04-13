package com.junxia.demo.redis;

import javax.annotation.Resource;

public class Test {

    @Resource
    private RedisDao redisDao;


    public void setData(){
        redisDao.setValue("name", "junxia");
    }
}
