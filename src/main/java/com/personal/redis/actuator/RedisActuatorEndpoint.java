/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.redis.actuator;

import com.personal.redis.utils.RedisInfoUtils;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;

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
        return RedisInfoUtils.healthInfo();
    }
}
