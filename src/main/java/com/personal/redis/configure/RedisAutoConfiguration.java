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
        Assert.isTrue(!StringUtils.isEmpty(jedisProperties.getIp()), "ip can't be empty.");
        Assert.isTrue(!StringUtils.isEmpty(jedisProperties.getPort()), "port can't be empty.");
        // 获取连接池配置
        JedisPoolProperties poolProperties = jedisProperties.getPool();
        // 连接池配置定义
        JedisPoolConfig poolConfig = null;
        if(poolProperties != null){
            poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(poolProperties.getMaxActive());
            poolConfig.setMaxIdle(poolProperties.getMaxIdle());
            poolConfig.setMaxWaitMillis(poolProperties.getMaxWait());
            poolConfig.setTestOnBorrow(poolProperties.isTestOnBorrow());
            poolConfig.setTestOnReturn(poolProperties.isTestOnReturn());
        }

        if(jedisProperties.getTimeout() == 0
                && StringUtils.isEmpty(jedisProperties.getPassword())
                && jedisProperties.getDb() == null
                && StringUtils.isEmpty(jedisProperties.getClientName())){
            // ip, port有参数
            log.info("redis init ok. ip[{}], port[{}]", jedisProperties.getIp(), jedisProperties.getPort());
            return poolConfig == null ? new JedisPool(jedisProperties.getIp(), jedisProperties.getPort()) : new JedisPool(poolConfig, jedisProperties.getIp(), jedisProperties.getPort());
        }else if(jedisProperties.getTimeout() > 0
                && StringUtils.isEmpty(jedisProperties.getPassword())
                && jedisProperties.getDb() == null
                && StringUtils.isEmpty(jedisProperties.getClientName())){
            // ip, port, timeout有参数
            log.info("redis init ok. ip[{}], port[{}], timeout[{}]", jedisProperties.getIp(), jedisProperties.getPort(), jedisProperties.getTimeout());
            return poolConfig == null ? new JedisPool(new JedisPoolConfig(), jedisProperties.getIp(), jedisProperties.getPort(), jedisProperties.getTimeout())
                    : new JedisPool(poolConfig, jedisProperties.getIp(), jedisProperties.getPort(), jedisProperties.getTimeout());
        }else if(jedisProperties.getTimeout() > 0
                && !StringUtils.isEmpty(jedisProperties.getPassword())
                && jedisProperties.getDb() == null
                && StringUtils.isEmpty(jedisProperties.getClientName())){
            // ip, port, timeout, password有参数
            log.info("redis init ok. ip[{}], port[{}], timeout[{}], password[{}]", jedisProperties.getIp(), jedisProperties.getPort(), jedisProperties.getTimeout(), jedisProperties.getPassword());
            return poolConfig == null ? new JedisPool(new JedisPoolConfig(), jedisProperties.getIp(), jedisProperties.getPort(), jedisProperties.getTimeout(), jedisProperties.getPassword())
                    : new JedisPool(poolConfig, jedisProperties.getIp(), jedisProperties.getPort(), jedisProperties.getTimeout(), jedisProperties.getPassword());
        }else if(jedisProperties.getTimeout() > 0
                && !StringUtils.isEmpty(jedisProperties.getPassword())
                && jedisProperties.getDb() != null
                && StringUtils.isEmpty(jedisProperties.getClientName())){
            // ip, port, timeout, password, db有参数
            log.info("redis init ok. ip[{}], port[{}], timeout[{}], password[{}], db[{}]", jedisProperties.getIp(), jedisProperties.getPort(), jedisProperties.getTimeout(), jedisProperties.getPassword(), jedisProperties.getDb());
            return poolConfig == null ? new JedisPool(new JedisPoolConfig(), jedisProperties.getIp(), jedisProperties.getPort(), jedisProperties.getTimeout(), jedisProperties.getPassword(), jedisProperties.getDb())
                    : new JedisPool(poolConfig, jedisProperties.getIp(), jedisProperties.getPort(), jedisProperties.getTimeout(), jedisProperties.getPassword(), jedisProperties.getDb());
        }else if(jedisProperties.getTimeout() > 0
                && !StringUtils.isEmpty(jedisProperties.getPassword())
                && jedisProperties.getDb() != null
                && !StringUtils.isEmpty(jedisProperties.getClientName())){
            // ip, port, timeout, password, db, clientName有参数
            log.info("redis init ok. ip[{}], port[{}], timeout[{}], password[{}], db[{}], clientName[{}]", jedisProperties.getIp(), jedisProperties.getPort(), jedisProperties.getTimeout(), jedisProperties.getPassword(), jedisProperties.getDb(), jedisProperties.getClientName());
            return poolConfig == null ? new JedisPool(new JedisPoolConfig(), jedisProperties.getIp(), jedisProperties.getPort(), jedisProperties.getTimeout(), jedisProperties.getPassword(), jedisProperties.getDb(), jedisProperties.getClientName())
                    : new JedisPool(poolConfig, jedisProperties.getIp(), jedisProperties.getPort(), jedisProperties.getTimeout(), jedisProperties.getPassword(), jedisProperties.getDb(), jedisProperties.getClientName());
        }else{
            Assert.isTrue(true, "redis init fail");
            return null;
        }
    }
}
