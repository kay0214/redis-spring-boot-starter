/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.redis.actuator;

import com.personal.redis.utils.RedisInfoUtils;
import com.personal.redis.utils.RedisUtils;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author sunpeikai
 * @version RedisHealthIndicator, v0.1 2020/9/24 17:45
 * @description redis健康检查
 */
@Component("redis")
public class RedisHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();
        if(RedisUtils.isRun()){
            // 把redis系统信息放入
            RedisInfoUtils.systemInfo().forEach(builder::withDetail);
            return builder.up().build();
        }else{
            return builder.down().build();
        }
    }
}
