# oss-spring-boot-starter
## 目标:提供redis自动装配
### 配置
redis:  
  &emsp;enable: true  
  &emsp;ip: 47.105.221.190  
  &emsp;port: 6379  
  &emsp;#超时时间,可省略,默认为2000毫秒  
  &emsp;timeout: 2000  
  &emsp;#没有密码可不填  
  &emsp;password: IUIODU  
  &emsp;#数据库索引,可省略,默认为0  
  &emsp;db: 0  
  &emsp;#客户端名称,可省略,用于actuator显示  
  &emsp;client-name: test-redis  
  &emsp;pool:  
    &emsp;&emsp;max-active: 1024  
    &emsp;&emsp;max-idle: 200  
    &emsp;&emsp;max-wait: 10000  
    &emsp;&emsp;max-total: 1000  
    &emsp;&emsp;test-on-borrow: true  
    &emsp;&emsp;test-on-return: true
    
### 使用 : 除以下方法外,其余方法使用与jedis一致
```
// 单服务使用时,设置值
Boolean singleLockSet(String key, String value);
// 单服务使用时,设置值 - tryTimeoutSeconds重试时间
Boolean singleLockSet(String key, String value, int tryTimeoutSeconds);
// 单服务使用时,设置值
Boolean singleLockSet(String key, String value, long expireSeconds);
// 单服务使用时,设置值 - tryTimeoutSeconds重试时间
Boolean singleLockSet(String key, String value, long expireSeconds, long tryTimeoutSeconds);
// 获取分布式锁
Boolean getLock(String key, String requestId, int expireSeconds);
// 释放分布式锁
Boolean releaseLock(String key, String requestId);
```