/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.redis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sunpeikai
 * @version JedisProperties, v0.1 2020/9/21 17:02
 * @description
 */
@ConfigurationProperties(prefix = "redis")
public class JedisProperties {
    private boolean enable;
    private String ip;
    private Integer port;
    private int timeout = 2000;
    private String password;
    private int db = 0;
    private String clientName;
    private JedisPoolProperties pool;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public JedisPoolProperties getPool() {
        return pool;
    }

    public void setPool(JedisPoolProperties pool) {
        this.pool = pool;
    }
}
