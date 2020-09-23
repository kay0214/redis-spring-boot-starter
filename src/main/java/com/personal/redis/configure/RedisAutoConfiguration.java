/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.redis.configure;

import com.personal.redis.properties.JedisPoolProperties;
import com.personal.redis.properties.JedisProperties;
import com.personal.redis.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author sunpeikai
 * @version RedisAutoConfiguration, v0.1 2020/9/21 16:44
 * @description
 */
@Configuration
@Import({SpringUtils.class})
@ConditionalOnClass({JedisPool.class})
@EnableConfigurationProperties({JedisProperties.class,JedisPoolProperties.class})
@ConditionalOnProperty(name = {"redis.enable"}, havingValue = "true")
public class RedisAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(RedisAutoConfiguration.class);
    private final JedisProperties jedisProperties;

    public RedisAutoConfiguration(JedisProperties jedisProperties) {
        this.jedisProperties = jedisProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = {"redis.enable"}, havingValue = "true")
    public JedisPool jedisPool(){
        Assert.isTrue(!StringUtils.isEmpty(this.jedisProperties.getIp()), "ip can't be empty.");
        Assert.isTrue(!StringUtils.isEmpty(this.jedisProperties.getPort()), "port can't be empty.");
        // 获取连接池配置
        JedisPoolProperties poolProperties = jedisProperties.getPool();
        log.info("redis init ok");
        if(poolProperties == null){
            return new JedisPool(this.jedisProperties.getIp(), this.jedisProperties.getPort());
        }else{
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(poolProperties.getMaxActive());
            poolConfig.setMaxIdle(poolProperties.getMaxIdle());
            poolConfig.setMaxWaitMillis(poolProperties.getMaxWait());
            poolConfig.setTestOnBorrow(poolProperties.isTestOnBorrow());
            poolConfig.setTestOnReturn(poolProperties.isTestOnReturn());
            return new JedisPool(poolConfig, this.jedisProperties.getIp(), this.jedisProperties.getPort(), 100000, this.jedisProperties.getPassword(), this.jedisProperties.getDb());
        }

    }
}
