package com.personal.redis.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.*;
import redis.clients.jedis.params.SetParams;

import java.util.*;

/**
 * @author sunpeikai
 * @version RedisActuatorEndpoint, v0.1 2020/9/18 09:47
 * @description redis工具类
 */
public class RedisUtils {

    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);

    private static JedisPool pool = null;

    private static String OK = "OK";

    //**********          redis连接池操作的方法          **********//

    /**
     * redis连接池
     * @return redis连接池
     */
    public static JedisPool getPool() {
        if (pool == null) {
            pool = SpringUtils.getBean(JedisPool.class);
        }
        return pool;
    }

    /**
     * 从redis连接池中获取redis
     * @return jedis
     */
    public static Jedis getJedis() {
        return getPool().getResource();
    }

    /**
     * 关闭redis
     * @param redis redis实例
     * @return void
     */
    public static void close(Jedis redis) {
        if (redis != null) {
            try {
                redis.close();
            } catch (Exception e) {
                log.error("redis close fail ==> ", e);
            }
        }
    }

    //**********          redis获取系统信息的方法          **********//
    /**
     * 获取系统信息
     * @return 系统信息
     */
    public static String info() {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.info();
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    //**********          redis字符串数据操作的方法          **********//

    /**
     * 设置给定key的值
     * 如果key已经存储其他值,覆写旧值,且无视类型
     * @param key redis键
     * @param value redis值
     * @return 2.6.12以前版本,SET命令总是返回OK;2.6.12版本开始,SET在设置操作成功完成时,才返回OK
     */
    public static String set(String key, String value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 设置给定key的值
     * 如果key已经存储其他值,覆写旧值,且无视类型
     * @param condition 满足这个条件才去set
     * @param key redis键
     * @param value redis值
     * @return 2.6.12以前版本,SET命令总是返回OK;2.6.12版本开始,SET在设置操作成功完成时,才返回OK
     */
    public static String set(boolean condition, String key, String value) {
        if(condition){
            return set(key, value);
        }
        return null;
    }

    /**
     * 设置给定key的值
     * 如果key已经存储其他值,覆写旧值,且无视类型
     * @param key redis键
     * @param value redis值
     * @return 2.6.12以前版本,SET命令总是返回OK;2.6.12版本开始,SET在设置操作成功完成时,才返回OK
     */
    public static String set(byte[] key, byte[] value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 设置给定key的值
     * 如果key已经存储其他值,覆写旧值,且无视类型
     * @param condition 满足这个条件才去set
     * @param key redis键
     * @param value redis值
     * @return 2.6.12以前版本,SET命令总是返回OK;2.6.12版本开始,SET在设置操作成功完成时,才返回OK
     */
    public static String set(boolean condition, byte[] key, byte[] value) {
        if(condition){
            return set(key, value);
        }
        return null;
    }

    /**
     * 设置给定key的值
     * 如果key已经存储其他值,覆写旧值,且无视类型
     * @param key redis键
     * @param value redis值
     * @param expireSeconds 过期时间 - 秒
     * @return 2.6.12以前版本,SET命令总是返回OK;2.6.12版本开始,SET在设置操作成功完成时,才返回OK
     */
    public static String set(String key, String value, int expireSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.set(key, value);
            // 设置键的过期时间
            expire(key, expireSeconds);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 设置给定key的值
     * 如果key已经存储其他值,覆写旧值,且无视类型
     * @param condition 满足这个条件才去set
     * @param key redis键
     * @param value redis值
     * @param expireSeconds 过期时间 - 秒
     * @return 2.6.12以前版本,SET命令总是返回OK;2.6.12版本开始,SET在设置操作成功完成时,才返回OK
     */
    public static String set(boolean condition, String key, String value, int expireSeconds) {
        if(condition){
            return set(key, value, expireSeconds);
        }
        return null;
    }

    /**
     * 设置给定key的值
     * 如果key已经存储其他值,覆写旧值,且无视类型
     * @param key redis键
     * @param value redis值
     * @param expireSeconds 过期时间 - 秒
     * @return 2.6.12以前版本,SET命令总是返回OK;2.6.12版本开始,SET在设置操作成功完成时,才返回OK
     */
    public static String set(String key, String value, long expireSeconds) {
        return set(key, value, new Long(expireSeconds).intValue());
    }

    /**
     * 设置给定key的值
     * 如果key已经存储其他值,覆写旧值,且无视类型
     * @param condition 满足这个条件才去set
     * @param key redis键
     * @param value redis值
     * @param expireSeconds 过期时间 - 秒
     * @return 2.6.12以前版本,SET命令总是返回OK;2.6.12版本开始,SET在设置操作成功完成时,才返回OK
     */
    public static String set(boolean condition, String key, String value, long expireSeconds) {
        if(condition){
            return set(key, value, expireSeconds);
        }
        return null;
    }

    /**
     * 设置给定key的值
     * 如果key已经存储其他值,覆写旧值,且无视类型
     * @param key redis键
     * @param value redis值
     * @return 2.6.12以前版本,SET命令总是返回OK;2.6.12版本开始,SET在设置操作成功完成时,才返回OK
     */
    public static String setObj(String key, Object value) {
        return set(key, JSON.toJSONString(value));
    }

    /**
     * 设置给定key的值
     * 如果key已经存储其他值,覆写旧值,且无视类型
     * @param condition 满足这个条件才去setObj
     * @param key redis键
     * @param value redis值
     * @return 2.6.12以前版本,SET命令总是返回OK;2.6.12版本开始,SET在设置操作成功完成时,才返回OK
     */
    public static String setObj(boolean condition, String key, Object value) {
        if(condition){
            return setObj(key, value);
        }
        return null;
    }

    /**
     * 设置给定key的值
     * 如果key已经存储其他值,覆写旧值,且无视类型
     * @param key redis键
     * @param value redis值
     * @param expireSeconds 过期时间 - 秒
     * @return 2.6.12以前版本,SET命令总是返回OK;2.6.12版本开始,SET在设置操作成功完成时,才返回OK
     */
    public static String setObj(String key, Object value, int expireSeconds) {
        return set(key, JSON.toJSONString(value), expireSeconds);
    }

    /**
     * 设置给定key的值
     * 如果key已经存储其他值,覆写旧值,且无视类型
     * @param condition 满足这个条件才去setObj
     * @param key redis键
     * @param value redis值
     * @param expireSeconds 过期时间 - 秒
     * @return 2.6.12以前版本,SET命令总是返回OK;2.6.12版本开始,SET在设置操作成功完成时,才返回OK
     */
    public static String setObj(boolean condition, String key, Object value, int expireSeconds) {
        if(condition){
            return setObj(key, value, expireSeconds);
        }
        return null;
    }

    /**
     * 设置给定key的值
     * 如果key已经存储其他值,覆写旧值,且无视类型
     * @param key redis键
     * @param value redis值
     * @param expireSeconds 过期时间 - 秒
     * @return 2.6.12以前版本,SET命令总是返回OK;2.6.12版本开始,SET在设置操作成功完成时,才返回OK
     */
    public static String setObj(String key, Object value, long expireSeconds) {
        return setObj(key, value, new Long(expireSeconds).intValue());
    }

    /**
     * 设置给定key的值
     * 如果key已经存储其他值,覆写旧值,且无视类型
     * @param condition 满足这个条件才去setObj
     * @param key redis键
     * @param value redis值
     * @param expireSeconds 过期时间 - 秒
     * @return 2.6.12以前版本,SET命令总是返回OK;2.6.12版本开始,SET在设置操作成功完成时,才返回OK
     */
    public static String setObj(boolean condition, String key, Object value, long expireSeconds) {
        if(condition){
            return setObj(key, value, expireSeconds);
        }
        return null;
    }

    /**
     * 获取指定key的值
     * @param key redis键
     * @return 返回key的值,如果key不存在时,返回nil;如果key不是字符串类型,那么返回一个错误
     */
    public static String get(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.get(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 获取指定key的值
     * @param key redis键
     * @return 返回key的值,如果key不存在时,返回nil;如果key不是字符串类型,那么返回一个错误
     */
    public static byte[] get(byte[] key) {
        byte[] value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.get(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 获取指定key的值
     * @param key redis键
     * @param clz 实体类class
     * @return 返回key的值,如果key不存在时,返回nil;如果key不是字符串类型,那么返回一个错误
     */
    public static <T> T getObj(String key, Class<T> clz) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String value = jedis.get(key);
            return JSON.parseObject(value, clz);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return null;
    }

    /**
     * 获取存储在指定key中字符串的子字符串
     * 字符串的截取范围由startOffset和endOffset两个偏移量决定(包括startOffset和endOffset在内)
     * @param key redis键
     * @param startOffset 偏移开始
     * @param endOffset 偏移结束
     * @return 截取得到的子字符串
     */
    public static String getRange(String key, long startOffset, long endOffset) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.getrange(key, startOffset, endOffset);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 获取存储在指定key中字符串的子字符串
     * 字符串的截取范围由startOffset和endOffset两个偏移量决定(包括startOffset和endOffset在内)
     * @param key redis键
     * @param startOffset 偏移开始
     * @param endOffset 偏移结束
     * @return 截取得到的子字符串
     */
    public static byte[] getRange(byte[] key, long startOffset, long endOffset) {
        byte[] value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.getrange(key, startOffset, endOffset);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 设置指定key的值,并返回key的旧值
     * @param key redis键
     * @param value redis值
     * @return 给定key的旧值.当key没有旧值时,即key不存在时,返回nil;当key存在但不是字符串类型时,返回一个错误
     */
    public static String getSet(String key, String value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.getSet(key, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 设置指定key的值,并返回key的旧值
     * @param condition 满足这个条件才去getSet
     * @param key redis键
     * @param value redis值
     * @return 给定key的旧值.当key没有旧值时,即key不存在时,返回nil;当key存在但不是字符串类型时,返回一个错误
     */
    public static String getSet(boolean condition, String key, String value) {
        if(condition){
            return getSet(key, value);
        }
        return null;
    }

    /**
     * 设置指定key的值,并返回key的旧值
     * @param key redis键
     * @param value redis值
     * @return 给定key的旧值.当key没有旧值时,即key不存在时,返回nil;当key存在但不是字符串类型时,返回一个错误
     */
    public static byte[] getSet(byte[] key, byte[] value) {
        byte[] result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.getSet(key, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 设置指定key的值,并返回key的旧值
     * @param condition 满足这个条件才去getSet
     * @param key redis键
     * @param value redis值
     * @return 给定key的旧值.当key没有旧值时,即key不存在时,返回nil;当key存在但不是字符串类型时,返回一个错误
     */
    public static byte[] getSet(boolean condition, byte[] key, byte[] value) {
        if(condition){
            return getSet(key, value);
        }
        return null;
    }

    /**
     * 对key所储存的字符串值,获取指定偏移量上的位(bit)
     * @param key redis键
     * @param offset 偏移量
     * @return 指定偏移量上的位.当偏移量offset比字符串值的长度大,或者key不存在时,返回0
     */
    public static Boolean getbit(String key, long offset) {
        Boolean result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.getbit(key, offset);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 对 key 所储存的字符串值,获取指定偏移量上的位(bit)
     * @param key redis键
     * @param offset 偏移量
     * @return 指定偏移量上的位.当偏移量offset比字符串值的长度大,或者key不存在时,返回0
     */
    public static Boolean getbit(byte[] key, long offset) {
        Boolean result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.getbit(key, offset);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取所有(一个或多个)给定key的值
     * 如果给定的key里面,有某个key不存在,那么这个key返回特殊值nil
     * @param keys redis键
     * @return 一个包含所有给定key的值的列表
     */
    public static List<String> mget(String ... keys) {
        List<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.mget(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取所有(一个或多个)给定key的值
     * 如果给定的key里面,有某个key不存在,那么这个key返回特殊值nil
     * @param keys redis键
     * @return 一个包含所有给定key的值的列表
     */
    public static List<byte[]> mget(byte[] ... keys) {
        List<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.mget(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 对key所储存的字符串值,设置或清除指定偏移量上的位(bit)
     * @param key redis键
     * @param offset 偏移量
     * @param value redis的值
     * @return 指定偏移量原来储存的位
     */
    public static Boolean setbit(String key, long offset, String value) {
        Boolean result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.setbit(key, offset, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 对key所储存的字符串值,设置或清除指定偏移量上的位(bit)
     * @param condition 满足这个条件才setbit
     * @param key redis键
     * @param offset 偏移量
     * @param value redis的值
     * @return 指定偏移量原来储存的位
     */
    public static Boolean setbit(boolean condition, String key, long offset, String value) {
        if(condition){
            return setbit(key, offset, value);
        }
        return null;
    }

    /**
     * 对key所储存的字符串值,设置或清除指定偏移量上的位(bit)
     * @param key redis键
     * @param offset 偏移量
     * @param value redis的值
     * @return 指定偏移量原来储存的位
     */
    public static Boolean setbit(String key, long offset, boolean value) {
        Boolean result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.setbit(key, offset, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 对key所储存的字符串值,设置或清除指定偏移量上的位(bit)
     * @param condition 满足这个条件才setbit
     * @param key redis键
     * @param offset 偏移量
     * @param value redis的值
     * @return 指定偏移量原来储存的位
     */
    public static Boolean setbit(boolean condition, String key, long offset, boolean value) {
        if(condition){
            return setbit(key, offset, value);
        }
        return null;
    }

    /**
     * 对key所储存的字符串值,设置或清除指定偏移量上的位(bit)
     * @param key redis键
     * @param offset 偏移量
     * @param value redis的值
     * @return 指定偏移量原来储存的位
     */
    public static Boolean setbit(byte[] key, long offset, byte[] value) {
        Boolean result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.setbit(key, offset, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 对key所储存的字符串值,设置或清除指定偏移量上的位(bit)
     * @param condition 满足这个条件才setbit
     * @param key redis键
     * @param offset 偏移量
     * @param value redis的值
     * @return 指定偏移量原来储存的位
     */
    public static Boolean setbit(boolean condition, byte[] key, long offset, byte[] value) {
        if(condition){
            return setbit(key, offset, value);
        }
        return null;
    }

    /**
     * 对key所储存的字符串值,设置或清除指定偏移量上的位(bit)
     * @param key redis键
     * @param offset 偏移量
     * @param value redis的值
     * @return 指定偏移量原来储存的位
     */
    public static Boolean setbit(byte[] key, long offset, boolean value) {
        Boolean result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.setbit(key, offset, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 对key所储存的字符串值,设置或清除指定偏移量上的位(bit)
     * @param condition 满足这个条件才setbit
     * @param key redis键
     * @param offset 偏移量
     * @param value redis的值
     * @return 指定偏移量原来储存的位
     */
    public static Boolean setbit(boolean condition, byte[] key, long offset, boolean value) {
        if(condition){
            return setbit(key, offset, value);
        }
        return null;
    }

    /**
     * 将值value关联到key,并将key的过期时间设为seconds(以秒为单位)
     * @param key redis键
     * @param seconds 过期时间 - 秒
     * @param value redis的值
     * @return 设置成功时返回OK
     */
    public static String setex(String key, int seconds, String value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将值value关联到key,并将key的过期时间设为seconds(以秒为单位)
     * @param condition 满足这个条件才setex
     * @param key redis键
     * @param seconds 过期时间 - 秒
     * @param value redis的值
     * @return 设置成功时返回OK
     */
    public static String setex(boolean condition, String key, int seconds, String value) {
        if(condition){
            return setex(key, seconds, value);
        }
        return null;
    }

    /**
     * 将值value关联到key,并将key的过期时间设为seconds(以秒为单位)
     * @param key redis键
     * @param seconds 过期时间 - 秒
     * @param value redis的值
     * @return 设置成功时返回OK
     */
    public static String setex(byte[] key, int seconds, byte[] value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将值value关联到key,并将key的过期时间设为seconds(以秒为单位)
     * @param condition 满足这个条件才setex
     * @param key redis键
     * @param seconds 过期时间
     * @param value redis的值
     * @return 设置成功时返回OK
     */
    public static String setex(boolean condition, byte[] key, int seconds, byte[] value) {
        if(condition){
            return setex(key, seconds, value);
        }
        return null;
    }

    /**
     * 只有在key不存在时,设置key的值
     * @param key redis键
     * @param value redis的值
     * @return 设置成功,返回1;设置失败,返回0
     */
    public static Long setnx(String key, String value) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 只有在key不存在时,设置key的值
     * @param condition 满足这个条件才setnx
     * @param key redis键
     * @param value redis的值
     * @return 设置成功,返回1;设置失败,返回0
     */
    public static Long setnx(boolean condition, String key, String value) {
        if(condition){
            return setnx(key, value);
        }
        return null;
    }

    /**
     * 只有在key不存在时,设置key的值
     * @param key redis键
     * @param value redis的值
     * @return 设置成功,返回1;设置失败,返回0
     */
    public static Long setnx(byte[] key, byte[] value) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 只有在key不存在时,设置key的值
     * @param condition 满足这个条件才setnx
     * @param key redis键
     * @param value redis的值
     * @return 设置成功,返回1;设置失败,返回0
     */
    public static Long setnx(boolean condition, byte[] key, byte[] value) {
        if(condition){
            return setnx(key, value);
        }
        return null;
    }

    /**
     * 用value参数覆写给定key所储存的字符串值,从偏移量offset开始
     * @param key redis键
     * @param offset 偏移量
     * @param value redis的值
     * @return 被修改后的字符串长度
     */
    public static Long setRange(String key, long offset, String value) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.setrange(key, offset, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 用value参数覆写给定key所储存的字符串值,从偏移量offset开始
     * @param condition 满足这个条件才setRange
     * @param key redis键
     * @param offset 偏移量
     * @param value redis的值
     * @return 被修改后的字符串长度
     */
    public static Long setRange(boolean condition, String key, long offset, String value) {
        if(condition){
            return setRange(key, offset, value);
        }
        return null;
    }

    /**
     * 用value参数覆写给定key所储存的字符串值,从偏移量offset开始
     * @param key redis键
     * @param offset 偏移量
     * @param value redis的值
     * @return 被修改后的字符串长度
     */
    public static Long setRange(byte[] key, long offset, byte[] value) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.setrange(key, offset, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 用value参数覆写给定key所储存的字符串值,从偏移量offset开始
     * @param condition 满足这个条件才setRange
     * @param key redis键
     * @param offset 偏移量
     * @param value redis的值
     * @return 被修改后的字符串长度
     */
    public static Long setRange(boolean condition, byte[] key, long offset, byte[] value) {
        if(condition){
            return setRange(key, offset, value);
        }
        return null;
    }

    /**
     * 获取key所储存的字符串值的长度
     * 当key储存的不是字符串值时,返回一个错误
     * @param key redis键
     * @return 字符串值的长度.当key不存在时,返回0
     */
    public static Long strlen(String key){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.strlen(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取key所储存的字符串值的长度
     * 当key储存的不是字符串值时,返回一个错误
     * @param key redis键
     * @return 字符串值的长度.当key不存在时,返回0
     */
    public static Long strlen(byte[] key){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.strlen(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 同时设置一个或多个key-value对
     * @param keysvalues redis键-值对
     * @return 总是返回OK
     */
    public static String mset(String ... keysvalues){
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.mset(keysvalues);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 同时设置一个或多个key-value对
     * @param condition 满足这个条件才mset
     * @param keysvalues redis键-值对
     * @return 总是返回OK
     */
    public static String mset(boolean condition, String ... keysvalues){
        if(condition){
            return mset(keysvalues);
        }
        return null;
    }

    /**
     * 同时设置一个或多个key-value对
     * @param keysvalues redis键-值对
     * @return 总是返回OK
     */
    public static String mset(byte[] ... keysvalues){
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.mset(keysvalues);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 同时设置一个或多个key-value对
     * @param condition 满足这个条件才mset
     * @param keysvalues redis键-值对
     * @return 总是返回OK
     */
    public static String mset(boolean condition, byte[] ... keysvalues){
        if(condition){
            return mset(keysvalues);
        }
        return null;
    }

    /**
     * 同时设置一个或多个key-value对
     * @param map redis键-值对
     * @return 总是返回OK
     */
    public static String mset(Map<String, String> map){
        if(map != null && !map.isEmpty()){
            // 处理数据
            String[] keysvalues = new String[map.size() * 2];
            int index = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                keysvalues[index ++] = entry.getKey();
                keysvalues[index ++] = entry.getValue();
            }
            return mset(keysvalues);
        }
        return null;
    }

    /**
     * 同时设置一个或多个key-value对
     * @param condition 满足这个条件才mset
     * @param map redis键-值对
     * @return 总是返回OK
     */
    public static String mset(boolean condition, Map<String, String> map){
        if(condition){
            return mset(map);
        }
        return null;
    }

    /**
     * 同时设置一个或多个key-value对,当且仅当所有给定key都不存在
     * @param keysvalues redis键-值对
     * @return 当所有key都成功设置,返回1;如果所有给定key都设置失败(至少有一个key已经存在),那么返回0
     */
    public static Long msetnx(String ... keysvalues){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.msetnx(keysvalues);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 同时设置一个或多个key-value对,当且仅当所有给定key都不存在
     * @param condition 满足这个条件才msetnx
     * @param keysvalues redis键-值对
     * @return 当所有key都成功设置,返回1;如果所有给定key都设置失败(至少有一个key已经存在),那么返回0
     */
    public static Long msetnx(boolean condition, String ... keysvalues){
        if(condition){
            return msetnx(keysvalues);
        }
        return null;
    }

    /**
     * 同时设置一个或多个key-value对,当且仅当所有给定key都不存在
     * @param keysvalues redis键-值对
     * @return 当所有key都成功设置,返回1;如果所有给定key都设置失败(至少有一个key已经存在),那么返回0
     */
    public static Long msetnx(byte[] ... keysvalues){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.msetnx(keysvalues);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 同时设置一个或多个key-value对,当且仅当所有给定key都不存在
     * @param condition 满足这个条件才msetnx
     * @param keysvalues redis键-值对
     * @return 当所有key都成功设置,返回1;如果所有给定key都设置失败(至少有一个key已经存在),那么返回0
     */
    public static Long msetnx(boolean condition, byte[] ... keysvalues){
        if(condition){
            return msetnx(keysvalues);
        }
        return null;
    }

    /**
     * 同时设置一个或多个key-value对,当且仅当所有给定key都不存在
     * @param map redis键-值对
     * @return 当所有key都成功设置,返回1;如果所有给定key都设置失败(至少有一个key已经存在),那么返回0
     */
    public static Long msetnx(Map<String, String> map){
        if(map != null && !map.isEmpty()){
            // 处理数据
            String[] keysvalues = new String[map.size() * 2];
            int index = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                keysvalues[index ++] = entry.getKey();
                keysvalues[index ++] = entry.getValue();
            }
            return msetnx(keysvalues);
        }
        return null;
    }

    /**
     * 同时设置一个或多个key-value对,当且仅当所有给定key都不存在
     * @param condition 满足这个条件才msetnx
     * @param map redis键-值对
     * @return 当所有key都成功设置,返回1;如果所有给定key都设置失败(至少有一个key已经存在),那么返回0
     */
    public static Long msetnx(boolean condition, Map<String, String> map){
        if(condition){
            return msetnx(map);
        }
        return null;
    }

    /**
     * 设置带过期时间的key-value对 - 毫秒级
     * @param key redis键
     * @param milliseconds 过期时间 - 毫秒
     * @param value redis的值
     * @return 设置成功时返回OK
     */
    public static String psetex(String key, long milliseconds, String value){
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.psetex(key, milliseconds, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 设置带过期时间的key-value对 - 毫秒级
     * @param condition 满足这个条件才psetex
     * @param key redis键
     * @param milliseconds 过期时间 - 毫秒
     * @param value redis的值
     * @return 设置成功时返回OK
     */
    public static String psetex(boolean condition, String key, long milliseconds, String value){
        if(condition){
            return psetex(key, milliseconds, value);
        }
        return null;
    }

    /**
     * 设置带过期时间的key-value对 - 毫秒级
     * @param key redis键
     * @param milliseconds 过期时间 - 毫秒
     * @param value redis的值
     * @return 设置成功时返回OK
     */
    public static String psetex(byte[] key, long milliseconds, byte[] value){
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.psetex(key, milliseconds, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 设置带过期时间的key-value对 - 毫秒级
     * @param condition 满足这个条件才psetex
     * @param key redis键
     * @param milliseconds 过期时间 - 毫秒
     * @param value redis的值
     * @return 设置成功时返回OK
     */
    public static String psetex(boolean condition, byte[] key, long milliseconds, byte[] value){
        if(condition){
            return psetex(key, milliseconds, value);
        }
        return null;
    }

    /**
     * 将key中储存的数字值增一。
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行累加操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param key redis键
     * @return 累加之后key的值
     */
    public static Long incr(String key) {
        Long value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.incr(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 将key中储存的数字值增一。
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行累加操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param condition 满足这个条件才去incr
     * @param key redis键
     * @return 累加之后key的值
     */
    public static Long incr(boolean condition, String key) {
        if(condition){
            return incr(key);
        }
        return null;
    }

    /**
     * 将key中储存的数字值增一。
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行累加操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param key redis键
     * @return 累加之后key的值
     */
    public static Long incr(byte[] key) {
        Long value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.incr(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 将key中储存的数字值增一。
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行累加操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param condition 满足这个条件才去incr
     * @param key redis键
     * @return 累加之后key的值
     */
    public static Long incr(boolean condition, byte[] key) {
        if(condition){
            return incr(key);
        }
        return null;
    }

    /**
     * 将key中储存的数字值加上指定的增量值。
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行累加操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param key redis键
     * @param increment 增加值
     * @return 加上指定的增量值之后,key的值
     */
    public static Long incrBy(String key, long increment) {
        Long value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.incrBy(key,increment);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 将key中储存的数字值加上指定的增量值。
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行累加操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param condition 满足这个条件才去incrBy
     * @param key redis键
     * @param increment 增加值
     * @return 加上指定的增量值之后,key的值
     */
    public static Long incrBy(boolean condition, String key, long increment) {
        if(condition){
            return incrBy(key,increment);
        }
        return null;
    }

    /**
     * 将key中储存的数字值加上指定的增量值。
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行累加操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param key redis键
     * @param increment 增加值
     * @return 加上指定的增量值之后,key的值
     */
    public static Long incrBy(byte[] key, long increment) {
        Long value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.incrBy(key,increment);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 将key中储存的数字值加上指定的增量值。
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行累加操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param condition 满足这个条件才去incrBy
     * @param increment 增加值
     * @param key redis键
     * @return 加上指定的增量值之后,key的值
     */
    public static Long incrBy(boolean condition, byte[] key, long increment) {
        if(condition){
            return incrBy(key,increment);
        }
        return null;
    }

    /**
     * 将key中储存的数字值加上指定的增量值。
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行累加操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param key redis键
     * @param increment 增加值
     * @return 加上指定的增量值之后,key的值
     */
    public static Double incrByFloat(String key, double increment) {
        Double value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.incrByFloat(key,increment);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 将key中储存的数字值加上指定的增量值。
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行累加操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param condition 满足这个条件才去incrByFloat
     * @param key redis键
     * @param increment 增加值
     * @return 加上指定的增量值之后,key的值
     */
    public static Double incrByFloat(boolean condition, String key, double increment) {
        if(condition){
            return incrByFloat(key,increment);
        }
        return null;
    }

    /**
     * 将key中储存的数字值加上指定的增量值。
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行累加操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param key redis键
     * @param increment 增加值
     * @return 加上指定的增量值之后,key的值
     */
    public static Double incrByFloat(byte[] key, double increment) {
        Double value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.incrByFloat(key,increment);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 将key中储存的数字值加上指定的增量值。
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行累加操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param condition 满足这个条件才去incrByFloat
     * @param increment 增加值
     * @param key redis键
     * @return 加上指定的增量值之后,key的值
     */
    public static Double incrByFloat(boolean condition, byte[] key, double increment) {
        if(condition){
            return incrByFloat(key,increment);
        }
        return null;
    }

    /**
     * 将key中储存的数字值减一。
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行减法操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param key redis键
     * @return 减一之后的key值
     */
    public static Long decr(String key){
        Long value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.decr(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 将key中储存的数字值减一。
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行减法操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param condition 满足这个条件才去decr
     * @param key redis键
     * @return 减一之后的key值
     */
    public static Long decr(boolean condition, String key){
        if(condition){
            return decr(key);
        }
        return null;
    }

    /**
     * 将key中储存的数字值减一。
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行减法操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param key redis键
     * @return 减一之后的key值
     */
    public static Long decr(byte[] key){
        Long value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.decr(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 将key中储存的数字值减一。
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行减法操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param condition 满足这个条件才去decr
     * @param key redis键
     * @return 减一之后的key值
     */
    public static Long decr(boolean condition, byte[] key){
        if(condition){
            return decr(key);
        }
        return null;
    }

    /**
     * 将key中储存的数字值减去指定的减量值
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行减法操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param key redis键
     * @param decrement 需要减的值
     * @return 减去指定减量值之后的key值
     */
    public static Long decrBy(String key, long decrement){
        Long value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.decrBy(key,decrement);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 将key中储存的数字值减去指定的减量值
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行减法操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param condition 满足这个条件才去decrBy
     * @param key redis键
     * @param decrement 需要减的值
     * @return 减去指定减量值之后的key值
     */
    public static Long decrBy(boolean condition, String key, long decrement){
        if(condition){
            return decrBy(key,decrement);
        }
        return null;
    }

    /**
     * 将key中储存的数字值减去指定的减量值
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行减法操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param key redis键
     * @param decrement 需要减的值
     * @return 减去指定减量值之后的key值
     */
    public static Long decrBy(byte[] key, long decrement){
        Long value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.decrBy(key,decrement);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 将key中储存的数字值减去指定的减量值
     * 如果key不存在,那么key的值会先被初始化为0,然后再执行减法操作
     * 如果值包含错误的类型,或字符串类型的值不能表示为数字,那么返回一个错误
     * 本操作的值限制在64位(bit)有符号数字表示之内
     * @param condition 满足这个条件才去decrBy
     * @param key redis键
     * @param decrement 需要减的值
     * @return 减去指定减量值之后的key值
     */
    public static Long decrBy(boolean condition, byte[] key, long decrement){
        if(condition){
            return decrBy(key,decrement);
        }
        return null;
    }

    /**
     * 如果key已经存在并且是一个字符串,将value追加到该key原来值value的末尾
     * 如果key不存在,就将给定key设为value
     * @param key redis键
     * @param value redis值
     * @return 追加指定值之后,key中字符串的长度
     */
    public static Long append(String key, String value){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.append(key,value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 如果key已经存在并且是一个字符串,将value追加到该key原来值value的末尾
     * 如果key不存在,就将给定key设为value
     * @param condition 满足这个条件才去append
     * @param key redis键
     * @param value redis值
     * @return 追加指定值之后,key中字符串的长度
     */
    public static Long append(boolean condition, String key, String value){
        if(condition){
            return append(key, value);
        }
        return null;
    }

    /**
     * 如果key已经存在并且是一个字符串,将value追加到该key原来值value的末尾
     * 如果key不存在,就将给定key设为value
     * @param key redis键
     * @param value redis值
     * @return 追加指定值之后,key中字符串的长度
     */
    public static Long append(byte[] key, byte[] value){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.append(key,value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 如果key已经存在并且是一个字符串,将value追加到该key原来值value的末尾
     * 如果key不存在,就将给定key设为value
     * @param condition 满足这个条件才去append
     * @param key redis键
     * @param value redis值
     * @return 追加指定值之后,key中字符串的长度
     */
    public static Long append(boolean condition, byte[] key, byte[] value){
        if(condition){
            return append(key, value);
        }
        return null;
    }

    //**********          redis哈希数据操作的方法          **********//

    /**
     * 赋值数据
     * 如果哈希表不存在,一个新的哈希表被创建并进行HSET操作
     * 如果字段已经存在于哈希表中,旧值将被覆盖
     * @param key redis键
     * @param field redis域
     * @param value redis值
     * @return 新字段设置成功返回1,旧字段覆盖成功返回0
     */
    public static Long hset(String key,String field, String value) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hset(key, field, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 赋值数据
     * 如果哈希表不存在,一个新的哈希表被创建并进行HSET操作
     * 如果字段已经存在于哈希表中,旧值将被覆盖
     * @param condition 满足这个条件才去hset
     * @param key redis键
     * @param field redis域
     * @param value redis值
     * @return 新字段设置成功返回1,旧字段覆盖成功返回0
     */
    public static Long hset(boolean condition,String key,String field, String value) {
        if(condition){
            return hset(key, field, value);
        }
        return null;
    }

    /**
     * 赋值数据
     * 如果哈希表不存在,一个新的哈希表被创建并进行HSET操作
     * 如果字段已经存在于哈希表中,旧值将被覆盖
     * @param key redis键
     * @param hash redis域和值
     * @return 新字段设置成功返回1,旧字段覆盖成功返回0
     */
    public static Long hset(String key, Map<String, String> hash) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hset(key, hash);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 赋值数据
     * 如果哈希表不存在,一个新的哈希表被创建并进行HSET操作
     * 如果字段已经存在于哈希表中,旧值将被覆盖
     * @param condition 满足这个条件才去hset
     * @param key redis键
     * @param hash redis域和值
     * @return 新字段设置成功返回1,旧字段覆盖成功返回0
     */
    public static Long hset(boolean condition, String key, Map<String, String> hash) {
        if(condition){
            return hset(key, hash);
        }
        return null;
    }

    /**
     * 赋值数据
     * 如果哈希表不存在,一个新的哈希表被创建并进行HSET操作
     * 如果字段已经存在于哈希表中,旧值将被覆盖
     * @param key redis键
     * @param field redis域
     * @param value redis值
     * @return 新字段设置成功返回1,旧字段覆盖成功返回0
     */
    public static Long hset(byte[] key,byte[] field, byte[] value) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hset(key, field, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 赋值数据
     * 如果哈希表不存在,一个新的哈希表被创建并进行HSET操作
     * 如果字段已经存在于哈希表中,旧值将被覆盖
     * @param condition 满足这个条件才去hset
     * @param key redis键
     * @param field redis域
     * @param value redis值
     * @return 新字段设置成功返回1,旧字段覆盖成功返回0
     */
    public static Long hset(boolean condition,byte[] key,byte[] field, byte[] value) {
        if(condition){
            return hset(key, field, value);
        }
        return null;
    }

    /**
     * 赋值数据
     * 如果哈希表不存在,一个新的哈希表被创建并进行HSET操作
     * 如果字段已经存在于哈希表中,旧值将被覆盖
     * @param key redis键
     * @param hash redis域和值
     * @return 新字段设置成功返回1,旧字段覆盖成功返回0
     */
    public static Long hset(byte[] key,Map<byte[],byte[]> hash) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hset(key, hash);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 赋值数据
     * 如果哈希表不存在,一个新的哈希表被创建并进行HSET操作
     * 如果字段已经存在于哈希表中,旧值将被覆盖
     * @param condition 满足这个条件才去hset
     * @param key redis键
     * @param hash redis域和值
     * @return 新字段设置成功返回1,旧字段覆盖成功返回0
     */
    public static Long hset(boolean condition,byte[] key,Map<byte[],byte[]> hash) {
        if(condition){
            return hset(key, hash);
        }
        return null;
    }

    /**
     * 为哈希表中不存在的的字段赋值
     * 如果哈希表不存在,一个新的哈希表被创建并进行HSET操作
     * 如果字段已经存在于哈希表中,操作无效
     * 如果key不存在,一个新哈希表被创建并执行HSETNX命令
     * @param key redis键
     * @param field redis域
     * @param value redis值
     * @return 设置成功,返回1;如果给定字段已经存在且没有操作被执行,返回0
     */
    public static Long hsetnx(String key,String field, String value) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hsetnx(key, field, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 为哈希表中不存在的的字段赋值
     * 如果哈希表不存在,一个新的哈希表被创建并进行HSET操作
     * 如果字段已经存在于哈希表中,操作无效
     * 如果key不存在,一个新哈希表被创建并执行HSETNX命令
     * @param condition 满足这个条件才去hsetnx
     * @param key redis键
     * @param field redis域
     * @param value redis值
     * @return 设置成功,返回1;如果给定字段已经存在且没有操作被执行,返回0
     */
    public static Long hsetnx(boolean condition,String key,String field, String value) {
        if(condition){
            return hsetnx(key, field, value);
        }
        return null;
    }

    /**
     * 为哈希表中不存在的的字段赋值
     * 如果哈希表不存在,一个新的哈希表被创建并进行HSET操作
     * 如果字段已经存在于哈希表中,操作无效
     * 如果key不存在,一个新哈希表被创建并执行HSETNX命令
     * @param key redis键
     * @param field redis域
     * @param value redis值
     * @return 设置成功,返回1;如果给定字段已经存在且没有操作被执行,返回0
     */
    public static Long hsetnx(byte[] key,byte[] field, byte[] value) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hsetnx(key, field, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 为哈希表中不存在的的字段赋值
     * 如果哈希表不存在,一个新的哈希表被创建并进行HSET操作
     * 如果字段已经存在于哈希表中,操作无效
     * 如果key不存在,一个新哈希表被创建并执行HSETNX命令
     * @param condition 满足这个条件才去hsetnx
     * @param key redis键
     * @param field redis域
     * @param value redis值
     * @return 设置成功,返回1;如果给定字段已经存在且没有操作被执行,返回0
     */
    public static Long hsetnx(boolean condition,byte[] key,byte[] field, byte[] value) {
        if(condition){
            return hsetnx(key, field, value);
        }
        return null;
    }

    /**
     * 获取哈希表中指定字段的值
     * @param key redis键
     * @param field redis域
     * @return 返回给定字段的值.如果给定的字段或key不存在时,返回nil
     */
    public static String hget(String key,String field) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hget(key, field);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取哈希表中指定字段的值
     * @param key redis键
     * @param field redis域
     * @return 返回给定字段的值.如果给定的字段或key不存在时,返回nil
     */
    public static byte[] hget(byte[] key,byte[] field) {
        byte[] result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hget(key, field);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取哈希表中所有的字段和值
     * 在返回值里,紧跟每个字段名(field)之后是字段的值(value),所以返回值的长度是哈希表大小的两倍
     * @param key redis键
     * @return 以Map形式返回哈希表的字段及字段值;若key不存在,返回空Map
     */
    public static Map<String, String> hgetAll(String key) {
        Map<String, String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hgetAll(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取哈希表中所有的字段和值
     * 在返回值里,紧跟每个字段名(field)之后是字段的值(value),所以返回值的长度是哈希表大小的两倍
     * @param key redis键
     * @return 以Map形式返回哈希表的字段及字段值;若key不存在,返回空Map
     */
    public static Map<byte[], byte[]> hgetAll(byte[] key) {
        Map<byte[], byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hgetAll(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 判断哈希表的指定字段是否存在
     * @param key redis键
     * @param field 域
     * @return 如果哈希表含有给定字段,返回true;如果哈希表不含有给定字段,或key不存在,返回false
     */
    public static Boolean hexists(String key, String field) {
        Boolean value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.hexists(key, field);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 判断哈希表的指定字段是否存在
     * @param key redis键
     * @param field 域
     * @return 如果哈希表含有给定字段,返回true;如果哈希表不含有给定字段,或key不存在,返回false
     */
    public static Boolean hexists(byte[] key, byte[] field) {
        Boolean value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.hexists(key, field);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 删除哈希表key中的一个或多个指定字段,不存在的字段将被忽略
     * @param key redis键
     * @param fields redis域
     * @return 被成功删除字段的数量,不包括被忽略的字段
     */
    public static Long hdel(String key, String ... fields) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hdel(key, fields);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 删除哈希表key中的一个或多个指定字段,不存在的字段将被忽略
     * @param condition 满足这个条件才去hdel
     * @param key redis键
     * @param fields redis域
     * @return 被成功删除字段的数量,不包括被忽略的字段
     */
    public static Long hdel(boolean condition, String key, String ... fields) {
        if(condition){
            return hdel(key, fields);
        }
        return null;
    }

    /**
     * 删除哈希表key中的一个或多个指定字段,不存在的字段将被忽略
     * @param key redis键
     * @param fields redis域
     * @return 被成功删除字段的数量,不包括被忽略的字段
     */
    public static Long hdel(byte[] key, byte[] ... fields) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hdel(key, fields);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 删除哈希表key中的一个或多个指定字段,不存在的字段将被忽略
     * @param condition 满足这个条件才去hdel
     * @param key redis键
     * @param fields redis域
     * @return 被成功删除字段的数量,不包括被忽略的字段
     */
    public static Long hdel(boolean condition, byte[] key, byte[] ... fields) {
        if(condition){
            return hdel(key, fields);
        }
        return null;
    }

    /**
     * 为哈希表中的字段值加上指定增量值
     * 增量也可以为负数,相当于对指定字段进行减法操作
     * 如果哈希表的key不存在,一个新的哈希表被创建并执行累加命令
     * 如果指定的字段不存在,那么在执行命令前,字段的值被初始化为0
     * 对一个储存字符串值的字段执行累加命令将造成一个错误。
     * 本操作的值被限制在64位(bit)有符号数字表示之内
     * @param key redis键
     * @param field redis域
     * @param increment 增值
     * @return 累加指定增量值之后的key的值
     */
    public static Long hincrBy(String key, String field, long increment) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hincrBy(key, field, increment);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 为哈希表中的字段值加上指定增量值
     * 增量也可以为负数,相当于对指定字段进行减法操作
     * 如果哈希表的key不存在,一个新的哈希表被创建并执行累加命令
     * 如果指定的字段不存在,那么在执行命令前,字段的值被初始化为0
     * 对一个储存字符串值的字段执行累加命令将造成一个错误。
     * 本操作的值被限制在64位(bit)有符号数字表示之内
     * @param condition 满足这个条件才去hincrBy
     * @param key redis键
     * @param increment 增值
     * @return 累加指定增量值之后的key的值
     */
    public static Long hincrBy(boolean condition, String key, String field, long increment) {
        if(condition){
            return hincrBy(key, field, increment);
        }
        return null;
    }

    /**
     * 为哈希表中的字段值加上指定增量值
     * 增量也可以为负数,相当于对指定字段进行减法操作
     * 如果哈希表的key不存在,一个新的哈希表被创建并执行累加命令
     * 如果指定的字段不存在,那么在执行命令前,字段的值被初始化为0
     * 对一个储存字符串值的字段执行累加命令将造成一个错误。
     * 本操作的值被限制在64位(bit)有符号数字表示之内
     * @param key redis键
     * @param field redis域
     * @param increment 增值
     * @return 累加指定增量值之后的key的值
     */
    public static Long hincrBy(byte[] key, byte[] field, long increment) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hincrBy(key, field, increment);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 为哈希表中的字段值加上指定增量值
     * 增量也可以为负数,相当于对指定字段进行减法操作
     * 如果哈希表的key不存在,一个新的哈希表被创建并执行累加命令
     * 如果指定的字段不存在,那么在执行命令前,字段的值被初始化为0
     * 对一个储存字符串值的字段执行累加命令将造成一个错误。
     * 本操作的值被限制在64位(bit)有符号数字表示之内
     * @param condition 满足这个条件才去hincrBy
     * @param key redis键
     * @param increment 增值
     * @return 累加指定增量值之后的key的值
     */
    public static Long hincrBy(boolean condition, byte[] key, byte[] field, long increment) {
        if(condition){
            return hincrBy(key, field, increment);
        }
        return null;
    }

    /**
     * 为哈希表中的字段值加上指定增量值
     * 增量也可以为负数,相当于对指定字段进行减法操作
     * 如果哈希表的key不存在,一个新的哈希表被创建并执行累加命令
     * 如果指定的字段不存在,那么在执行命令前,字段的值被初始化为0
     * 对一个储存字符串值的字段执行累加命令将造成一个错误。
     * 本操作的值被限制在64位(bit)有符号数字表示之内
     * @param key redis键
     * @param field redis域
     * @param increment 增值
     * @return 累加指定增量值之后的key的值
     */
    public static Double hincrByFloat(String key, String field, double increment) {
        Double result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hincrByFloat(key, field, increment);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 为哈希表中的字段值加上指定增量值
     * 增量也可以为负数,相当于对指定字段进行减法操作
     * 如果哈希表的key不存在,一个新的哈希表被创建并执行累加命令
     * 如果指定的字段不存在,那么在执行命令前,字段的值被初始化为0
     * 对一个储存字符串值的字段执行累加命令将造成一个错误。
     * 本操作的值被限制在64位(bit)有符号数字表示之内
     * @param condition 满足这个条件才去hincrByFloat
     * @param key redis键
     * @param increment 增值
     * @return 累加指定增量值之后的key的值
     */
    public static Double hincrByFloat(boolean condition, String key, String field, double increment) {
        if(condition){
            return hincrByFloat(key, field, increment);
        }
        return null;
    }

    /**
     * 为哈希表中的字段值加上指定增量值
     * 增量也可以为负数,相当于对指定字段进行减法操作
     * 如果哈希表的key不存在,一个新的哈希表被创建并执行累加命令
     * 如果指定的字段不存在,那么在执行命令前,字段的值被初始化为0
     * 对一个储存字符串值的字段执行累加命令将造成一个错误。
     * 本操作的值被限制在64位(bit)有符号数字表示之内
     * @param key redis键
     * @param field redis域
     * @param increment 增值
     * @return 累加指定增量值之后的key的值
     */
    public static Double hincrByFloat(byte[] key, byte[] field, double increment) {
        Double result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hincrByFloat(key, field, increment);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 为哈希表中的字段值加上指定增量值
     * 增量也可以为负数,相当于对指定字段进行减法操作
     * 如果哈希表的key不存在,一个新的哈希表被创建并执行累加命令
     * 如果指定的字段不存在,那么在执行命令前,字段的值被初始化为0
     * 对一个储存字符串值的字段执行累加命令将造成一个错误。
     * 本操作的值被限制在64位(bit)有符号数字表示之内
     * @param condition 满足这个条件才去hincrByFloat
     * @param key redis键
     * @param increment 增值
     * @return 累加指定增量值之后的key的值
     */
    public static Double hincrByFloat(boolean condition, byte[] key, byte[] field, double increment) {
        if(condition){
            return hincrByFloat(key, field, increment);
        }
        return null;
    }

    /**
     * 命令用于获取哈希表中的所有域(field)
     * @param keys redis键
     * @return 包含哈希表中所有域(field)列表;当key不存在时,返回一个空列表
     */
    public static Set<String> hkeys(String keys) {
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hkeys(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 命令用于获取哈希表中的所有域(field)
     * @param keys redis键
     * @return 包含哈希表中所有域(field)列表;当key不存在时,返回一个空列表
     */
    public static Set<byte[]> hkeys(byte[] keys) {
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hkeys(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取哈希表所有值(value)
     * @param keys redis键
     * @return 包含哈希表中所有值(value)列表;当key不存在时,返回一个空列表
     */
    public static List<String> hvals(String keys) {
        List<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hvals(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取哈希表所有值(value)
     * @param keys redis键
     * @return 包含哈希表中所有值(value)列表;当key不存在时,返回一个空列表
     */
    public static List<byte[]> hvals(byte[] keys) {
        List<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hvals(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取哈希表中字段的数量
     * @param keys redis键
     * @return 哈希表中字段的数量;当key不存在时,返回0
     */
    public static Long hlen(String keys) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hlen(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取哈希表中字段的数量
     * @param keys redis键
     * @return 哈希表中字段的数量;当key不存在时,返回0
     */
    public static Long hlen(byte[] keys) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hlen(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取哈希表中,一个或多个给定字段的值
     * 如果指定的字段不存在于哈希表,那么返回一个nil值
     * @param key redis键
     * @param fields redis域
     * @return 一个包含多个给定字段关联值的表,表值的排列顺序和指定字段的请求顺序一样
     */
    public static List<String> hmget(String key, String ... fields){
        List<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hmget(key, fields);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取哈希表中,一个或多个给定字段的值
     * 如果指定的字段不存在于哈希表,那么返回一个nil值
     * @param key redis键
     * @param fields redis域
     * @return 一个包含多个给定字段关联值的表,表值的排列顺序和指定字段的请求顺序一样
     */
    public static List<String> hmget(String key, Collection<String> fields){
        // 处理数据
        if(fields != null && !fields.isEmpty()){
            String[] param = new String[fields.size()];
            int index = 0;
            for (String field : fields) {
                param[index++] = field;
            }
            return hmget(key, param);
        }
        return null;
    }

    /**
     * 获取哈希表中,一个或多个给定字段的值
     * 如果指定的字段不存在于哈希表,那么返回一个nil值
     * @param key redis键
     * @param fields redis域
     * @return 一个包含多个给定字段关联值的表,表值的排列顺序和指定字段的请求顺序一样
     */
    public static List<byte[]> hmget(byte[] key, byte[] ... fields){
        List<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hmget(key, fields);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取哈希表中,一个或多个给定字段的值
     * 如果指定的字段不存在于哈希表,那么返回一个nil值
     * @param key redis键
     * @param fields redis域
     * @return 一个包含多个给定字段关联值的表,表值的排列顺序和指定字段的请求顺序一样
     */
    public static List<byte[]> hmget(byte[] key, Collection<byte[]> fields){
        // 处理数据
        if(fields != null && !fields.isEmpty()){
            byte[][] param = new byte[fields.size()][];
            int index = 0;
            for (byte[] field : fields) {
                param[index++] = field;
            }
            return hmget(key, param);
        }
        return null;
    }

    /**
     * 同时将多个field-value(域-值)对设置到哈希表中
     * 此命令会覆盖哈希表中已存在的字段
     * 如果哈希表不存在,会创建一个空哈希表,并执行HMSET操作
     * @param key redis键
     * @param hash redis Map
     * @return 成功返回OK
     */
    public static String hmset(String key, Map<String, String> hash) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hmset(key, hash);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 同时将多个field-value(域-值)对设置到哈希表中
     * 此命令会覆盖哈希表中已存在的字段
     * 如果哈希表不存在,会创建一个空哈希表,并执行HMSET操作
     * @param condition 满足这个条件才去hmset
     * @param key redis键
     * @param hash redis Map
     * @return 成功返回OK
     */
    public static String hmset(boolean condition, String key, Map<String, String> hash) {
        if(condition){
            return hmset(key, hash);
        }
        return null;
    }

    /**
     * 同时将多个field-value(域-值)对设置到哈希表中
     * 此命令会覆盖哈希表中已存在的字段
     * 如果哈希表不存在,会创建一个空哈希表,并执行HMSET操作
     * @param key redis键
     * @param hash redis Map
     * @return 成功返回OK
     */
    public static String hmset(byte[] key, Map<byte[], byte[]> hash) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hmset(key, hash);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 同时将多个field-value(域-值)对设置到哈希表中
     * 此命令会覆盖哈希表中已存在的字段
     * 如果哈希表不存在,会创建一个空哈希表,并执行HMSET操作
     * @param condition 满足这个条件才去hmset
     * @param key redis键
     * @param hash redis Map
     * @return 成功返回OK
     */
    public static String hmset(boolean condition, byte[] key, Map<byte[], byte[]> hash) {
        if(condition){
            return hmset(key, hash);
        }
        return null;
    }

    /**
     * 迭代数据库中的数据库键
     * @param key redis键
     * @return 返回的每个元素都是一个元组,每一个元组元素由一个域(field)和值(value)组成
     */
    public static ScanResult<Map.Entry<String, String>> hscan(String key) {
        return hscan(key, ScanParams.SCAN_POINTER_START);
    }

    /**
     * 迭代数据库中的数据库键
     * @param key redis键
     * @param cursor 游标
     * @return 返回的每个元素都是一个元组,每一个元组元素由一个域(field)和值(value)组成
     */
    public static ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        return hscan(key, cursor, "", 0);
    }

    /**
     * 迭代数据库中的数据库键
     * @param key redis键
     * @param cursor 游标
     * @param pattern 表达式
     * @return 返回的每个元素都是一个元组,每一个元组元素由一个域(field)和值(value)组成
     */
    public static ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, String pattern) {
        return hscan(key, cursor, pattern, 0);
    }

    /**
     * 迭代数据库中的数据库键
     * @param key redis键
     * @param cursor 游标
     * @param count 限制数量
     * @return 返回的每个元素都是一个元组,每一个元组元素由一个域(field)和值(value)组成
     */
    public static ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, int count) {
        return hscan(key, cursor, "", count);
    }

    /**
     * 迭代数据库中的数据库键
     * @param key redis键
     * @param cursor 游标
     * @param pattern 表达式
     * @param count 限制数量
     * @return 返回的每个元素都是一个元组,每一个元组元素由一个域(field)和值(value)组成
     */
    public static ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, String pattern, int count) {
        ScanResult<Map.Entry<String, String>> result = null;
        Jedis jedis = null;
        try {
            ScanParams params = new ScanParams();
            if(!StringUtils.isEmpty(pattern)){
                params.match(pattern);
            }
            if(count > 0){
                params.count(count);
            }
            jedis = getJedis();
            result = jedis.hscan(key, cursor, params);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    //**********          redis列表数据操作的方法          **********//

    /**
     * 移出并获取列表的第一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param keys redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<String> blpop(String ... keys) {
        List<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.blpop(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移出并获取列表的第一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param condition 满足这个条件才blpop
     * @param keys redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<String> blpop(boolean condition, String ... keys) {
        if(condition){
            return blpop(keys);
        }
        return null;
    }

    /**
     * 移出并获取列表的第一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param timeout 超时时间
     * @param keys redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<String> blpop(int timeout, String ... keys) {
        List<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.blpop(timeout,keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移出并获取列表的第一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param condition 满足这个条件才blpop
     * @param timeout 超时时间
     * @param keys redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<String> blpop(boolean condition, int timeout, String ... keys) {
        if(condition){
            return blpop(timeout, keys);
        }
        return null;
    }

    /**
     * 移出并获取列表的第一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param timeout 超时时间
     * @param key redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<String> blpop(int timeout, String key) {
        List<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.blpop(timeout,key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移出并获取列表的第一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param condition 满足这个条件才blpop
     * @param timeout 超时时间
     * @param key redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<String> blpop(boolean condition, int timeout, String key) {
        if(condition){
            return blpop(timeout, key);
        }
        return null;
    }

    /**
     * 移出并获取列表的第一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param keys redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<byte[]> blpop(byte[] ... keys) {
        List<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.blpop(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移出并获取列表的第一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param condition 满足这个条件才blpop
     * @param keys redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<byte[]> blpop(boolean condition, byte[] ... keys) {
        if(condition){
            return blpop(keys);
        }
        return null;
    }

    /**
     * 移出并获取列表的第一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param timeout 超时时间
     * @param keys redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<byte[]> blpop(int timeout, byte[] ... keys) {
        List<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.blpop(timeout,keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移出并获取列表的第一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param condition 满足这个条件才blpop
     * @param timeout 超时时间
     * @param keys redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<byte[]> blpop(boolean condition, int timeout, byte[] ... keys) {
        if(condition){
            return blpop(timeout, keys);
        }
        return null;
    }

    /**
     * 移出并获取列表的最后一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param keys redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<String> brpop(String ... keys) {
        List<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.brpop(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移出并获取列表的最后一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param condition 满足这个条件才brpop
     * @param keys redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<String> brpop(boolean condition, String ... keys) {
        if(condition){
            return brpop(keys);
        }
        return null;
    }

    /**
     * 移出并获取列表的最后一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param timeout 超时时间
     * @param keys redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<String> brpop(int timeout, String ... keys) {
        List<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.brpop(timeout,keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移出并获取列表的最后一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param condition 满足这个条件才brpop
     * @param timeout 超时时间
     * @param keys redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<String> brpop(boolean condition, int timeout, String ... keys) {
        if(condition){
            return brpop(timeout, keys);
        }
        return null;
    }

    /**
     * 移出并获取列表的最后一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param timeout 超时时间
     * @param key redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<String> brpop(int timeout, String key) {
        List<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.brpop(timeout,key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移出并获取列表的最后一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param condition 满足这个条件才brpop
     * @param timeout 超时时间
     * @param key redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<String> brpop(boolean condition, int timeout, String key) {
        if(condition){
            return brpop(timeout, key);
        }
        return null;
    }

    /**
     * 移出并获取列表的最后一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param keys redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<byte[]> brpop(byte[] ... keys) {
        List<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.brpop(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移出并获取列表的最后一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param condition 满足这个条件才brpop
     * @param keys redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<byte[]> brpop(boolean condition, byte[] ... keys) {
        if(condition){
            return brpop(keys);
        }
        return null;
    }

    /**
     * 移出并获取列表的最后一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param timeout 超时时间
     * @param keys redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<byte[]> brpop(int timeout, byte[] ... keys) {
        List<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.brpop(timeout,keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移出并获取列表的最后一个元素
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param condition 满足这个条件才brpop
     * @param timeout 超时时间
     * @param keys redis键
     * @return 如果列表为空,返回一个nil;否则,返回一个含有两个元素的列表,第一个元素是被弹出元素所属的key,第二个元素是被弹出元素的值
     */
    public static List<byte[]> brpop(boolean condition, int timeout, byte[] ... keys) {
        if(condition){
            return brpop(timeout, keys);
        }
        return null;
    }

    /**
     * 从列表中取出最后一个元素,并插入到另外一个列表的头部
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param source 源列表
     * @param destination 另外一个列表
     * @param timeout 超时时间
     * @return 被操作的元素
     */
    public static String brpoplpush(String source, String destination, int timeout) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.brpoplpush(source, destination, timeout);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 从列表中取出最后一个元素,并插入到另外一个列表的头部
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param condition 满足这个条件才brpoplpush
     * @param source 源列表
     * @param destination 另外一个列表
     * @param timeout 超时时间
     * @return 被操作的元素
     */
    public static String brpoplpush(boolean condition, String source, String destination, int timeout) {
        if(condition){
            return brpoplpush(source, destination, timeout);
        }
        return null;
    }

    /**
     * 从列表中取出最后一个元素,并插入到另外一个列表的头部
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param source 源列表
     * @param destination 另外一个列表
     * @param timeout 超时时间
     * @return 被操作的元素
     */
    public static byte[] brpoplpush(byte[] source, byte[] destination, int timeout) {
        byte[] result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.brpoplpush(source, destination, timeout);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 从列表中取出最后一个元素,并插入到另外一个列表的头部
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     * @param condition 满足这个条件才brpoplpush
     * @param source 源列表
     * @param destination 另外一个列表
     * @param timeout 超时时间
     * @return 被操作的元素
     */
    public static byte[] brpoplpush(boolean condition, byte[] source, byte[] destination, int timeout) {
        if(condition){
            return brpoplpush(source, destination, timeout);
        }
        return null;
    }

    /**
     * 通过索引获取列表中的元素
     * 你也可以使用负数下标,-1表示列表的最后一个元素,-2表示列表的倒数第二个元素,以此类推
     * @param key redis键
     * @param index 列表索引
     * @return 列表中下标为指定索引值的元素;如果指定索引值不在列表的区间范围内,返回nil
     */
    public static String lindex(String key, long index) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.lindex(key, index);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 通过索引获取列表中的元素
     * 你也可以使用负数下标,-1表示列表的最后一个元素,-2表示列表的倒数第二个元素,以此类推
     * @param key redis键
     * @param index 列表索引
     * @return 列表中下标为指定索引值的元素;如果指定索引值不在列表的区间范围内,返回nil
     */
    public static byte[] lindex(byte[] key, long index) {
        byte[] result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.lindex(key, index);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 在列表的元素before前插入元素value
     * 当指定元素不存在于列表中时,不执行任何操作
     * 当列表不存在时,被视为空列表,不执行任何操作
     * 如果key不是列表类型,返回一个错误
     * @param key redis键
     * @param before 在这个元素前插入
     * @param value 待插入的元素
     * @return 如果命令执行成功,返回插入操作完成之后,列表的长度;如果没有找到指定元素,返回-1;如果key不存在或为空列表,返回0
     */
    public static Long linsertBefore(String key, String before, String value){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.linsert(key,ListPosition.BEFORE,before,value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 在列表的元素before前插入元素value
     * 当指定元素不存在于列表中时,不执行任何操作
     * 当列表不存在时,被视为空列表,不执行任何操作
     * 如果key不是列表类型,返回一个错误
     * @param condition 满足这个条件才linsertBefore
     * @param key redis键
     * @param before 在这个元素前插入
     * @param value 待插入的元素
     * @return 如果命令执行成功,返回插入操作完成之后,列表的长度;如果没有找到指定元素,返回-1;如果key不存在或为空列表,返回0
     */
    public static Long linsertBefore(boolean condition, String key, String before, String value){
        if(condition){
            return linsertBefore(key, before, value);
        }
        return null;
    }

    /**
     * 在列表的元素before前插入元素value
     * 当指定元素不存在于列表中时,不执行任何操作
     * 当列表不存在时,被视为空列表,不执行任何操作
     * 如果key不是列表类型,返回一个错误
     * @param key redis键
     * @param before 在这个元素前插入
     * @param value 待插入的元素
     * @return 如果命令执行成功,返回插入操作完成之后,列表的长度;如果没有找到指定元素,返回-1;如果key不存在或为空列表,返回0
     */
    public static Long linsertBefore(byte[] key, byte[] before, byte[] value){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.linsert(key,ListPosition.BEFORE,before,value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 在列表的元素before前插入元素value
     * 当指定元素不存在于列表中时,不执行任何操作
     * 当列表不存在时,被视为空列表,不执行任何操作
     * 如果key不是列表类型,返回一个错误
     * @param condition 满足这个条件才linsertBefore
     * @param key redis键
     * @param before 在这个元素前插入
     * @param value 待插入的元素
     * @return 如果命令执行成功,返回插入操作完成之后,列表的长度;如果没有找到指定元素,返回-1;如果key不存在或为空列表,返回0
     */
    public static Long linsertBefore(boolean condition, byte[] key, byte[] before, byte[] value){
        if(condition){
            return linsertBefore(key, before, value);
        }
        return null;
    }

    /**
     * 在列表的元素after后插入元素value
     * 当指定元素不存在于列表中时,不执行任何操作
     * 当列表不存在时,被视为空列表,不执行任何操作
     * 如果key不是列表类型,返回一个错误
     * @param key redis键
     * @param after 在这个元素后插入
     * @param value 待插入的元素
     * @return 如果命令执行成功,返回插入操作完成之后,列表的长度;如果没有找到指定元素,返回-1;如果key不存在或为空列表,返回0
     */
    public static Long linsertAfter(String key, String after, String value){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.linsert(key,ListPosition.AFTER,after,value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 在列表的元素after后插入元素value
     * 当指定元素不存在于列表中时,不执行任何操作
     * 当列表不存在时,被视为空列表,不执行任何操作
     * 如果key不是列表类型,返回一个错误
     * @param condition 满足这个条件才linsertAfter
     * @param key redis键
     * @param after 在这个元素后插入
     * @param value 待插入的元素
     * @return 如果命令执行成功,返回插入操作完成之后,列表的长度;如果没有找到指定元素,返回-1;如果key不存在或为空列表,返回0
     */
    public static Long linsertAfter(boolean condition, String key, String after, String value){
        if(condition){
            return linsertAfter(key, after, value);
        }
        return null;
    }

    /**
     * 在列表的元素after后插入元素value
     * 当指定元素不存在于列表中时,不执行任何操作
     * 当列表不存在时,被视为空列表,不执行任何操作
     * 如果key不是列表类型,返回一个错误
     * @param key redis键
     * @param after 在这个元素后插入
     * @param value 待插入的元素
     * @return 如果命令执行成功,返回插入操作完成之后,列表的长度;如果没有找到指定元素,返回-1;如果key不存在或为空列表,返回0
     */
    public static Long linsertAfter(byte[] key, byte[] after, byte[] value){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.linsert(key,ListPosition.AFTER,after,value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 在列表的元素after后插入元素value
     * 当指定元素不存在于列表中时,不执行任何操作
     * 当列表不存在时,被视为空列表,不执行任何操作
     * 如果key不是列表类型,返回一个错误
     * @param condition 满足这个条件才linsertAfter
     * @param key redis键
     * @param after 在这个元素后插入
     * @param value 待插入的元素
     * @return 如果命令执行成功,返回插入操作完成之后,列表的长度;如果没有找到指定元素,返回-1;如果key不存在或为空列表,返回0
     */
    public static Long linsertAfter(boolean condition, byte[] key, byte[] after, byte[] value){
        if(condition){
            return linsertAfter(key, after, value);
        }
        return null;
    }

    /**
     * 获取列表长度
     * 如果列表key不存在,则key被解释为一个空列表,返回0
     * 如果key不是列表类型,返回一个错误
     * @param key redis键
     * @return 列表的长度
     */
    public static Long llen(String key) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.llen(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取列表长度
     * 如果列表key不存在,则key被解释为一个空列表,返回0
     * 如果key不是列表类型,返回一个错误
     * @param key redis键
     * @return 列表的长度
     */
    public static Long llen(byte[] key) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.llen(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移出并获取列表的第一个元素
     * @param key redis键
     * @return 列表的第一个元素.当列表key不存在时,返回nil
     */
    public static String lpop(String key) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.lpop(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移出并获取列表的第一个元素
     * @param condition 满足这个条件才lpop
     * @param key redis键
     * @return 列表的第一个元素.当列表key不存在时,返回nil
     */
    public static String lpop(boolean condition, String key) {
        if(condition){
            return lpop(key);
        }
        return null;
    }

    /**
     * 移出并获取列表的第一个元素
     * @param key redis键
     * @return 列表的第一个元素.当列表key不存在时,返回nil
     */
    public static byte[] lpop(byte[] key) {
        byte[] result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.lpop(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移出并获取列表的第一个元素
     * @param condition 满足这个条件才lpop
     * @param key redis键
     * @return 列表的第一个元素.当列表key不存在时,返回nil
     */
    public static byte[] lpop(boolean condition, byte[] key) {
        if(condition){
            return lpop(key);
        }
        return null;
    }

    /**
     * 将一个或多个值插入到列表头部
     * 如果key不存在,一个空列表会被创建并执行LPUSH操作
     * 当key存在但不是列表类型时,返回一个错误
     * 注意:在Redis2.4版本以前的LPUSH命令,都只接受单个value值
     * @param key redis键
     * @param values redis值
     * @return 执行LPUSH命令后,列表的长度
     */
    public static Long lpush(String key, String... values) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.lpush(key, values);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将一个或多个值插入到列表头部
     * 如果key不存在,一个空列表会被创建并执行LPUSH操作
     * 当key存在但不是列表类型时,返回一个错误
     * 注意:在Redis2.4版本以前的LPUSH命令,都只接受单个value值
     * @param condition 满足这个条件才lpush
     * @param key redis键
     * @param values redis值
     * @return 执行LPUSH命令后,列表的长度
     */
    public static Long lpush(boolean condition, String key, String... values) {
        if(condition){
            return lpush(key, values);
        }
        return null;
    }

    /**
     * 将一个或多个值插入到列表头部
     * 如果key不存在,一个空列表会被创建并执行LPUSH操作
     * 当key存在但不是列表类型时,返回一个错误
     * 注意:在Redis2.4版本以前的LPUSH命令,都只接受单个value值
     * @param key redis键
     * @param values redis值
     * @return 执行LPUSH命令后,列表的长度
     */
    public static Long lpush(byte[] key, byte[] ... values) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.lpush(key, values);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将一个或多个值插入到列表头部
     * 如果key不存在,一个空列表会被创建并执行LPUSH操作
     * 当key存在但不是列表类型时,返回一个错误
     * 注意:在Redis2.4版本以前的LPUSH命令,都只接受单个value值
     * @param condition 满足这个条件才lpush
     * @param key redis键
     * @param values redis值
     * @return 执行LPUSH命令后,列表的长度
     */
    public static Long lpush(boolean condition, byte[] key, byte[] ... values) {
        if(condition){
            return lpush(key, values);
        }
        return null;
    }

    /**
     * 将一个值插入到已存在的列表头部,列表不存在时操作无效
     * @param key redis键
     * @param values redis值
     * @return LPUSHX命令执行之后,列表的长度
     */
    public static Long lpushx(String key, String... values) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.lpushx(key, values);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将一个值插入到已存在的列表头部,列表不存在时操作无效
     * @param condition 满足这个条件才lpushx
     * @param key redis键
     * @param values redis值
     * @return LPUSHX命令执行之后,列表的长度
     */
    public static Long lpushx(boolean condition, String key, String... values) {
        if(condition){
            return lpushx(key, values);
        }
        return null;
    }

    /**
     * 将一个值插入到已存在的列表头部,列表不存在时操作无效
     * @param key redis键
     * @param values redis值
     * @return LPUSHX命令执行之后,列表的长度
     */
    public static Long lpushx(byte[] key, byte[] ... values) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.lpushx(key, values);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将一个值插入到已存在的列表头部,列表不存在时操作无效
     * @param condition 满足这个条件才lpushx
     * @param key redis键
     * @param values redis值
     * @return LPUSHX命令执行之后,列表的长度
     */
    public static Long lpushx(boolean condition, byte[] key, byte[] ... values) {
        if(condition){
            return lpushx(key, values);
        }
        return null;
    }

    /**
     * 获取列表中指定区间内的元素,区间以偏移量start和end指定
     * 其中0表示列表的第一个元素;1表示列表的第二个元素,以此类推
     * 你也可以使用负数下标,以-1表示列表的最后一个元素,-2表示列表的倒数第二个元素,以此类推
     * @param key redis键
     * @param start 偏移开始
     * @param end 偏移结束
     * @return 一个列表,包含指定区间内的元素
     */
    public static List<String> lrange(String key, long start, long end) {
        List<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.lrange(key, start, end);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取列表中指定区间内的元素,区间以偏移量start和end指定
     * 其中0表示列表的第一个元素;1表示列表的第二个元素,以此类推
     * 你也可以使用负数下标,以-1表示列表的最后一个元素,-2表示列表的倒数第二个元素,以此类推
     * @param key redis键
     * @param start 偏移开始
     * @param end 偏移结束
     * @return 一个列表,包含指定区间内的元素
     */
    public static List<byte[]> lrange(byte[] key, long start, long end) {
        List<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.lrange(key, start, end);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除列表中与value相等的count个元素
     * count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT
     * count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值
     * count = 0 : 移除表中所有与 VALUE 相等的值
     * @param key redis键
     * @param count 数量
     * @param value redis值
     * @return 被移除元素的数量.列表不存在时返回0
     */
    public static Long lrem(String key, long count, String value) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.lrem(key, count, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除列表中与value相等的count个元素
     * count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT
     * count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值
     * count = 0 : 移除表中所有与 VALUE 相等的值
     * @param condition 满足这个条件才lrem
     * @param key redis键
     * @param count 数量
     * @param value redis值
     * @return 被移除元素的数量.列表不存在时返回0
     */
    public static Long lrem(boolean condition, String key, long count, String value) {
        if(condition){
            return lrem(key, count, value);
        }
        return null;
    }

    /**
     * 移除列表中与value相等的count个元素
     * count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT
     * count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值
     * count = 0 : 移除表中所有与 VALUE 相等的值
     * @param key redis键
     * @param count 数量
     * @param value redis值
     * @return 被移除元素的数量.列表不存在时返回0
     */
    public static Long lrem(byte[] key, long count, byte[] value) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.lrem(key, count, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除列表中与value相等的count个元素
     * count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT
     * count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值
     * count = 0 : 移除表中所有与 VALUE 相等的值
     * @param condition 满足这个条件才lrem
     * @param key redis键
     * @param count 数量
     * @param value redis值
     * @return 被移除元素的数量.列表不存在时返回0
     */
    public static Long lrem(boolean condition, byte[] key, long count, byte[] value) {
        if(condition){
            return lrem(key, count, value);
        }
        return null;
    }

    /**
     * 通过索引设置列表元素的值
     * 当索引参数超出范围,或对一个空列表进行LSET时,返回一个错误
     * @param key redis键
     * @param index 索引
     * @param value redis值
     * @return 操作成功返回ok,否则返回错误信息
     */
    public static String lset(String key, long index, String value){
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.lset(key, index, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 通过索引设置列表元素的值
     * 当索引参数超出范围,或对一个空列表进行LSET时,返回一个错误
     * @param condition 满足这个条件才lset
     * @param key redis键
     * @param index 索引
     * @param value redis值
     * @return 操作成功返回ok,否则返回错误信息
     */
    public static String lset(boolean condition, String key, long index, String value){
        if(condition){
            return lset(key, index, value);
        }
        return null;
    }

    /**
     * 通过索引设置列表元素的值
     * 当索引参数超出范围,或对一个空列表进行LSET时,返回一个错误
     * @param key redis键
     * @param index 索引
     * @param value redis值
     * @return 操作成功返回ok,否则返回错误信息
     */
    public static String lset(byte[] key, long index, byte[] value){
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.lset(key, index, value);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 通过索引设置列表元素的值
     * 当索引参数超出范围,或对一个空列表进行LSET时,返回一个错误
     * @param condition 满足这个条件才lset
     * @param key redis键
     * @param index 索引
     * @param value redis值
     * @return 操作成功返回ok,否则返回错误信息
     */
    public static String lset(boolean condition, byte[] key, long index, byte[] value){
        if(condition){
            return lset(key, index, value);
        }
        return null;
    }

    /**
     * 对一个列表进行修剪(trim) - 让列表只保留指定区间内的元素,不在指定区间之内的元素都将被删除
     * 下标0表示列表的第一个元素,1表示列表的第二个元素,以此类推
     * 你也可以使用负数下标,-1表示列表的最后一个元素,-2表示列表的倒数第二个元素,以此类推
     * @param key redis键
     * @param start 开始索引
     * @param end 结束索引
     * @return 执行成功返回ok
     */
    public static String ltrim(String key, long start, long end){
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.ltrim(key, start, end);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 对一个列表进行修剪(trim) - 让列表只保留指定区间内的元素,不在指定区间之内的元素都将被删除
     * 下标0表示列表的第一个元素,1表示列表的第二个元素,以此类推
     * 你也可以使用负数下标,-1表示列表的最后一个元素,-2表示列表的倒数第二个元素,以此类推
     * @param condition 满足这个条件才ltrim
     * @param key redis键
     * @param start 开始索引
     * @param end 结束索引
     * @return 执行成功返回ok
     */
    public static String ltrim(boolean condition, String key, long start, long end){
        if(condition){
            return ltrim(key, start, end);
        }
        return null;
    }

    /**
     * 对一个列表进行修剪(trim) - 让列表只保留指定区间内的元素,不在指定区间之内的元素都将被删除
     * 下标0表示列表的第一个元素,1表示列表的第二个元素,以此类推
     * 你也可以使用负数下标,-1表示列表的最后一个元素,-2表示列表的倒数第二个元素,以此类推
     * @param key redis键
     * @param start 开始索引
     * @param end 结束索引
     * @return 执行成功返回ok
     */
    public static String ltrim(byte[] key, long start, long end){
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.ltrim(key, start, end);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 对一个列表进行修剪(trim) - 让列表只保留指定区间内的元素,不在指定区间之内的元素都将被删除
     * 下标0表示列表的第一个元素,1表示列表的第二个元素,以此类推
     * 你也可以使用负数下标,-1表示列表的最后一个元素,-2表示列表的倒数第二个元素,以此类推
     * @param condition 满足这个条件才ltrim
     * @param key redis键
     * @param start 开始索引
     * @param end 结束索引
     * @return 执行成功返回ok
     */
    public static String ltrim(boolean condition, byte[] key, long start, long end){
        if(condition){
            return ltrim(key, start, end);
        }
        return null;
    }

    /**
     * 移除列表的最后一个元素,返回值为移除的元素
     * @param key redis键
     * @return 被移除的元素;当列表不存在时,返回nil
     */
    public static String rpop(String key) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.rpop(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除列表的最后一个元素,返回值为移除的元素
     * @param condition 满足这个条件才rpop
     * @param key redis键
     * @return 被移除的元素;当列表不存在时,返回nil
     */
    public static String rpop(boolean condition, String key) {
        if(condition){
            return rpop(key);
        }
        return null;
    }

    /**
     * 移除列表的最后一个元素,返回值为移除的元素
     * @param key redis键
     * @return 被移除的元素;当列表不存在时,返回nil
     */
    public static byte[] rpop(byte[] key) {
        byte[] result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.rpop(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除列表的最后一个元素,返回值为移除的元素
     * @param condition 满足这个条件才rpop
     * @param key redis键
     * @return 被移除的元素;当列表不存在时,返回nil
     */
    public static byte[] rpop(boolean condition, byte[] key) {
        if(condition){
            return rpop(key);
        }
        return null;
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     * @param source redis键 - 源头
     * @param destination redis键 - 目标
     * @return 被弹出的元素
     */
    public static String rpoplpush(String source, String destination) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.rpoplpush(source, destination);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     * @param condition 满足这个条件才rpoplpush
     * @param source redis键 - 源头
     * @param destination redis键 - 目标
     * @return 被弹出的元素
     */
    public static String rpoplpush(boolean condition, String source, String destination) {
        if(condition){
            return rpoplpush(source, destination);
        }
        return null;
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     * @param source redis键 - 源头
     * @param destination redis键 - 目标
     * @return 被弹出的元素
     */
    public static byte[] rpoplpush(byte[] source, byte[] destination) {
        byte[] result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.rpoplpush(source, destination);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     * @param condition 满足这个条件才rpoplpush
     * @param source redis键 - 源头
     * @param destination redis键 - 目标
     * @return 被弹出的元素
     */
    public static byte[] rpoplpush(boolean condition, byte[] source, byte[] destination) {
        if(condition){
            return rpoplpush(source, destination);
        }
        return null;
    }

    /**
     * 将一个或多个值插入到列表的尾部(最右边)
     * 如果列表不存在,一个空列表会被创建并执行RPUSH操作
     * 当列表存在但不是列表类型时,返回一个错误
     * 注意:Redis2.4版本以前的RPUSH命令,都只接受单个value值
     * @param key redis键
     * @param values redis值
     * @return 执行插入后的列表的长度
     */
    public static Long rpush(String key, String... values) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.rpush(key, values);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将一个或多个值插入到列表的尾部(最右边)
     * 如果列表不存在,一个空列表会被创建并执行RPUSH操作
     * 当列表存在但不是列表类型时,返回一个错误
     * 注意:Redis2.4版本以前的RPUSH命令,都只接受单个value值
     * @param condition 满足这个条件才rpush
     * @param key redis键
     * @param values redis值
     * @return 执行插入后的列表的长度
     */
    public static Long rpush(boolean condition, String key, String... values) {
        if(condition){
            return rpush(key, values);
        }
        return null;
    }

    /**
     * 将一个或多个值插入到列表的尾部(最右边)
     * 如果列表不存在,一个空列表会被创建并执行RPUSH操作
     * 当列表存在但不是列表类型时,返回一个错误
     * 注意:Redis2.4版本以前的RPUSH命令,都只接受单个value值
     * @param key redis键
     * @param values redis值
     * @return 执行插入后的列表的长度
     */
    public static Long rpush(byte[] key, byte[] ... values) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.rpush(key, values);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将一个或多个值插入到列表的尾部(最右边)
     * 如果列表不存在,一个空列表会被创建并执行RPUSH操作
     * 当列表存在但不是列表类型时,返回一个错误
     * 注意:Redis2.4版本以前的RPUSH命令,都只接受单个value值
     * @param condition 满足这个条件才rpush
     * @param key redis键
     * @param values redis值
     * @return 执行插入后的列表的长度
     */
    public static Long rpush(boolean condition, byte[] key, byte[] ... values) {
        if(condition){
            return rpush(key, values);
        }
        return null;
    }

    /**
     * 将一个值插入到已存在的列表尾部(最右边)
     * 如果列表不存在,操作无效
     * @param key redis键
     * @param values redis值
     * @return 执行插入后的列表的长度
     */
    public static Long rpushx(String key, String... values) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.rpushx(key, values);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将一个值插入到已存在的列表尾部(最右边)
     * 如果列表不存在,操作无效
     * @param condition 满足这个条件才rpushx
     * @param key redis键
     * @param values redis值
     * @return 执行插入后的列表的长度
     */
    public static Long rpushx(boolean condition, String key, String... values) {
        if(condition){
            return rpushx(key, values);
        }
        return null;
    }

    /**
     * 将一个值插入到已存在的列表尾部(最右边)
     * 如果列表不存在,操作无效
     * @param key redis键
     * @param values redis值
     * @return 执行插入后的列表的长度
     */
    public static Long rpushx(byte[] key, byte[] ... values) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.rpushx(key, values);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将一个值插入到已存在的列表尾部(最右边)
     * 如果列表不存在,操作无效
     * @param condition 满足这个条件才rpushx
     * @param key redis键
     * @param values redis值
     * @return 执行插入后的列表的长度
     */
    public static Long rpushx(boolean condition, byte[] key, byte[] ... values) {
        if(condition){
            return rpushx(key, values);
        }
        return null;
    }

    //**********          redis集合数据操作的方法          **********//

    /**
     * 将一个或多个成员元素加入到集合中,已经存在于集合的成员元素将被忽略
     * 假如集合key不存在,则创建一个只包含添加的元素作成员的集合
     * 当集合key不是集合类型时,返回一个错误
     * 注意:Redis2.4版本以前,SADD只接受单个成员值
     * @param key redis键
     * @param values redis值
     * @return 被添加到集合中的新元素的数量,不包括被忽略的元素
     */
    public static Long sadd(String key, String ... values) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.sadd(key, values);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将一个或多个成员元素加入到集合中,已经存在于集合的成员元素将被忽略
     * 假如集合key不存在,则创建一个只包含添加的元素作成员的集合
     * 当集合key不是集合类型时,返回一个错误
     * 注意:Redis2.4版本以前,SADD只接受单个成员值
     * @param condition 满足这个条件才sadd
     * @param key redis键
     * @param values redis值
     * @return 被添加到集合中的新元素的数量,不包括被忽略的元素
     */
    public static Long sadd(boolean condition, String key, String ... values) {
        if(condition){
            return sadd(key, values);
        }
        return null;
    }

    /**
     * 将一个或多个成员元素加入到集合中,已经存在于集合的成员元素将被忽略
     * 假如集合key不存在,则创建一个只包含添加的元素作成员的集合
     * 当集合key不是集合类型时,返回一个错误
     * 注意:Redis2.4版本以前,SADD只接受单个成员值
     * @param key redis键
     * @param values redis值
     * @return 被添加到集合中的新元素的数量,不包括被忽略的元素
     */
    public static Long sadd(byte[] key, byte[] ... values) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.sadd(key, values);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将一个或多个成员元素加入到集合中,已经存在于集合的成员元素将被忽略
     * 假如集合key不存在,则创建一个只包含添加的元素作成员的集合
     * 当集合key不是集合类型时,返回一个错误
     * 注意:Redis2.4版本以前,SADD只接受单个成员值
     * @param condition 满足这个条件才sadd
     * @param key redis键
     * @param values redis值
     * @return 被添加到集合中的新元素的数量,不包括被忽略的元素
     */
    public static Long sadd(boolean condition, byte[] key, byte[] ... values) {
        if(condition){
            return sadd(key, values);
        }
        return null;
    }

    /**
     * 获取集合中元素的数量
     * @param key redis键
     * @return 集合的数量.当集合key不存在时,返回0
     */
    public static Long scard(String key) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.scard(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取集合中元素的数量
     * @param key redis键
     * @return 集合的数量.当集合key不存在时,返回0
     */
    public static Long scard(byte[] key) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.scard(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回第一个集合与其他集合之间的差异
     * 也可以认为第一个集合中独有的元素,不存在的集合key将视为空集
     * 差集的结果来自keys的第一个key,而不是后面的其他key,也不是整个keys的差集
     * @param keys redis键
     * @return 包含差集成员的列表
     */
    public static Set<String> sdiff(String ... keys) {
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.sdiff(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回第一个集合与其他集合之间的差异
     * 也可以认为第一个集合中独有的元素,不存在的集合key将视为空集
     * 差集的结果来自keys的第一个key,而不是后面的其他key,也不是整个keys的差集
     * @param keys redis键
     * @return 包含差集成员的列表
     */
    public static Set<byte[]> sdiff(byte[] ... keys) {
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.sdiff(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回给定所有集合的差集并存储在destination中
     * 如果指定的集合destination已存在,则会被覆盖
     * @param destination 存储键
     * @param keys redis键
     * @return 结果集中的元素数量
     */
    public static Long sdiffstore(String destination, String ... keys) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.sdiffstore(destination, keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回给定所有集合的差集并存储在destination中
     * 如果指定的集合destination已存在,则会被覆盖
     * @param condition 满足这个条件才sdiffstore
     * @param destination 存储键
     * @param keys redis键
     * @return 结果集中的元素数量
     */
    public static Long sdiffstore(boolean condition, String destination, String ... keys) {
        if(condition){
            return sdiffstore(destination, keys);
        }
        return null;
    }

    /**
     * 返回给定所有集合的差集并存储在destination中
     * 如果指定的集合destination已存在,则会被覆盖
     * @param destination 存储键
     * @param keys redis键
     * @return 结果集中的元素数量
     */
    public static Long sdiffstore(byte[] destination, byte[] ... keys) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.sdiffstore(destination, keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回给定所有集合的差集并存储在destination中
     * 如果指定的集合destination已存在,则会被覆盖
     * @param condition 满足这个条件才sdiffstore
     * @param destination 存储键
     * @param keys redis键
     * @return 结果集中的元素数量
     */
    public static Long sdiffstore(boolean condition, byte[] destination, byte[] ... keys) {
        if(condition){
            return sdiffstore(destination, keys);
        }
        return null;
    }

    /**
     * 返回给定所有集合的交集
     * 不存在的集合key被视为空集
     * 当给定集合当中有一个空集时,结果也为空集(根据集合运算定律)
     * @param keys redis键
     * @return 交集成员的列表
     */
    public static Set<String> sinter(String ... keys){
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.sinter(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回给定所有集合的交集
     * 不存在的集合key被视为空集
     * 当给定集合当中有一个空集时,结果也为空集(根据集合运算定律)
     * @param keys redis键
     * @return 交集成员的列表
     */
    public static Set<byte[]> sinter(byte[] ... keys){
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.sinter(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将给定集合之间的交集存储在指定的集合中
     * 如果指定的集合已经存在,则将其覆盖
     * @param destination 存储键
     * @param keys redis键
     * @return 返回存储交集的集合的元素数量
     */
    public static Long sinterstore(String destination, String ... keys){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.sinterstore(destination, keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将给定集合之间的交集存储在指定的集合中
     * 如果指定的集合已经存在,则将其覆盖
     * @param condition 满足这个条件才sinterstore
     * @param destination 存储键
     * @param keys redis键
     * @return 返回存储交集的集合的元素数量
     */
    public static Long sinterstore(boolean condition, String destination, String ... keys){
        if(condition){
            return sinterstore(destination, keys);
        }
        return null;
    }

    /**
     * 将给定集合之间的交集存储在指定的集合中
     * 如果指定的集合已经存在,则将其覆盖
     * @param destination 存储键
     * @param keys redis键
     * @return 返回存储交集的集合的元素数量
     */
    public static Long sinterstore(byte[] destination, byte[] ... keys){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.sinterstore(destination, keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将给定集合之间的交集存储在指定的集合中
     * 如果指定的集合已经存在,则将其覆盖
     * @param condition 满足这个条件才sinterstore
     * @param destination 存储键
     * @param keys redis键
     * @return 返回存储交集的集合的元素数量
     */
    public static Long sinterstore(boolean condition, byte[] destination, byte[] ... keys){
        if(condition){
            return sinterstore(destination, keys);
        }
        return null;
    }

    /**
     * 返回所有给定集合的并集
     * 不存在的集合key被视为空集
     * @param keys redis键
     * @return 并集成员的列表
     */
    public static Set<String> sunion(String ... keys){
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.sunion(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回所有给定集合的并集
     * 不存在的集合key被视为空集
     * @param keys redis键
     * @return 并集成员的列表
     */
    public static Set<byte[]> sunion(byte[] ... keys){
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.sunion(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 所有给定集合的并集存储在destination集合中
     * 如果destination已经存在,则将其覆盖
     * @param destination 存储键
     * @param keys redis键
     * @return 结果集中的元素数量
     */
    public static Long sunionstore(String destination, String ... keys){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.sunionstore(destination, keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 所有给定集合的并集存储在destination集合中
     * 如果destination已经存在,则将其覆盖
     * @param condition 满足这个条件才sunionstore
     * @param destination 存储键
     * @param keys redis键
     * @return 结果集中的元素数量
     */
    public static Long sunionstore(boolean condition, String destination, String ... keys){
        if(condition){
            return sunionstore(destination, keys);
        }
        return null;
    }

    /**
     * 所有给定集合的并集存储在destination集合中
     * 如果destination已经存在,则将其覆盖
     * @param destination 存储键
     * @param keys redis键
     * @return 结果集中的元素数量
     */
    public static Long sunionstore(byte[] destination, byte[] ... keys){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.sunionstore(destination, keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 所有给定集合的并集存储在destination集合中
     * 如果destination已经存在,则将其覆盖
     * @param condition 满足这个条件才sunionstore
     * @param destination 存储键
     * @param keys redis键
     * @return 结果集中的元素数量
     */
    public static Long sunionstore(boolean condition, byte[] destination, byte[] ... keys){
        if(condition){
            return sunionstore(destination, keys);
        }
        return null;
    }

    /**
     * 判断成员元素是否是集合的成员
     * @param key redis键
     * @param member 是否有这个值
     * @return 如果成员元素是集合的成员,返回1;如果成员元素不是集合的成员,或key不存在,返回0
     */
    public static Boolean sismember(String key, String member) {
        Boolean result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.sismember(key, member);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 判断成员元素是否是集合的成员
     * @param key redis键
     * @param member 是否有这个值
     * @return 如果成员元素是集合的成员,返回1;如果成员元素不是集合的成员,或key不存在,返回0
     */
    public static Boolean sismember(byte[] key, byte[] member) {
        Boolean result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.sismember(key, member);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回集合中的所有的成员
     * 不存在的集合key被视为空集合
     * @param key redis键
     * @return 集合中的所有成员
     */
    public static Set<String> smembers(String key) {
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.smembers(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回集合中的所有的成员
     * 不存在的集合key被视为空集合
     * @param key redis键
     * @return 集合中的所有成员
     */
    public static Set<byte[]> smembers(byte[] key) {
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.smembers(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将指定成员member元素从source集合移动到destination集合
     * SMOVE是原子性操作
     * 如果source集合不存在或不包含指定的member元素,则SMOVE命令不执行任何操作,仅返回0
     * 否则,member元素从source集合中被移除,并添加到destination集合中去
     * 当destination集合已经包含member元素时,SMOVE命令只是简单地将source集合中的member元素删除
     * 当source或destination不是集合类型时,返回一个错误
     * @param source 源
     * @param destination 目标
     * @param member 元素
     * @return 如果成员元素被成功移除,返回1;如果成员元素不是source集合的成员,并且没有任何操作对destination集合执行,那么返回0
     */
    public static Long smove(String source, String destination, String member) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.smove(source, destination, member);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将指定成员member元素从source集合移动到destination集合
     * SMOVE是原子性操作
     * 如果source集合不存在或不包含指定的member元素,则SMOVE命令不执行任何操作,仅返回0
     * 否则,member元素从source集合中被移除,并添加到destination集合中去
     * 当destination集合已经包含member元素时,SMOVE命令只是简单地将source集合中的member元素删除
     * 当source或destination不是集合类型时,返回一个错误
     * @param condition 满足这个条件才smove
     * @param source 源
     * @param destination 目标
     * @param member 元素
     * @return 如果成员元素被成功移除,返回1;如果成员元素不是source集合的成员,并且没有任何操作对destination集合执行,那么返回0
     */
    public static Long smove(boolean condition, String source, String destination, String member) {
        if(condition){
            return smove(source, destination, member);
        }
        return null;
    }

    /**
     * 将指定成员member元素从source集合移动到destination集合
     * SMOVE是原子性操作
     * 如果source集合不存在或不包含指定的member元素,则SMOVE命令不执行任何操作,仅返回0
     * 否则,member元素从source集合中被移除,并添加到destination集合中去
     * 当destination集合已经包含member元素时,SMOVE命令只是简单地将source集合中的member元素删除
     * 当source或destination不是集合类型时,返回一个错误
     * @param source 源
     * @param destination 目标
     * @param member 元素
     * @return 如果成员元素被成功移除,返回1;如果成员元素不是source集合的成员,并且没有任何操作对destination集合执行,那么返回0
     */
    public static Long smove(byte[] source, byte[] destination, byte[] member) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.smove(source, destination, member);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将指定成员member元素从source集合移动到destination集合
     * SMOVE是原子性操作
     * 如果source集合不存在或不包含指定的member元素,则SMOVE命令不执行任何操作,仅返回0
     * 否则,member元素从source集合中被移除,并添加到destination集合中去
     * 当destination集合已经包含member元素时,SMOVE命令只是简单地将source集合中的member元素删除
     * 当source或destination不是集合类型时,返回一个错误
     * @param condition 满足这个条件才smove
     * @param source 源
     * @param destination 目标
     * @param member 元素
     * @return 如果成员元素被成功移除,返回1;如果成员元素不是source集合的成员,并且没有任何操作对destination集合执行,那么返回0
     */
    public static Long smove(boolean condition, byte[] source, byte[] destination, byte[] member) {
        if(condition){
            return smove(source, destination, member);
        }
        return null;
    }

    /**
     * 移除并返回集合中的一个随机元素
     * @param key redis键
     * @return 被移除的随机元素.当集合不存在或是空集时,返回nil
     */
    public static String spop(String key) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.spop(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除并返回集合中的一个随机元素
     * @param condition 满足这个条件才spop
     * @param key redis键
     * @return 被移除的随机元素.当集合不存在或是空集时,返回nil
     */
    public static String spop(boolean condition, String key) {
        if(condition){
            return spop(key);
        }
        return null;
    }

    /**
     * 移除并返回集合中的多个随机元素
     * @param key redis键
     * @param count 限制个数
     * @return 被移除的随机元素.当集合不存在或是空集时,返回nil
     */
    public static Set<String> spop(String key, int count) {
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.spop(key, count);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除并返回集合中的多个随机元素
     * @param condition 满足这个条件才spop
     * @param key redis键
     * @param count 限制个数
     * @return 被移除的随机元素.当集合不存在或是空集时,返回nil
     */
    public static Set<String> spop(boolean condition, String key, int count) {
        if(condition){
            return spop(key, count);
        }
        return null;
    }

    /**
     * 移除并返回集合中的一个随机元素
     * @param key redis键
     * @return 被移除的随机元素.当集合不存在或是空集时,返回nil
     */
    public static byte[] spop(byte[] key) {
        byte[] result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.spop(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除并返回集合中的一个随机元素
     * @param condition 满足这个条件才spop
     * @param key redis键
     * @return 被移除的随机元素.当集合不存在或是空集时,返回nil
     */
    public static byte[] spop(boolean condition, byte[] key) {
        if(condition){
            return spop(key);
        }
        return null;
    }

    /**
     * 移除并返回集合中的多个随机元素
     * @param key redis键
     * @param count 限制个数
     * @return 被移除的随机元素.当集合不存在或是空集时,返回nil
     */
    public static Set<byte[]> spop(byte[] key, int count) {
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.spop(key, count);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除并返回集合中的多个随机元素
     * @param condition 满足这个条件才spop
     * @param key redis键
     * @param count 限制个数
     * @return 被移除的随机元素.当集合不存在或是空集时,返回nil
     */
    public static Set<byte[]> spop(boolean condition, byte[] key, int count) {
        if(condition){
            return spop(key, count);
        }
        return null;
    }

    /**
     * 返回集合中一个随机元素
     * @param key redis键
     * @return 返回一个随机元素.如果集合为空,返回nil
     */
    public static String srandmember(String key) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.srandmember(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回集合中一个或多个随机元素
     * @param key redis键
     * @param count 限制个数
     * @return 返回一个数组.如果集合为空,返回空数组
     */
    public static List<String> srandmember(String key, int count) {
        List<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.srandmember(key, count);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回集合中一个随机元素
     * @param key redis键
     * @return 返回一个随机元素.如果集合为空,返回nil
     */
    public static byte[] srandmember(byte[] key) {
        byte[] result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.srandmember(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回集合中一个或多个随机元素
     * @param key redis键
     * @param count 限制个数
     * @return 返回一个数组.如果集合为空,返回空数组
     */
    public static List<byte[]> srandmember(byte[] key, int count) {
        List<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.srandmember(key, count);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除集合中一个或多个成员,不存在的成员元素会被忽略
     * 当key不是集合类型,返回一个错误
     * Redis2.4版本以前,SREM只接受单个成员值
     * @param key redis键
     * @param members 元素
     * @return 被成功移除的元素的数量,不包括被忽略的元素
     */
    public static Long srem(String key, String ... members) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.srem(key, members);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除集合中一个或多个成员,不存在的成员元素会被忽略
     * 当key不是集合类型,返回一个错误
     * Redis2.4版本以前,SREM只接受单个成员值
     * @param condition 满足这个条件才srem
     * @param key redis键
     * @param members 元素
     * @return 被成功移除的元素的数量,不包括被忽略的元素
     */
    public static Long srem(boolean condition, String key, String ... members) {
        if(condition){
            return srem(key, members);
        }
        return null;
    }

    /**
     * 移除集合中一个或多个成员,不存在的成员元素会被忽略
     * 当key不是集合类型,返回一个错误
     * Redis2.4版本以前,SREM只接受单个成员值
     * @param key redis键
     * @param members 元素
     * @return 被成功移除的元素的数量,不包括被忽略的元素
     */
    public static Long srem(byte[] key, byte[] ... members) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.srem(key, members);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除集合中一个或多个成员,不存在的成员元素会被忽略
     * 当key不是集合类型,返回一个错误
     * Redis2.4版本以前,SREM只接受单个成员值
     * @param condition 满足这个条件才srem
     * @param key redis键
     * @param members 元素
     * @return 被成功移除的元素的数量,不包括被忽略的元素
     */
    public static Long srem(boolean condition, byte[] key, byte[] ... members) {
        if(condition){
            return srem(key, members);
        }
        return null;
    }

    /**
     * 迭代数据库中的数据库键
     * @param key redis键
     * @return 数组列表
     */
    public static ScanResult<String> sscan(String key){
        return sscan(key, ScanParams.SCAN_POINTER_START, "", 0);
    }

    /**
     * 迭代数据库中的数据库键
     * @param key redis键
     * @param cursor 游标
     * @return 数组列表
     */
    public static ScanResult<String> sscan(String key, String cursor){
        return sscan(key, cursor, "", 0);
    }

    /**
     * 迭代数据库中的数据库键
     * @param key redis键
     * @param cursor 游标
     * @param pattern 表达式
     * @return 数组列表
     */
    public static ScanResult<String> sscan(String key, String cursor, String pattern){
        return sscan(key, cursor, pattern, 0);
    }

    /**
     * 迭代数据库中的数据库键
     * @param key redis键
     * @param cursor 游标
     * @param count 限制数量
     * @return 数组列表
     */
    public static ScanResult<String> sscan(String key, String cursor, int count){
        return sscan(key, cursor, "", count);
    }

    /**
     * 迭代数据库中的数据库键
     * @param key redis键
     * @param cursor 游标
     * @param pattern 表达式
     * @param count 限制数量
     * @return 数组列表
     */
    public static ScanResult<String> sscan(String key, String cursor, String pattern, int count){
        ScanResult<String> result = null;
        Jedis jedis = null;
        try {
            ScanParams params = new ScanParams();
            if(!StringUtils.isEmpty(pattern)){
                params.match(pattern);
            }
            if(count > 0){
                params.count(count);
            }
            jedis = getJedis();
            result = jedis.sscan(key, cursor, params);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 迭代数据库中的数据库键
     * @param key redis键
     * @return 数组列表
     */
    public static ScanResult<byte[]> sscanBinary(byte[] key){
        return sscanBinary(key, ScanParams.SCAN_POINTER_START_BINARY, null, 0);
    }

    /**
     * 迭代数据库中的数据库键
     * @param key redis键
     * @param cursor 游标
     * @return 数组列表
     */
    public static ScanResult<byte[]> sscanBinary(byte[] key, byte[] cursor){
        return sscanBinary(key, cursor, null, 0);
    }

    /**
     * 迭代数据库中的数据库键
     * @param key redis键
     * @param cursor 游标
     * @param pattern 表达式
     * @return 数组列表
     */
    public static ScanResult<byte[]> sscanBinary(byte[] key, byte[] cursor, byte[] pattern){
        return sscanBinary(key, cursor, pattern, 0);
    }

    /**
     * 迭代数据库中的数据库键
     * @param key redis键
     * @param cursor 游标
     * @param count 限制数量
     * @return 数组列表
     */
    public static ScanResult<byte[]> sscanBinary(byte[] key, byte[] cursor, int count){
        return sscanBinary(key, cursor, null, count);
    }

    /**
     * 迭代数据库中的数据库键
     * @param key redis键
     * @param cursor 游标
     * @param pattern 表达式
     * @param count 限制数量
     * @return 数组列表
     */
    public static ScanResult<byte[]> sscanBinary(byte[] key, byte[] cursor, byte[] pattern, int count){
        ScanResult<byte[]> result = null;
        Jedis jedis = null;
        try {
            ScanParams params = new ScanParams();
            if(!StringUtils.isEmpty(pattern)){
                params.match(pattern);
            }
            if(count > 0){
                params.count(count);
            }
            jedis = getJedis();
            result = jedis.sscan(key, cursor, params);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    //**********          redis有序集合数据操作的方法          **********//

    /**
     * 将一个成员元素及其分数值加入到有序集当中
     * 如果某个成员已经是有序集的成员,那么更新这个成员的分数值,并通过重新插入这个成员元素,来保证该成员在正确的位置上
     * 分数值可以是整数值或双精度浮点数
     * 如果有序集合key不存在,则创建一个空的有序集并执行ZADD操作
     * 当key存在但不是有序集类型时,返回一个错误
     * 注意:Redis2.4版本以前,ZADD每次只能添加一个元素
     * @param key redis键
     * @param score 分数
     * @param member 元素
     * @return 被成功添加的新成员的数量,不包括那些被更新的或者已经存在的成员
     */
    public static Long zadd(String key, double score, String member){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zadd(key, score, member);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将一个成员元素及其分数值加入到有序集当中
     * 如果某个成员已经是有序集的成员,那么更新这个成员的分数值,并通过重新插入这个成员元素,来保证该成员在正确的位置上
     * 分数值可以是整数值或双精度浮点数
     * 如果有序集合key不存在,则创建一个空的有序集并执行ZADD操作
     * 当key存在但不是有序集类型时,返回一个错误
     * 注意:Redis2.4版本以前,ZADD每次只能添加一个元素
     * @param condition 满足条件才zadd
     * @param key redis键
     * @param score 分数
     * @param member 元素
     * @return 被成功添加的新成员的数量,不包括那些被更新的或者已经存在的成员
     */
    public static Long zadd(boolean condition, String key, double score, String member){
        if(condition){
            return zadd(key, score, member);
        }
        return null;
    }

    /**
     * 将一个或多个成员元素及其分数值加入到有序集当中
     * 如果某个成员已经是有序集的成员,那么更新这个成员的分数值,并通过重新插入这个成员元素,来保证该成员在正确的位置上
     * 分数值可以是整数值或双精度浮点数
     * 如果有序集合key不存在,则创建一个空的有序集并执行ZADD操作
     * 当key存在但不是有序集类型时,返回一个错误
     * 注意:Redis2.4版本以前,ZADD每次只能添加一个元素
     * @param key redis键
     * @param members 元素和分数的map
     * @return 被成功添加的新成员的数量,不包括那些被更新的或者已经存在的成员
     */
    public static Long zadd(String key, Map<String, Double> members){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zadd(key, members);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将一个或多个成员元素及其分数值加入到有序集当中
     * 如果某个成员已经是有序集的成员,那么更新这个成员的分数值,并通过重新插入这个成员元素,来保证该成员在正确的位置上
     * 分数值可以是整数值或双精度浮点数
     * 如果有序集合key不存在,则创建一个空的有序集并执行ZADD操作
     * 当key存在但不是有序集类型时,返回一个错误
     * 注意:Redis2.4版本以前,ZADD每次只能添加一个元素
     * @param condition 满足条件才zadd
     * @param key redis键
     * @param members 元素和分数的map
     * @return 被成功添加的新成员的数量,不包括那些被更新的或者已经存在的成员
     */
    public static Long zadd(boolean condition, String key, Map<String, Double> members){
        if(condition){
            return zadd(key, members);
        }
        return null;
    }

    /**
     * 将一个成员元素及其分数值加入到有序集当中
     * 如果某个成员已经是有序集的成员,那么更新这个成员的分数值,并通过重新插入这个成员元素,来保证该成员在正确的位置上
     * 分数值可以是整数值或双精度浮点数
     * 如果有序集合key不存在,则创建一个空的有序集并执行ZADD操作
     * 当key存在但不是有序集类型时,返回一个错误
     * 注意:Redis2.4版本以前,ZADD每次只能添加一个元素
     * @param key redis键
     * @param score 分数
     * @param member 元素
     * @return 被成功添加的新成员的数量,不包括那些被更新的或者已经存在的成员
     */
    public static Long zadd(byte[] key, double score, byte[] member){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zadd(key, score, member);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将一个成员元素及其分数值加入到有序集当中
     * 如果某个成员已经是有序集的成员,那么更新这个成员的分数值,并通过重新插入这个成员元素,来保证该成员在正确的位置上
     * 分数值可以是整数值或双精度浮点数
     * 如果有序集合key不存在,则创建一个空的有序集并执行ZADD操作
     * 当key存在但不是有序集类型时,返回一个错误
     * 注意:Redis2.4版本以前,ZADD每次只能添加一个元素
     * @param condition 满足条件才zadd
     * @param key redis键
     * @param score 分数
     * @param member 元素
     * @return 被成功添加的新成员的数量,不包括那些被更新的或者已经存在的成员
     */
    public static Long zadd(boolean condition, byte[] key, double score, byte[] member){
        if(condition){
            return zadd(key, score, member);
        }
        return null;
    }

    /**
     * 将一个或多个成员元素及其分数值加入到有序集当中
     * 如果某个成员已经是有序集的成员,那么更新这个成员的分数值,并通过重新插入这个成员元素,来保证该成员在正确的位置上
     * 分数值可以是整数值或双精度浮点数
     * 如果有序集合key不存在,则创建一个空的有序集并执行ZADD操作
     * 当key存在但不是有序集类型时,返回一个错误
     * 注意:Redis2.4版本以前,ZADD每次只能添加一个元素
     * @param key redis键
     * @param members 元素和分数的map
     * @return 被成功添加的新成员的数量,不包括那些被更新的或者已经存在的成员
     */
    public static Long zadd(byte[] key, Map<byte[], Double> members){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zadd(key, members);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将一个或多个成员元素及其分数值加入到有序集当中
     * 如果某个成员已经是有序集的成员,那么更新这个成员的分数值,并通过重新插入这个成员元素,来保证该成员在正确的位置上
     * 分数值可以是整数值或双精度浮点数
     * 如果有序集合key不存在,则创建一个空的有序集并执行ZADD操作
     * 当key存在但不是有序集类型时,返回一个错误
     * 注意:Redis2.4版本以前,ZADD每次只能添加一个元素
     * @param condition 满足条件才zadd
     * @param key redis键
     * @param members 元素和分数的map
     * @return 被成功添加的新成员的数量,不包括那些被更新的或者已经存在的成员
     */
    public static Long zadd(boolean condition, byte[] key, Map<byte[], Double> members){
        if(condition){
            return zadd(key, members);
        }
        return null;
    }

    /**
     * 获取有序集合的成员数
     * @param key redis键
     * @return 当key存在且是有序集类型时,返回有序集的基数;当key不存在时,返回0
     */
    public static Long zcard(String key){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zcard(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 获取有序集合的成员数
     * @param key redis键
     * @return 当key存在且是有序集类型时,返回有序集的基数;当key不存在时,返回0
     */
    public static Long zcard(byte[] key){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zcard(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 计算在有序集合中指定区间分数的成员数
     * @param key redis键
     * @param min 最低分数
     * @param max 最高分数
     * @return 分数值在min和max之间的成员的数量
     */
    public static Long zcount(String key, double min, double max){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zcount(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 计算在有序集合中指定区间分数的成员数
     * @param key redis键
     * @param min 最低分数
     * @param max 最高分数
     * @return 分数值在min和max之间的成员的数量
     */
    public static Long zcount(String key, String min, String max){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zcount(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 计算在有序集合中指定区间分数的成员数
     * @param key redis键
     * @param min 最低分数
     * @param max 最高分数
     * @return 分数值在min和max之间的成员的数量
     */
    public static Long zcount(byte[] key, double min, double max){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zcount(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 计算在有序集合中指定区间分数的成员数
     * @param key redis键
     * @param min 最低分数
     * @param max 最高分数
     * @return 分数值在min和max之间的成员的数量
     */
    public static Long zcount(byte[] key, byte[] min, byte[] max){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zcount(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 对有序集合中指定成员的分数加上增量increment
     * 可以通过传递一个负数值increment,让分数减去相应的值
     * 当key不存在,或分数不是key的成员时,重新add一个成员
     * 当key不是有序集类型时,返回一个错误
     * 分数值可以是整数值或双精度浮点数
     * @param key redis键
     * @param increment 增量
     * @param member 元素
     * @return member成员的新分数值
     */
    public static Double zincrby(String key, double increment, String member){
        Double result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zincrby(key, increment, member);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 对有序集合中指定成员的分数加上增量increment
     * 可以通过传递一个负数值increment,让分数减去相应的值
     * 当key不存在,或分数不是key的成员时,重新add一个成员
     * 当key不是有序集类型时,返回一个错误
     * 分数值可以是整数值或双精度浮点数
     * @param condition 满足这个条件才zincrby
     * @param key redis键
     * @param increment 增量
     * @param member 元素
     * @return member成员的新分数值
     */
    public static Double zincrby(boolean condition, String key, double increment, String member){
        if(condition){
            return zincrby(key, increment, member);
        }
        return null;
    }

    /**
     * 对有序集合中指定成员的分数加上增量increment
     * 可以通过传递一个负数值increment,让分数减去相应的值
     * 当key不存在,或分数不是key的成员时,重新add一个成员
     * 当key不是有序集类型时,返回一个错误
     * 分数值可以是整数值或双精度浮点数
     * @param key redis键
     * @param increment 增量
     * @param member 元素
     * @return member成员的新分数值
     */
    public static Double zincrby(byte[] key, double increment, byte[] member){
        Double result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zincrby(key, increment, member);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 对有序集合中指定成员的分数加上增量increment
     * 可以通过传递一个负数值increment,让分数减去相应的值
     * 当key不存在,或分数不是key的成员时,重新add一个成员
     * 当key不是有序集类型时,返回一个错误
     * 分数值可以是整数值或双精度浮点数
     * @param condition 满足这个条件才zincrby
     * @param key redis键
     * @param increment 增量
     * @param member 元素
     * @return member成员的新分数值
     */
    public static Double zincrby(boolean condition, byte[] key, double increment, byte[] member){
        if(condition){
            return zincrby(key, increment, member);
        }
        return null;
    }

    /**
     * 计算给定的一个或多个有序集的交集,并将该交集(结果集)储存到destination
     * 默认情况下,结果集中某个成员的分数值是所有给定集下该成员分数值之和
     * @param destination 目标键
     * @param members 元素
     * @return 保存到目标结果集的的成员数量
     */
    public static Long zinterstore(String destination, String ... members){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zinterstore(destination, members);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 计算给定的一个或多个有序集的交集,并将该交集(结果集)储存到destination
     * 默认情况下,结果集中某个成员的分数值是所有给定集下该成员分数值之和
     * @param condition 满足这个条件才zinterstore
     * @param destination 目标键
     * @param members 元素
     * @return 保存到目标结果集的的成员数量
     */
    public static Long zinterstore(boolean condition, String destination, String ... members){
        if(condition){
            return zinterstore(destination, members);
        }
        return null;
    }

    /**
     * 计算给定的一个或多个有序集的交集,并将该交集(结果集)储存到destination
     * 默认情况下,结果集中某个成员的分数值是所有给定集下该成员分数值之和
     * @param destination 目标键
     * @param members 元素
     * @return 保存到目标结果集的的成员数量
     */
    public static Long zinterstore(byte[] destination, byte[] ... members){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zinterstore(destination, members);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 计算给定的一个或多个有序集的交集,并将该交集(结果集)储存到destination
     * 默认情况下,结果集中某个成员的分数值是所有给定集下该成员分数值之和
     * @param condition 满足这个条件才zinterstore
     * @param destination 目标键
     * @param members 元素
     * @return 保存到目标结果集的的成员数量
     */
    public static Long zinterstore(boolean condition, byte[] destination, byte[] ... members){
        if(condition){
            return zinterstore(destination, members);
        }
        return null;
    }

    /**
     * 计算有序集合中指定字典区间内成员数量
     * @param key redis键
     * @param min 最小字典
     * @param max 最大字典
     * @return 指定区间内的成员数量
     */
    public static Long zlexcount(String key, String min, String max){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zlexcount(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 计算有序集合中指定字典区间内成员数量
     * @param key redis键
     * @param min 最小字典
     * @param max 最大字典
     * @return 指定区间内的成员数量
     */
    public static Long zlexcount(byte[] key, byte[] min, byte[] max){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zlexcount(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集中,指定区间内的成员
     * 其中成员的位置按分数值递增(从小到大)来排序
     * 具有相同分数值的成员按字典序(lexicographical order)来排列
     * 如果你需要成员按值递减(从大到小)来排列,请使用ZREVRANGE命令
     * 下标参数start和stop都以0为底,也即0表示有序集第一个成员,1表示有序集第二个成员,以此类推
     * 你也可以使用负数下标,-1表示最后一个成员,-2表示倒数第二个成员,以此类推
     * @param key redis键
     * @param start 偏移开始
     * @param end 偏移结束
     * @return 指定区间内的成员
     */
    public static Set<String> zrange(String key, long start, long end){
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrange(key, start, end);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集中,指定区间内的成员
     * 其中成员的位置按分数值递增(从小到大)来排序
     * 具有相同分数值的成员按字典序(lexicographical order)来排列
     * 如果你需要成员按值递减(从大到小)来排列,请使用ZREVRANGE命令
     * 下标参数start和stop都以0为底,也即0表示有序集第一个成员,1表示有序集第二个成员,以此类推
     * 你也可以使用负数下标,-1表示最后一个成员,-2表示倒数第二个成员,以此类推
     * @param key redis键
     * @param start 偏移开始
     * @param end 偏移结束
     * @return 指定区间内的成员
     */
    public static Set<byte[]> zrange(byte[] key, long start, long end){
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrange(key, start, end);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 通过字典区间返回有序集合的成员
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 指定区间内的元素列表
     */
    public static Set<String> zrangeByLex(String key, String min, String max){
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrangeByLex(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 通过字典区间返回有序集合的成员
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @param offset 偏移量
     * @param count 限制数量
     * @return 指定区间内的元素列表
     */
    public static Set<String> zrangeByLex(String key, String min, String max, int offset, int count){
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrangeByLex(key, min, max, offset, count);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 通过字典区间返回有序集合的成员
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 指定区间内的元素列表
     */
    public static Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max){
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrangeByLex(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 通过字典区间返回有序集合的成员
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @param offset 偏移量
     * @param count 限制数量
     * @return 指定区间内的元素列表
     */
    public static Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count){
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrangeByLex(key, min, max, offset, count);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集合中指定分数区间的成员列表
     * 有序集成员按分数值递增(从小到大)次序排列
     * 具有相同分数值的成员按字典序来排列(该属性是有序集提供的,不需要额外的计算)
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 有序集成员的列表
     */
    public static Set<String> zrangeByScore(String key, String min, String max){
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集合中指定分数区间的成员列表
     * 有序集成员按分数值递增(从小到大)次序排列
     * 具有相同分数值的成员按字典序来排列(该属性是有序集提供的,不需要额外的计算)
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 有序集成员的列表
     */
    public static Set<String> zrangeByScore(String key, double min, double max){
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集合中指定分数区间的成员列表
     * 有序集成员按分数值递增(从小到大)次序排列
     * 具有相同分数值的成员按字典序来排列(该属性是有序集提供的,不需要额外的计算)
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @param offset 偏移量
     * @param count 限制数量
     * @return 有序集成员的列表
     */
    public static Set<String> zrangeByScore(String key, String min, String max, int offset, int count){
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集合中指定分数区间的成员列表
     * 有序集成员按分数值递增(从小到大)次序排列
     * 具有相同分数值的成员按字典序来排列(该属性是有序集提供的,不需要额外的计算)
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @param offset 偏移量
     * @param count 限制数量
     * @return 有序集成员的列表
     */
    public static Set<String> zrangeByScore(String key, double min, double max, int offset, int count){
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集合中指定分数区间的成员列表
     * 有序集成员按分数值递增(从小到大)次序排列
     * 具有相同分数值的成员按字典序来排列(该属性是有序集提供的,不需要额外的计算)
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 有序集成员的列表
     */
    public static Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max){
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集合中指定分数区间的成员列表
     * 有序集成员按分数值递增(从小到大)次序排列
     * 具有相同分数值的成员按字典序来排列(该属性是有序集提供的,不需要额外的计算)
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 有序集成员的列表
     */
    public static Set<byte[]> zrangeByScore(byte[] key, double min, double max){
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集合中指定分数区间的成员列表
     * 有序集成员按分数值递增(从小到大)次序排列
     * 具有相同分数值的成员按字典序来排列(该属性是有序集提供的,不需要额外的计算)
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @param offset 偏移量
     * @param count 限制数量
     * @return 有序集成员的列表
     */
    public static Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count){
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集合中指定分数区间的成员列表
     * 有序集成员按分数值递增(从小到大)次序排列
     * 具有相同分数值的成员按字典序来排列(该属性是有序集提供的,不需要额外的计算)
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @param offset 偏移量
     * @param count 限制数量
     * @return 有序集成员的列表
     */
    public static Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count){
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集中指定成员的排名
     * 其中有序集成员按分数值递增(从小到大)顺序排列
     * @param key redis键
     * @param member 成员
     * @return 如果成员是有序集key的成员,返回member的排名;如果成员不是有序集key的成员,返回nil
     */
    public static Long zrank(String key, String member){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrank(key, member);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集中指定成员的排名
     * 其中有序集成员按分数值递增(从小到大)顺序排列
     * @param key redis键
     * @param member 成员
     * @return 如果成员是有序集key的成员,返回member的排名;如果成员不是有序集key的成员,返回nil
     */
    public static Long zrank(byte[] key, byte[] member){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrank(key, member);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 用于移除有序集中的一个或多个成员,不存在的成员将被忽略
     * 当key存在但不是有序集类型时,返回一个错误
     * 注意:Redis2.4版本以前,ZREM每次只能删除一个元素
     * @param key redis键
     * @param members 成员
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zrem(String key, String ... members){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrem(key, members);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 用于移除有序集中的一个或多个成员,不存在的成员将被忽略
     * 当key存在但不是有序集类型时,返回一个错误
     * 注意:Redis2.4版本以前,ZREM每次只能删除一个元素
     * @param condition 满足条件才zrem
     * @param key redis键
     * @param members 成员
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zrem(boolean condition, String key, String ... members){
        if(condition){
            return zrem(key, members);
        }
        return null;
    }

    /**
     * 用于移除有序集中的一个或多个成员,不存在的成员将被忽略
     * 当key存在但不是有序集类型时,返回一个错误
     * 注意:Redis2.4版本以前,ZREM每次只能删除一个元素
     * @param key redis键
     * @param members 成员
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zrem(byte[] key, byte[] ... members){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrem(key, members);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 用于移除有序集中的一个或多个成员,不存在的成员将被忽略
     * 当key存在但不是有序集类型时,返回一个错误
     * 注意:Redis2.4版本以前,ZREM每次只能删除一个元素
     * @param condition 满足条件才zrem
     * @param key redis键
     * @param members 成员
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zrem(boolean condition, byte[] key, byte[] ... members){
        if(condition){
            return zrem(key, members);
        }
        return null;
    }

    /**
     * 移除有序集合中给定的字典区间的所有成员
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zremrangeByLex(String key, String min, String max){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zremrangeByLex(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除有序集合中给定的字典区间的所有成员
     * @param condition 满足条件才zremrangeByLex
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zremrangeByLex(boolean condition, String key, String min, String max){
        if(condition){
            return zremrangeByLex(key, min, max);
        }
        return null;
    }

    /**
     * 移除有序集合中给定的字典区间的所有成员
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zremrangeByLex(byte[] key, byte[] min, byte[] max){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zremrangeByLex(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除有序集合中给定的字典区间的所有成员
     * @param condition 满足条件才zremrangeByLex
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zremrangeByLex(boolean condition, byte[] key, byte[] min, byte[] max){
        if(condition){
            return zremrangeByLex(key, min, max);
        }
        return null;
    }

    /**
     * 移除有序集中指定排名(rank)区间内的所有成员
     * @param key redis键
     * @param start 最小
     * @param end 最大
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zremrangeByRank(String key, long start, long end){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zremrangeByRank(key, start, end);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除有序集中指定排名(rank)区间内的所有成员
     * @param condition 满足条件才zremrangeByRank
     * @param key redis键
     * @param start 最小
     * @param end 最大
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zremrangeByRank(boolean condition, String key, long start, long end){
        if(condition){
            return zremrangeByRank(key, start, end);
        }
        return null;
    }

    /**
     * 移除有序集中指定排名(rank)区间内的所有成员
     * @param key redis键
     * @param start 最小
     * @param end 最大
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zremrangeByRank(byte[] key, long start, long end){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zremrangeByRank(key, start, end);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除有序集中指定排名(rank)区间内的所有成员
     * @param condition 满足条件才zremrangeByRank
     * @param key redis键
     * @param start 最小
     * @param end 最大
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zremrangeByRank(boolean condition, byte[] key, long start, long end){
        if(condition){
            return zremrangeByRank(key, start, end);
        }
        return null;
    }

    /**
     * 移除有序集中指定分数(score)区间内的所有成员
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zremrangeByScore(String key, double min, double max){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zremrangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除有序集中指定分数(score)区间内的所有成员
     * @param condition 满足条件才zremrangeByScore
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zremrangeByScore(boolean condition, String key, double min, double max){
        if(condition){
            return zremrangeByScore(key, min, max);
        }
        return null;
    }

    /**
     * 移除有序集中指定分数(score)区间内的所有成员
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zremrangeByScore(String key, String min, String max){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zremrangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除有序集中指定分数(score)区间内的所有成员
     * @param condition 满足条件才zremrangeByScore
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zremrangeByScore(boolean condition, String key, String min, String max){
        if(condition){
            return zremrangeByScore(key, min, max);
        }
        return null;
    }

    /**
     * 移除有序集中指定分数(score)区间内的所有成员
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zremrangeByScore(byte[] key, double min, double max){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zremrangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除有序集中指定分数(score)区间内的所有成员
     * @param condition 满足条件才zremrangeByScore
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zremrangeByScore(boolean condition, byte[] key, double min, double max){
        if(condition){
            return zremrangeByScore(key, min, max);
        }
        return null;
    }

    /**
     * 移除有序集中指定分数(score)区间内的所有成员
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zremrangeByScore(byte[] key, byte[] min, byte[] max){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zremrangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除有序集中指定分数(score)区间内的所有成员
     * @param condition 满足条件才zremrangeByScore
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 被成功移除的成员的数量,不包括被忽略的成员
     */
    public static Long zremrangeByScore(boolean condition, byte[] key, byte[] min, byte[] max){
        if(condition){
            return zremrangeByScore(key, min, max);
        }
        return null;
    }

    /**
     * 返回有序集中,指定区间内的成员
     * 其中成员的位置按分数值递减(从大到小)来排列
     * 具有相同分数值的成员按字典序的逆序(reverse lexicographical order)排列
     * 除了成员按分数值递减的次序排列这一点外,ZREVRANGE命令的其他方面和ZRANGE命令一样
     * @param key redis键
     * @param start 最小
     * @param end 最大
     * @return 有序集成员的列表
     */
    public static Set<String> zrevrange(String key, long start, long end){
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集中,指定区间内的成员
     * 其中成员的位置按分数值递减(从大到小)来排列
     * 具有相同分数值的成员按字典序的逆序(reverse lexicographical order)排列
     * 除了成员按分数值递减的次序排列这一点外,ZREVRANGE命令的其他方面和ZRANGE命令一样
     * @param key redis键
     * @param start 最小
     * @param end 最大
     * @return 有序集成员的列表
     */
    public static Set<byte[]> zrevrange(byte[] key, long start, long end){
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集中,指定分数区间内的成员
     * 其中成员的位置按分数值递减(从大到小)来排列
     * 具有相同分数值的成员按字典序的逆序(reverse lexicographical order)排列
     * 除了成员按分数值递减的次序排列这一点外,ZREVRANGE命令的其他方面和ZRANGE命令一样
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 有序集成员的列表
     */
    public static Set<String> zrevrangeByScore(String key, double min, double max){
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrevrangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集中,指定分数区间内的成员
     * 其中成员的位置按分数值递减(从大到小)来排列
     * 具有相同分数值的成员按字典序的逆序(reverse lexicographical order)排列
     * 除了成员按分数值递减的次序排列这一点外,ZREVRANGE命令的其他方面和ZRANGE命令一样
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 有序集成员的列表
     */
    public static Set<String> zrevrangeByScore(String key, String min, String max){
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrevrangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集中,指定分数区间内的成员
     * 其中成员的位置按分数值递减(从大到小)来排列
     * 具有相同分数值的成员按字典序的逆序(reverse lexicographical order)排列
     * 除了成员按分数值递减的次序排列这一点外,ZREVRANGE命令的其他方面和ZRANGE命令一样
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 有序集成员的列表
     */
    public static Set<byte[]> zrevrangeByScore(byte[] key, double min, double max){
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrevrangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集中,指定分数区间内的成员
     * 其中成员的位置按分数值递减(从大到小)来排列
     * 具有相同分数值的成员按字典序的逆序(reverse lexicographical order)排列
     * 除了成员按分数值递减的次序排列这一点外,ZREVRANGE命令的其他方面和ZRANGE命令一样
     * @param key redis键
     * @param min 最小
     * @param max 最大
     * @return 有序集成员的列表
     */
    public static Set<byte[]> zrevrangeByScore(byte[] key, byte[] min, byte[] max){
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrevrangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集中成员的排名
     * 其中有序集成员按分数值递减(从大到小)排序
     * 排名以0为底,也就是说,分数值最大的成员排名为0
     * 使用ZRANK命令可以获得成员按分数值递增(从小到大)排列的排名
     * @param key redis键
     * @param member 元素
     * @return 如果成员是有序集key的成员,返回成员的排名;如果成员不是有序集key的成员,返回nil
     */
    public static Long zrevrank(String key, String member){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrevrank(key, member);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集中成员的排名
     * 其中有序集成员按分数值递减(从大到小)排序
     * 排名以0为底,也就是说,分数值最大的成员排名为0
     * 使用ZRANK命令可以获得成员按分数值递增(从小到大)排列的排名
     * @param key redis键
     * @param member 元素
     * @return 如果成员是有序集key的成员,返回成员的排名;如果成员不是有序集key的成员,返回nil
     */
    public static Long zrevrank(byte[] key, byte[] member){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zrevrank(key, member);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集中成员的分数值
     * @param key redis键
     * @param member 元素
     * @return 返回有序集中成员的分数值;如果成员元素不是有序集key的成员,或key不存在,返回nil
     */
    public static Double zscore(String key, String member){
        Double result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zscore(key, member);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回有序集中成员的分数值
     * @param key redis键
     * @param member 元素
     * @return 返回有序集中成员的分数值;如果成员元素不是有序集key的成员,或key不存在,返回nil
     */
    public static Double zscore(byte[] key, byte[] member){
        Double result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zscore(key, member);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 计算给定的一个或多个有序集的并集,并将该并集(结果集)储存到destination
     * 默认情况下,结果集中某个成员的分数值是所有给定集下该成员分数值之和
     * @param destination 存储键
     * @param members 元素
     * @return 保存到destination的结果集的成员数量
     */
    public static Long zunionstore(String destination, String members){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zunionstore(destination, members);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 计算给定的一个或多个有序集的并集,并将该并集(结果集)储存到destination
     * 默认情况下,结果集中某个成员的分数值是所有给定集下该成员分数值之和
     * @param condition 满足这个条件才zunionstore
     * @param destination 存储键
     * @param members 元素
     * @return 保存到destination的结果集的成员数量
     */
    public static Long zunionstore(boolean condition, String destination, String members){
        if(condition){
            return zunionstore(destination, members);
        }
        return null;
    }

    /**
     * 计算给定的一个或多个有序集的并集,并将该并集(结果集)储存到destination
     * 默认情况下,结果集中某个成员的分数值是所有给定集下该成员分数值之和
     * @param destination 存储键
     * @param members 元素
     * @return 保存到destination的结果集的成员数量
     */
    public static Long zunionstore(byte[] destination, byte[] members){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.zunionstore(destination, members);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 计算给定的一个或多个有序集的并集,并将该并集(结果集)储存到destination
     * 默认情况下,结果集中某个成员的分数值是所有给定集下该成员分数值之和
     * @param condition 满足这个条件才zunionstore
     * @param destination 存储键
     * @param members 元素
     * @return 保存到destination的结果集的成员数量
     */
    public static Long zunionstore(boolean condition, byte[] destination, byte[] members){
        if(condition){
            return zunionstore(destination, members);
        }
        return null;
    }

    /**
     * 迭代数据库中的数据库键(包括元素成员和元素分值)
     * @param key redis键
     * @return 返回的每个元素都是一个有序集合元素,一个有序集合元素由一个成员(member)和一个分值(score)组成
     */
    public static ScanResult<Tuple> zscan(String key){
        return zscan(key, ScanParams.SCAN_POINTER_START, "", 0);
    }

    /**
     * 迭代数据库中的数据库键(包括元素成员和元素分值)
     * @param key redis键
     * @param cursor 游标
     * @return 返回的每个元素都是一个有序集合元素,一个有序集合元素由一个成员(member)和一个分值(score)组成
     */
    public static ScanResult<Tuple> zscan(String key, String cursor){
        return zscan(key, cursor, "", 0);
    }

    /**
     * 迭代数据库中的数据库键(包括元素成员和元素分值)
     * @param key redis键
     * @param cursor 游标
     * @param pattern 表达式
     * @return 返回的每个元素都是一个有序集合元素,一个有序集合元素由一个成员(member)和一个分值(score)组成
     */
    public static ScanResult<Tuple> zscan(String key, String cursor, String pattern){
        return zscan(key, cursor, pattern, 0);
    }

    /**
     * 迭代数据库中的数据库键(包括元素成员和元素分值)
     * @param key redis键
     * @param cursor 游标
     * @param count 限制数量
     * @return 返回的每个元素都是一个有序集合元素,一个有序集合元素由一个成员(member)和一个分值(score)组成
     */
    public static ScanResult<Tuple> zscan(String key, String cursor, int count){
        return zscan(key, cursor, "", count);
    }

    /**
     * 迭代数据库中的数据库键(包括元素成员和元素分值)
     * @param key redis键
     * @param cursor 游标
     * @param pattern 表达式
     * @param count 限制数量
     * @return 返回的每个元素都是一个有序集合元素,一个有序集合元素由一个成员(member)和一个分值(score)组成
     */
    public static ScanResult<Tuple> zscan(String key, String cursor, String pattern, int count){
        ScanResult<Tuple> result = null;
        Jedis jedis = null;
        try {
            ScanParams params = new ScanParams();
            if(!StringUtils.isEmpty(pattern)){
                params.match(pattern);
            }
            if(count > 0){
                params.count(count);
            }
            jedis = getJedis();
            result = jedis.zscan(key, cursor, params);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 迭代数据库中的数据库键(包括元素成员和元素分值)
     * @param key redis键
     * @return 返回的每个元素都是一个有序集合元素,一个有序集合元素由一个成员(member)和一个分值(score)组成
     */
    public static ScanResult<Tuple> zscanBinary(byte[] key){
        return zscanBinary(key, ScanParams.SCAN_POINTER_START_BINARY, null, 0);
    }

    /**
     * 迭代数据库中的数据库键(包括元素成员和元素分值)
     * @param key redis键
     * @param cursor 游标
     * @return 返回的每个元素都是一个有序集合元素,一个有序集合元素由一个成员(member)和一个分值(score)组成
     */
    public static ScanResult<Tuple> zscanBinary(byte[] key, byte[] cursor){
        return zscanBinary(key, cursor, null, 0);
    }

    /**
     * 迭代数据库中的数据库键(包括元素成员和元素分值)
     * @param key redis键
     * @param cursor 游标
     * @param pattern 表达式
     * @return 返回的每个元素都是一个有序集合元素,一个有序集合元素由一个成员(member)和一个分值(score)组成
     */
    public static ScanResult<Tuple> zscanBinary(byte[] key, byte[] cursor, byte[] pattern){
        return zscanBinary(key, cursor, pattern, 0);
    }

    /**
     * 迭代数据库中的数据库键(包括元素成员和元素分值)
     * @param key redis键
     * @param cursor 游标
     * @param count 限制数量
     * @return 返回的每个元素都是一个有序集合元素,一个有序集合元素由一个成员(member)和一个分值(score)组成
     */
    public static ScanResult<Tuple> zscanBinary(byte[] key, byte[] cursor, int count){
        return zscanBinary(key, cursor, null, count);
    }

    /**
     * 迭代数据库中的数据库键(包括元素成员和元素分值)
     * @param key redis键
     * @param cursor 游标
     * @param pattern 表达式
     * @param count 限制数量
     * @return 返回的每个元素都是一个有序集合元素,一个有序集合元素由一个成员(member)和一个分值(score)组成
     */
    public static ScanResult<Tuple> zscanBinary(byte[] key, byte[] cursor, byte[] pattern, int count){
        ScanResult<Tuple> result = null;
        Jedis jedis = null;
        try {
            ScanParams params = new ScanParams();
            if(!StringUtils.isEmpty(pattern)){
                params.match(pattern);
            }
            if(count > 0){
                params.count(count);
            }
            jedis = getJedis();
            result = jedis.zscan(key, cursor, params);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    //**********          redis键数据操作的方法          **********//

    /**
     * 删除已存在的键,不存在的key会被忽略
     * @param key redis键
     * @return 被删除key的数量
     */
    public static Long del(String key) {
        Long value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.del(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 删除已存在的键,不存在的key会被忽略
     * @param condition 满足这个条件才去del
     * @param key redis键
     * @return 被删除key的数量
     */
    public static Long del(boolean condition, String key) {
        if(condition){
            return del(key);
        }
        return null;
    }

    /**
     * 删除已存在的键,不存在的key会被忽略
     * @param keys redis键
     * @return 被删除key的数量
     */
    public static Long del(String ... keys) {
        Long value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.del(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 删除已存在的键,不存在的key会被忽略
     * @param condition 满足这个条件才去del
     * @param keys redis键
     * @return 被删除key的数量
     */
    public static Long del(boolean condition, String ... keys) {
        if(condition){
            return del(keys);
        }
        return null;
    }

    /**
     * 删除已存在的键,不存在的key会被忽略
     * @param key redis键
     * @return 被删除key的数量
     */
    public static Long del(byte[] key) {
        Long value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.del(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 删除已存在的键,不存在的key会被忽略
     * @param condition 满足这个条件才去del
     * @param key redis键
     * @return 被删除key的数量
     */
    public static Long del(boolean condition, byte[] key) {
        if(condition){
            return del(key);
        }
        return null;
    }

    /**
     * 删除已存在的键,不存在的key会被忽略
     * @param keys redis键
     * @return 被删除key的数量
     */
    public static Long del(byte[] ... keys) {
        Long value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.del(keys);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 删除已存在的键,不存在的key会被忽略
     * @param condition 满足这个条件才去del
     * @param keys redis键
     * @return 被删除key的数量
     */
    public static Long del(boolean condition, byte[] ... keys) {
        if(condition){
            return del(keys);
        }
        return null;
    }

    /**
     * 批量模糊删除数据
     * @param key redis键
     * @return 成功返回TRUE
     */
    public static Boolean delLike(String key){
        Set<String> set = keys(key +"*");
        for (String s : set) {
            del(s);
        }
        return Boolean.TRUE;
    }

    /**
     * 批量模糊删除数据
     * @param condition 满足这个条件才去delLike
     * @param key redis键
     * @return 成功返回TRUE
     */
    public static Boolean delLike(boolean condition, String key){
        if(condition){
            return delLike(key);
        }
        return Boolean.TRUE;
    }

    /**
     * 序列化给定key
     * @param key redis键
     * @return 如果key不存在,那么返回nil;否则,返回序列化之后的值
     */
    public static byte[] dump(String key){
        byte[] result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.dump(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 序列化给定key
     * @param key redis键
     * @return 如果key不存在,那么返回nil;否则,返回序列化之后的值
     */
    public static byte[] dump(byte[] key){
        byte[] result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.dump(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 判断是否存在
     * @param key redis键
     * @return 若key存在返回true,否则返回false
     */
    public static Boolean exists(String key) {
        Boolean value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.exists(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 判断是否存在
     * @param key redis键
     * @return 存在的键的个数
     */
    public static Long exists(String ... key) {
        Long value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.exists(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 判断是否存在
     * @param key redis键
     * @return 若key存在返回true,否则返回false
     */
    public static Boolean exists(byte[] key) {
        Boolean value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.exists(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 判断是否存在
     * @param key redis键
     * @return 存在的键的个数
     */
    public static Long exists(byte[] ... key) {
        Long value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.exists(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return value;
    }

    /**
     * 设置过期时间
     * @param key redis键
     * @param expireSeconds 过期时间 - 秒
     * @return 设置成功返回1;当key不存在或者不能为key设置过期时间时(比如在低于2.1.3版本的Redis中你尝试更新key的过期时间)返回0
     */
    public static Long expire(String key, int expireSeconds) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.expire(key, expireSeconds);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 设置过期时间
     * @param condition 满足这个条件才去expire
     * @param key redis键
     * @param expireSeconds 过期时间 - 秒
     * @return 设置成功返回1;当key不存在或者不能为key设置过期时间时(比如在低于2.1.3版本的Redis中你尝试更新key的过期时间)返回0
     */
    public static Long expire(boolean condition, String key, int expireSeconds) {
        if(condition){
            return expire(key, expireSeconds);
        }
        return null;
    }

    /**
     * 设置过期时间
     * @param key redis键
     * @param expireSeconds 过期时间 - 秒
     * @return 设置成功返回1;当key不存在或者不能为key设置过期时间时(比如在低于2.1.3版本的Redis中你尝试更新key的过期时间)返回0
     */
    public static Long expire(byte[] key, int expireSeconds) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.expire(key, expireSeconds);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 设置过期时间
     * @param condition 满足这个条件才去expire
     * @param key redis键
     * @param expireSeconds 过期时间 - 秒
     * @return 设置成功返回1;当key不存在或者不能为key设置过期时间时(比如在低于2.1.3版本的Redis中你尝试更新key的过期时间)返回0
     */
    public static Long expire(boolean condition, byte[] key, int expireSeconds) {
        if(condition){
            return expire(key, expireSeconds);
        }
        return null;
    }

    /**
     * 设置过期时间
     * @param key redis键
     * @param unixTime unix时间戳
     * @return 设置成功返回1;当key不存在或者不能为key设置过期时间时(比如在低于2.1.3版本的Redis中你尝试更新key的过期时间)返回0
     */
    public static Long expireAt(String key, long unixTime){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.expireAt(key, unixTime);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 设置过期时间
     * @param condition 满足这个条件才去expireAt
     * @param key redis键
     * @param unixTime unix时间戳
     * @return 设置成功返回1;当key不存在或者不能为key设置过期时间时(比如在低于2.1.3版本的Redis中你尝试更新key的过期时间)返回0
     */
    public static Long expireAt(boolean condition, String key, long unixTime){
        if(condition){
            return expireAt(key, unixTime);
        }
        return null;
    }

    /**
     * 设置过期时间
     * @param key redis键
     * @param unixTime unix时间戳
     * @return 设置成功返回1;当key不存在或者不能为key设置过期时间时(比如在低于2.1.3版本的Redis中你尝试更新key的过期时间)返回0
     */
    public static Long expireAt(byte[] key, long unixTime){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.expireAt(key, unixTime);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 设置过期时间
     * @param condition 满足这个条件才去expireAt
     * @param key redis键
     * @param unixTime unix时间戳
     * @return 设置成功返回1;当key不存在或者不能为key设置过期时间时(比如在低于2.1.3版本的Redis中你尝试更新key的过期时间)返回0
     */
    public static Long expireAt(boolean condition, byte[] key, long unixTime){
        if(condition){
            return expireAt(key, unixTime);
        }
        return null;
    }

    /**
     * 设置过期时间
     * @param key redis键
     * @param milliseconds 毫秒级
     * @return 设置成功返回1;当key不存在或者不能为key设置过期时间时(比如在低于2.1.3版本的Redis中你尝试更新key的过期时间)返回0
     */
    public static Long pexpire(String key, long milliseconds){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.pexpire(key, milliseconds);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 设置过期时间
     * @param condition 满足这个条件才去pexpire
     * @param key redis键
     * @param milliseconds 毫秒级
     * @return 设置成功返回1;当key不存在或者不能为key设置过期时间时(比如在低于2.1.3版本的Redis中你尝试更新key的过期时间)返回0
     */
    public static Long pexpire(boolean condition, String key, long milliseconds){
        if(condition){
            return pexpire(key, milliseconds);
        }
        return null;
    }

    /**
     * 设置过期时间
     * @param key redis键
     * @param milliseconds 毫秒级
     * @return 设置成功返回1;当key不存在或者不能为key设置过期时间时(比如在低于2.1.3版本的Redis中你尝试更新key的过期时间)返回0
     */
    public static Long pexpire(byte[] key, long milliseconds){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.pexpire(key, milliseconds);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 设置过期时间
     * @param condition 满足这个条件才去pexpire
     * @param key redis键
     * @param milliseconds 毫秒级
     * @return 设置成功返回1;当key不存在或者不能为key设置过期时间时(比如在低于2.1.3版本的Redis中你尝试更新key的过期时间)返回0
     */
    public static Long pexpire(boolean condition, byte[] key, long milliseconds){
        if(condition){
            return pexpire(key, milliseconds);
        }
        return null;
    }

    /**
     * 设置过期时间
     * @param key redis键
     * @param millisecondsTimestamp unix时间戳 - 毫秒级
     * @return 设置成功返回1;当key不存在或者不能为key设置过期时间时(比如在低于2.1.3版本的Redis中你尝试更新key的过期时间)返回0
     */
    public static Long pExpireAt(String key, long millisecondsTimestamp){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.pexpireAt(key, millisecondsTimestamp);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 设置过期时间
     * @param condition 满足这个条件才去pExpireAt
     * @param key redis键
     * @param millisecondsTimestamp unix时间戳 - 毫秒级
     * @return 设置成功返回1;当key不存在或者不能为key设置过期时间时(比如在低于2.1.3版本的Redis中你尝试更新key的过期时间)返回0
     */
    public static Long pExpireAt(boolean condition, String key, long millisecondsTimestamp){
        if(condition){
            return pExpireAt(key, millisecondsTimestamp);
        }
        return null;
    }

    /**
     * 设置过期时间
     * @param key redis键
     * @param millisecondsTimestamp unix时间戳 - 毫秒级
     * @return 设置成功返回1;当key不存在或者不能为key设置过期时间时(比如在低于2.1.3版本的Redis中你尝试更新key的过期时间)返回0
     */
    public static Long pExpireAt(byte[] key, long millisecondsTimestamp){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.pexpireAt(key, millisecondsTimestamp);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 设置过期时间
     * @param condition 满足这个条件才去pExpireAt
     * @param key redis键
     * @param millisecondsTimestamp unix时间戳 - 毫秒级
     * @return 设置成功返回1;当key不存在或者不能为key设置过期时间时(比如在低于2.1.3版本的Redis中你尝试更新key的过期时间)返回0
     */
    public static Long pExpireAt(boolean condition, byte[] key, long millisecondsTimestamp){
        if(condition){
            return pExpireAt(key, millisecondsTimestamp);
        }
        return null;
    }

    /**
     * 查找所有符合给定模式(pattern)的key - 例如:sun*
     * @param pattern redis表达式
     * @return 符合给定模式的key列表
     */
    public static Set<String> keys(String pattern){
        Set<String> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.keys(pattern);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 查找所有符合给定模式(pattern)的key - 例如:sun*
     * @param pattern redis表达式
     * @return 符合给定模式的key列表
     */
    public static Set<byte[]> keys(byte[] pattern){
        Set<byte[]> result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.keys(pattern);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将当前数据库的key移动到给定的dbIndex数据库当中
     * @param key redis键
     * @param dbIndex 数据库索引
     * @return 移动成功返回1,失败则返回0
     */
    public static Long move(String key, int dbIndex){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.move(key, dbIndex);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将当前数据库的key移动到给定的dbIndex数据库当中
     * @param condition 满足条件才move
     * @param key redis键
     * @param dbIndex 数据库索引
     * @return 移动成功返回1,失败则返回0
     */
    public static Long move(boolean condition, String key, int dbIndex){
        if(condition){
            return move(key, dbIndex);
        }
        return null;
    }

    /**
     * 将当前数据库的key移动到给定的dbIndex数据库当中
     * @param key redis键
     * @param dbIndex 数据库索引
     * @return 移动成功返回1,失败则返回0
     */
    public static Long move(byte[] key, int dbIndex){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.move(key, dbIndex);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 将当前数据库的key移动到给定的dbIndex数据库当中
     * @param condition 满足条件才move
     * @param key redis键
     * @param dbIndex 数据库索引
     * @return 移动成功返回1,失败则返回0
     */
    public static Long move(boolean condition, byte[] key, int dbIndex){
        if(condition){
            return move(key, dbIndex);
        }
        return null;
    }

    /**
     * 移除key的过期时间,key将持久保持
     * @param key redis键
     * @return 当过期时间移除成功时,返回1;如果key不存在或key没有设置过期时间,返回0
     */
    public static Long persist(String key){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.persist(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除key的过期时间,key将持久保持
     * @param condition 满足条件才persist
     * @param key redis键
     * @return 当过期时间移除成功时,返回1;如果key不存在或key没有设置过期时间,返回0
     */
    public static Long persist(boolean condition, String key){
        if(condition){
            return persist(key);
        }
        return null;
    }

    /**
     * 移除key的过期时间,key将持久保持
     * @param key redis键
     * @return 当过期时间移除成功时,返回1;如果key不存在或key没有设置过期时间,返回0
     */
    public static Long persist(byte[] key){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.persist(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 移除key的过期时间,key将持久保持
     * @param condition 满足条件才persist
     * @param key redis键
     * @return 当过期时间移除成功时,返回1;如果key不存在或key没有设置过期时间,返回0
     */
    public static Long persist(boolean condition, byte[] key){
        if(condition){
            return persist(key);
        }
        return null;
    }

    /**
     * redis获取key的剩余时间 - 秒
     * @param key redis键
     * @return 当key不存在时,返回-2;当key存在但没有设置剩余生存时间时,返回-1;否则,以秒为单位,返回key的剩余生存时间
     * 注意:在Redis2.8以前,当key不存在,或者key没有设置剩余生存时间时,命令都返回-1
     */
    public static long ttl(String key) {
        long result = -1L;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.ttl(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * redis获取key的剩余时间 - 秒
     * @param key redis键
     * @return 当key不存在时,返回-2;当key存在但没有设置剩余生存时间时,返回-1;否则,以秒为单位,返回key的剩余生存时间
     * 注意:在Redis2.8以前,当key不存在,或者key没有设置剩余生存时间时,命令都返回-1
     */
    public static long ttl(byte[] key) {
        long result = -1L;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.ttl(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * redis获取key的剩余时间 - 毫秒
     * @param key redis键
     * @return 当key不存在时,返回-2;当key存在但没有设置剩余生存时间时,返回-1;否则,以秒为单位,返回key的剩余生存时间
     * 注意:在Redis2.8以前,当key不存在,或者key没有设置剩余生存时间时,命令都返回-1
     */
    public static long pttl(String key) {
        long result = -1L;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.pttl(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * redis获取key的剩余时间 - 毫秒
     * @param key redis键
     * @return 当key不存在时,返回-2;当key存在但没有设置剩余生存时间时,返回-1;否则,以秒为单位,返回key的剩余生存时间
     * 注意:在Redis2.8以前,当key不存在,或者key没有设置剩余生存时间时,命令都返回-1
     */
    public static long pttl(byte[] key) {
        long result = -1L;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.pttl(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 从当前数据库中随机返回一个 key
     * @return 当数据库不为空时,返回一个key;当数据库为空时,返回nil(windows系统返回null)
     */
    public static String randomKey(){
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.randomKey();
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 从当前数据库中随机返回一个 key
     * @return 当数据库不为空时,返回一个key;当数据库为空时,返回nil(windows系统返回null)
     */
    public static byte[] randomBinaryKey(){
        byte[] result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.randomBinaryKey();
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 修改key的名称
     * @param key 旧的key名称
     * @param newKey 新的key名称
     * @return 改名成功时提示OK,失败时候返回一个错误
     * 当key和newKey相同,或者key不存在时,返回一个错误;当newKey已经存在时,将覆盖旧值
     */
    public static String rename(String key, String newKey){
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.rename(key, newKey);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 修改key的名称
     * @param condition 满足这个条件才rename
     * @param key 旧的key名称
     * @param newKey 新的key名称
     * @return 改名成功时提示OK,失败时候返回一个错误
     * 当key和newKey相同,或者key不存在时,返回一个错误;当newKey已经存在时,将覆盖旧值
     */
    public static String rename(boolean condition, String key, String newKey){
        if(condition){
            return rename(key, newKey);
        }
        return null;
    }

    /**
     * 修改key的名称
     * @param key 旧的key名称
     * @param newKey 新的key名称
     * @return 改名成功时提示OK,失败时候返回一个错误
     * 当key和newKey相同,或者key不存在时,返回一个错误;当newKey已经存在时,将覆盖旧值
     */
    public static String rename(byte[] key, byte[] newKey){
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.rename(key, newKey);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 修改key的名称
     * @param condition 满足这个条件才rename
     * @param key 旧的key名称
     * @param newKey 新的key名称
     * @return 改名成功时提示OK,失败时候返回一个错误
     * 当key和newKey相同,或者key不存在时,返回一个错误;当newKey已经存在时,将覆盖旧值
     */
    public static String rename(boolean condition, byte[] key, byte[] newKey){
        if(condition){
            return rename(key, newKey);
        }
        return null;
    }

    /**
     * 仅当newKey不存在时,将key改名为newKey
     * @param key 旧的key名称
     * @param newKey 新的key名称
     * @return 修改成功时,返回1;如果newKey已经存在,返回0
     */
    public static Long renamenx(String key, String newKey){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.renamenx(key, newKey);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 仅当newKey不存在时,将key改名为newKey
     * @param condition 满足这个条件才renamenx
     * @param key 旧的key名称
     * @param newKey 新的key名称
     * @return 修改成功时,返回1;如果newKey已经存在,返回0
     */
    public static Long renamenx(boolean condition, String key, String newKey){
        if(condition){
            return renamenx(key, newKey);
        }
        return null;
    }

    /**
     * 仅当newKey不存在时,将key改名为newKey
     * @param key 旧的key名称
     * @param newKey 新的key名称
     * @return 修改成功时,返回1;如果newKey已经存在,返回0
     */
    public static Long renamenx(byte[] key, byte[] newKey){
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.renamenx(key, newKey);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 仅当newKey不存在时,将key改名为newKey
     * @param condition 满足这个条件才renamenx
     * @param key 旧的key名称
     * @param newKey 新的key名称
     * @return 修改成功时,返回1;如果newKey已经存在,返回0
     */
    public static Long renamenx(boolean condition, byte[] key, byte[] newKey){
        if(condition){
            return renamenx(key, newKey);
        }
        return null;
    }

    /**
     * 迭代数据库中的数据库键
     * 一个基于游标的迭代器,每次被调用之后,都会向用户返回一个新的游标
     * 用户在下次迭代时需要使用这个新游标作为SCAN命令的游标参数,以此来延续之前的迭代过程
     * @return 一个包含两个元素的数组,第一个元素是用于进行下一次迭代的新游标,而第二个元素则是一个数组,这个数组中包含了所有被迭代的元素;如果新游标返回0表示迭代已结束
     */
    public static ScanResult<String> scan(){
        return scan(ScanParams.SCAN_POINTER_START, "", 0);
    }

    /**
     * 迭代数据库中的数据库键
     * 一个基于游标的迭代器,每次被调用之后,都会向用户返回一个新的游标
     * 用户在下次迭代时需要使用这个新游标作为SCAN命令的游标参数,以此来延续之前的迭代过程
     * @param cursor 游标
     * @return 一个包含两个元素的数组,第一个元素是用于进行下一次迭代的新游标,而第二个元素则是一个数组,这个数组中包含了所有被迭代的元素;如果新游标返回0表示迭代已结束
     */
    public static ScanResult<String> scan(String cursor){
        return scan(cursor, "", 0);
    }

    /**
     * 迭代数据库中的数据库键
     * 一个基于游标的迭代器,每次被调用之后,都会向用户返回一个新的游标
     * 用户在下次迭代时需要使用这个新游标作为SCAN命令的游标参数,以此来延续之前的迭代过程
     * @param cursor 游标
     * @param pattern 匹配的模式
     * @return 一个包含两个元素的数组,第一个元素是用于进行下一次迭代的新游标,而第二个元素则是一个数组,这个数组中包含了所有被迭代的元素;如果新游标返回0表示迭代已结束
     */
    public static ScanResult<String> scan(String cursor, String pattern){
        return scan(cursor, pattern, 0);
    }

    /**
     * 迭代数据库中的数据库键
     * 一个基于游标的迭代器,每次被调用之后,都会向用户返回一个新的游标
     * 用户在下次迭代时需要使用这个新游标作为SCAN命令的游标参数,以此来延续之前的迭代过程
     * @param cursor 游标
     * @param count 限制条数
     * @return 一个包含两个元素的数组,第一个元素是用于进行下一次迭代的新游标,而第二个元素则是一个数组,这个数组中包含了所有被迭代的元素;如果新游标返回0表示迭代已结束
     */
    public static ScanResult<String> scan(String cursor, int count){
        return scan(cursor, "", count);
    }

    /**
     * 迭代数据库中的数据库键
     * 一个基于游标的迭代器,每次被调用之后,都会向用户返回一个新的游标
     * 用户在下次迭代时需要使用这个新游标作为SCAN命令的游标参数,以此来延续之前的迭代过程
     * @param cursor 游标
     * @param pattern 匹配的模式
     * @param count 限制条数
     * @return 一个包含两个元素的数组,第一个元素是用于进行下一次迭代的新游标,而第二个元素则是一个数组,这个数组中包含了所有被迭代的元素;如果新游标返回0表示迭代已结束
     */
    public static ScanResult<String> scan(String cursor, String pattern, int count){
        ScanResult<String> result = null;
        Jedis jedis = null;
        try {
            ScanParams scanParams = new ScanParams();
            if(!StringUtils.isEmpty(pattern)){
                scanParams.match(pattern);
            }
            if(count > 0){
                scanParams.count(count);
            }
            jedis = getJedis();
            result = jedis.scan(cursor,scanParams);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 迭代数据库中的数据库键
     * 一个基于游标的迭代器,每次被调用之后,都会向用户返回一个新的游标
     * 用户在下次迭代时需要使用这个新游标作为SCAN命令的游标参数,以此来延续之前的迭代过程
     * @return 一个包含两个元素的数组,第一个元素是用于进行下一次迭代的新游标,而第二个元素则是一个数组,这个数组中包含了所有被迭代的元素;如果新游标返回0表示迭代已结束
     */
    public static ScanResult<byte[]> scanBinary(){
        return scanBinary(ScanParams.SCAN_POINTER_START_BINARY, null, 0);
    }

    /**
     * 迭代数据库中的数据库键
     * 一个基于游标的迭代器,每次被调用之后,都会向用户返回一个新的游标
     * 用户在下次迭代时需要使用这个新游标作为SCAN命令的游标参数,以此来延续之前的迭代过程
     * @param cursor 游标
     * @return 一个包含两个元素的数组,第一个元素是用于进行下一次迭代的新游标,而第二个元素则是一个数组,这个数组中包含了所有被迭代的元素;如果新游标返回0表示迭代已结束
     */
    public static ScanResult<byte[]> scanBinary(byte[] cursor){
        return scanBinary(cursor, null, 0);
    }

    /**
     * 迭代数据库中的数据库键
     * 一个基于游标的迭代器,每次被调用之后,都会向用户返回一个新的游标
     * 用户在下次迭代时需要使用这个新游标作为SCAN命令的游标参数,以此来延续之前的迭代过程
     * @param cursor 游标
     * @param pattern 匹配的模式
     * @return 一个包含两个元素的数组,第一个元素是用于进行下一次迭代的新游标,而第二个元素则是一个数组,这个数组中包含了所有被迭代的元素;如果新游标返回0表示迭代已结束
     */
    public static ScanResult<byte[]> scanBinary(byte[] cursor, byte[] pattern){
        return scanBinary(cursor, pattern, 0);
    }

    /**
     * 迭代数据库中的数据库键
     * 一个基于游标的迭代器,每次被调用之后,都会向用户返回一个新的游标
     * 用户在下次迭代时需要使用这个新游标作为SCAN命令的游标参数,以此来延续之前的迭代过程
     * @param cursor 游标
     * @param count 限制条数
     * @return 一个包含两个元素的数组,第一个元素是用于进行下一次迭代的新游标,而第二个元素则是一个数组,这个数组中包含了所有被迭代的元素;如果新游标返回0表示迭代已结束
     */
    public static ScanResult<byte[]> scanBinary(byte[] cursor, int count){
        return scanBinary(cursor, null, count);
    }

    /**
     * 迭代数据库中的数据库键
     * 一个基于游标的迭代器,每次被调用之后,都会向用户返回一个新的游标
     * 用户在下次迭代时需要使用这个新游标作为SCAN命令的游标参数,以此来延续之前的迭代过程
     * @param cursor 游标
     * @param pattern 匹配的模式
     * @param count 限制条数
     * @return 一个包含两个元素的数组,第一个元素是用于进行下一次迭代的新游标,而第二个元素则是一个数组,这个数组中包含了所有被迭代的元素;如果新游标返回0表示迭代已结束
     */
    public static ScanResult<byte[]> scanBinary(byte[] cursor, byte[] pattern, int count){
        ScanResult<byte[]> result = null;
        Jedis jedis = null;
        try {
            ScanParams scanParams = new ScanParams();
            if(!StringUtils.isEmpty(pattern)){
                scanParams.match(pattern);
            }
            if(count > 0){
                scanParams.count(count);
            }
            jedis = getJedis();
            result = jedis.scan(cursor,scanParams);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回key所储存的值的类型
     * @param key redis键
     * @return none:不存在,string字符串,list列表,set集合,zset有序集合,hash哈希
     */
    public static String type(String key){
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.type(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 返回key所储存的值的类型
     * @param key redis键
     * @return none:不存在,string字符串,list列表,set集合,zset有序集合,hash哈希
     */
    public static String type(byte[] key){
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.type(key);
        } catch (Exception e) {
            log.error("redis operate fail ==> ", e);
        } finally {
            close(jedis);
        }
        return result;
    }

    //**********          redis并发操作的方法          **********//
    /**
     * 单服务使用时,redis锁set值
     * 没有过期时间
     * 默认重试5秒
     * @param key redis键
     * @param value redis值
     * @return 设置成功TRUE,失败FALSE
     */
    public static synchronized Boolean singleLockSet(String key, String value){
        return singleLockSet(key, value, 5);
    }

    /**
     * 单服务使用时,redis锁set值
     * 没有过期时间
     * @param key redis键
     * @param value redis值
     * @param tryTimeoutSeconds 尝试设置超时时间 - 秒
     * @return 设置成功TRUE,失败FALSE
     */
    public static synchronized Boolean singleLockSet(String key, String value, int tryTimeoutSeconds){
        return singleLockSet(key, value, 0L, tryTimeoutSeconds);
    }

    /**
     * 单服务使用时,redis锁set值
     * 默认重试5秒
     * @param key redis键
     * @param value redis值
     * @param expireSeconds 过期时间 - 秒
     * @return 设置成功TRUE,失败FALSE
     */
    public static synchronized Boolean singleLockSet(String key, String value, long expireSeconds){
        return singleLockSet(key, value, expireSeconds, 5);
    }

    /**
     * 单服务使用时,redis锁set值
     * @param key redis键
     * @param value redis值
     * @param expireSeconds 过期时间 - 秒
     * @param tryTimeoutSeconds 尝试设置超时时间 - 秒
     * @return 设置成功TRUE,失败FALSE
     */
    public static synchronized Boolean singleLockSet(String key, String value, long expireSeconds, long tryTimeoutSeconds){
        long time = System.currentTimeMillis();
        long timeoutAt = time + tryTimeoutSeconds * 1000;
        while(setnx(key, value) == 0){
            // 当setnx设置失败时执行
            // 当前时间大于等于超时时间
            if(System.currentTimeMillis() >= timeoutAt){
                return Boolean.FALSE;
            }
        }
        // 设置过期时间
        if(expireSeconds > 0){
            expire(key, new Long(expireSeconds).intValue());
        }
        return Boolean.TRUE;
    }

    /**
     * 多服务使用时 - 获取分布式锁
     * @param key redis键
     * @param requestId 请求ID,可以用UUID - 解锁时需要用
     * @param expireSeconds 过期时间 - 秒
     * @return 获取成功TRUE,失败FALSE
     */
    public static Boolean getLock(String key, String requestId, int expireSeconds){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String result = jedis.set(key, requestId, SetParams.setParams().ex(expireSeconds).nx());
            if (OK.equals(result)) {
                log.info("redis get distributed lock success, key[{}], requestId[{}], expireSeconds[{}]", key, requestId, expireSeconds);
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            log.error("redis get distributed lock fail => ", e);
        } finally {
            close(jedis);
        }
        return Boolean.FALSE;
    }

    /**
     * 多服务使用时 - 解锁
     * 使用lua脚本,保证"判断"和"删除"操作原子性
     * @param key redis键
     * @param requestId 获取锁时设置的请求ID
     * @return 解锁成功TRUE,失败FALSE
     */
    public static Boolean releaseLock(String key, String requestId){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(requestId));
            if (OK.equals(result)) {
                log.info("redis release distributed lock success, key[{}], requestId[{}]", key, requestId);
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            log.error("redis release distributed lock fail => ", e);
        } finally {
            close(jedis);
        }
        return Boolean.FALSE;
    }
}
