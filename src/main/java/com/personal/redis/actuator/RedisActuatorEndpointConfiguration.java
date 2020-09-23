/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.redis.actuator;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author sunpeikai
 * @version RedisActuatorEndpointConfiguration, v0.1 2020/9/18 09:46
 * @description
 */
@ConditionalOnClass({RedisActuatorEndpoint.class})
public class RedisActuatorEndpointConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnAvailableEndpoint
    public RedisActuatorEndpoint redisActuatorEndpoint(){
        return new RedisActuatorEndpoint();
    }
}
