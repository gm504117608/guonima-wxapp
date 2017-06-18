package com.guonima.wxapp.redis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis客户端访问
 */
public class RedisClient {

    public static JedisPool jedisPool; // 池化管理jedis链接池

    static {

        //读取相关的配置
        ResourceBundle resourceBundle = ResourceBundle.getBundle("redis");
        int maxActive = Integer.parseInt(resourceBundle.getString("redis.pool.maxActive"));
        int maxIdle = Integer.parseInt(resourceBundle.getString("redis.pool.maxIdle"));
        int maxWait = Integer.parseInt(resourceBundle.getString("redis.pool.maxWait"));
        String ip = resourceBundle.getString("redis.ip");
        int port = Integer.parseInt(resourceBundle.getString("redis.port"));
        String password = resourceBundle.getString("redis.password");
        int timeout = Integer.parseInt(resourceBundle.getString("redis.timeout"));

        JedisPoolConfig config = new JedisPoolConfig();
        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
        config.setMaxTotal(maxActive);
        //设置最大空闲数
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(maxIdle);
        //设置超时时间
        config.setMaxWaitMillis(maxWait);

        //初始化连接池
        jedisPool = new JedisPool(config, ip, port);
//        jedisPool = new JedisPool(config, ip, port, timeout, password);
    }

    /**
     * 向缓存中设置字符串内容
     *
     * @param key   key
     * @param value value
     * @return 存储成功 true 否则 false
     * @throws Exception
     */
    public static boolean set(String key, String value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 向缓存中设置字符串内容 并带有有效期时间
     *
     * @param key     key
     * @param seconds 有效时间(单位 秒)
     * @param value   value
     * @return 存储成功 true 否则 false
     * @throws Exception
     */
    public static boolean set(String key, int seconds, String value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex(key, seconds, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 向缓存中设置对象
     *
     * @param key
     * @param value
     * @return 存储成功 true 否则 false
     * @throws Exception
     */
    public static boolean set(String key, Object value) throws Exception {
        Jedis jedis = null;
        try {
            String objectJson = JSON.toJSONString(value);
            jedis = jedisPool.getResource();
            jedis.set(key, objectJson);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 向缓存中设置字符串内容 并带有有效期时间
     *
     * @param key     key 存储key值
     * @param seconds 有效时间(单位 秒)
     * @param value   value 存储value值
     * @return 存储成功 true 否则 false
     * @throws Exception
     */
    public static boolean set(String key, int seconds, Object value) throws Exception {
        Jedis jedis = null;
        try {
            String objectJson = JSON.toJSONString(value);
            jedis = jedisPool.getResource();
            jedis.setex(key, seconds, objectJson);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 删除缓存中得对象，根据key
     *
     * @param key
     * @return 存储成功 true 否则 false
     */
    public static boolean del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 根据key 获取内容
     *
     * @param key
     */
    public static Object get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Object value = jedis.get(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }


    /**
     * 根据key 获取对象
     *
     * @param key
     */
    public static <T> T get(String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = jedis.get(key);
            return JSON.parseObject(value, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 根据key 获取对象
     *
     * @param key
     */
    public static <T> List<T> lGet(String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = jedis.get(key);
            return JSON.parseArray(value, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 向缓存中设置对象
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean hset(String key, String field, Object value) {
        Jedis jedis = null;
        try {
            String objectJson = JSON.toJSONString(value);
            jedis = jedisPool.getResource();
            jedis.hset(key, field, objectJson);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 向缓存中设置对象
     *
     * @param key
     * @param field
     * @return
     */
    public static String hget(String key, String field) {
        String result = "";
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hget(key, field);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 向缓存中设置对象
     *
     * @param key
     * @param field
     * @return
     */
    public static List<String> hget(String key, String... field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hmget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 向缓存中设置对象
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean zset(String key, double score, Object value) {
        Jedis jedis = null;
        try {
            String objectJson = JSON.toJSONString(value);
            jedis = jedisPool.getResource();
            jedis.zadd(key, score, objectJson);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }


    /**
     * 向缓存中设置对象list
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean lpush(String key, Object value) {
        Jedis jedis = null;
        try {
            String objectJson = JSON.toJSONString(value);
            jedis = jedisPool.getResource();
            jedis.lpush(key, objectJson);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 向缓存中设置对象hash
     *
     * @param key
     * @param map
     * @return
     */
    public static boolean hmset(String key, Map map) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hmset(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 向缓存中设置对象hash
     *
     * @param key
     * @param scoreMembers
     * @return
     */
    public static boolean zadd(String key, Map<String, Double> scoreMembers) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.zadd(key, scoreMembers);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 向缓存中设置对象hash
     *
     * @param key
     * @return
     */
    public static List<String> hvals(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hvals(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 向缓存中设置对象hash
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Object[] zrange(String key, Long start, Long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Set<String> zset = jedis.zrange(key, start, end);
            return zset.toArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }


    /**
     * 根据key 获取对象
     *
     * @param key
     * @return
     */
    public static <T> T get(String key, String field, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String value = jedis.hget(key, field);
            return JSON.parseObject(value, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 根据key 获取对象
     *
     * @param key
     * @return
     */
    public static <T> List<T> getList(String key, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> jsons = jedis.lrange(key, 0, -1);
            for (String json : jsons) {
                list.add(JSON.parseObject(json, clazz));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }


}
