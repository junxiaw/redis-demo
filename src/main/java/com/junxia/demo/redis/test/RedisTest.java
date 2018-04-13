package com.junxia.demo.redis.test;

import com.junxia.demo.redis.RedisDao;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Resource
    private RedisDao redisDao;

    @Test
    public void redisTest() {
        String key = "name";
        String value = (String) redisDao.getValue("name");
        if (null == value) {
            value = "junxia";
            redisDao.setValue(key, value, 1 * 60);//缓存1分钟
//            redisDao.setValue(key, "junxia");
        }
        System.out.println(key + ": " + value);
    }

    @Test
    public void redisMapTest() {
        String map_key = "info_map";
        String cardNo = "10001";
        //读缓存
        if (!redisDao.exists(map_key)) {
            Map<String, String> map = new HashMap<String, String>() {{
                put("10001", "BANTEN23");
                put("10002", "TANGERANG");
            }};
            redisDao.saveRedisMap(map_key, map);
//            redisDao.saveRedisMaps(map_key, map, 1 * 60);
        }
        String name = redisDao.getSingleHash(map_key, cardNo);
        System.out.println("cardNo: " + cardNo + ", name: " + name);
    }
}
