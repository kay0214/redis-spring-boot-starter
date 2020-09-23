/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.redis.actuator;

import com.personal.redis.utils.SpringUtils;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunpeikai
 * @version RedisActuatorEndpoint, v0.1 2020/9/18 09:47
 * @description
 */
@WebEndpoint(id = "redis")
public class RedisActuatorEndpoint {

    @ReadOperation
    public Map<String, Object> redisActuator() {
        // 方法返回map
        Map<String, Object> result = new HashMap<>();
        // redis节点数量
        int size = 0;
        // redis.info缓存信息列表
        List<Object> jedisPoolList = new ArrayList<>();

        // redis信息
        Map<String, JedisPool> jedisPools = SpringUtils.getBeanOfType(JedisPool.class);
        size += jedisPools.size();
        jedisPools.keySet().forEach(beanName -> {
            Map<String, Object> jedisProperties = new HashMap<>();
            JedisPool jedisPool = jedisPools.get(beanName);
            jedisProperties.put("beanName", beanName);
            jedisProperties.put("maxBorrowWaitTimeMillis", jedisPool.getMaxBorrowWaitTimeMillis());
            jedisProperties.put("meanBorrowWaitTimeMillis", jedisPool.getMeanBorrowWaitTimeMillis());
            jedisProperties.put("numActive", jedisPool.getNumActive());
            jedisProperties.put("numIdle", jedisPool.getNumIdle());
            jedisProperties.put("numWaiters", jedisPool.getNumWaiters());
            jedisPoolList.add(jedisProperties);
        });

        result.put("size", size);
        result.put("info", jedisPoolList);
        return result;
    }
}
