package com.owl.redis.util;

import com.owl.redis.function.RedisCommitLamda;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2017/4/7.
 */
public class RedisUtil {
    /*
     * 判断是否存在key的信息
     * @param key
     * @return
     */
    public static boolean checkKeyExists(String key) {
        return GetRedis.commit(jedis -> jedis.exists(key));
    }

    public static boolean checkKeyExists(byte[] key) {
        return GetRedis.commit(jedis -> jedis.exists(key));
    }

    /*
     * 向redis中存储key-value
     * @param key
     * @param value
     */
    public static void setKeyValue(String key, String value) {
        GetRedis.commit(jedis -> jedis.set(key, value));
    }

    public static void setKeyValue(byte[] key, byte[] value) {
        GetRedis.commit(jedis -> jedis.set(key, value));
    }

    public static void msetKeyValue(String... keyValue) {
        GetRedis.commit(jedis -> jedis.mset(keyValue));
    }

    /*
     * 從redis中讀取key的value
     * @param key
     * @return
     */
    public static String getKeyValue(String key) {
        return GetRedis.commit((jedis) -> jedis.get(key));
    }

    public static byte[] getKeyValue(byte[] key) {
        return GetRedis.commit((jedis) -> jedis.get(key));
    }


    /*
     * 删除key
     * @param key
     */
    public static void deleteKey(String key) {
        GetRedis.commit((jedis) -> jedis.del(key));
    }

    public static void deleteKey(byte[] key) {
        GetRedis.commit((jedis) -> jedis.del(key));
    }

//    /*
//     * 设置用户登录信息过期时间
//     * @param key
//     */
//    public static void setOverTime(String key) {
//        GetRedis.commit((jedis) -> jedis.expire(key, Integer.parseInt(PropertiesUtil.readConfigProperties("redis.login.over.time"))));
//    }
//
//    /*
//     * 设置token过期时间
//     * @param key
//     */
//    public static void setOverTime(byte[] key) {
//        GetRedis.commit((jedis) -> jedis.expire(key, Integer.parseInt(PropertiesUtil.readConfigProperties("redis.login.over.time"))));
//    }

    /**
     * 终极杀器
     * @param redisCommitLamda lamda
     * @param <T>              泛型
     * @return 返回值
     */
    public static <T> T commit(RedisCommitLamda<T> redisCommitLamda) {
        return GetRedis.commit(redisCommitLamda);
    }

    public static JedisPool getPool(String host, String port) {
        return GetRedis.getPool(host, port);
    }


    private static class GetRedis {
        private static String host;
        private static String port;
        private static JedisPool pool;

        private synchronized static void initPool() {
            if (pool == null) {
                JedisPoolConfig config = new JedisPoolConfig();

                config.setMaxIdle(200);
                config.setMaxTotal(300);

                config.setTestOnBorrow(false);
                config.setTestOnReturn(false);

//                String host = PropertiesUtil.readConfigProperties("redis.host");
//                int port = Integer.parseInt(PropertiesUtil.readConfigProperties("redis.port"));

                pool = new JedisPool(config, host, Integer.parseInt(port), 3000);
            }
        }

        private static <T> T commit(RedisCommitLamda<T> redisCommitLamda) {
            Jedis jedis = null;
            try {
                initPool();
                jedis = pool.getResource();
                return redisCommitLamda.commit(jedis);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }

        private static JedisPool getPool(String host, String port) {
            GetRedis.host = host;
            GetRedis.port = port;
            return pool;
        }

//        private static void returnJedisPool(Jedis jedis){
//            if(pool != null) {
//                pool.returnResource(jedis);
//            }
//        }
//
//        private static void returnBrokenResourceJedisPool(Jedis jedis){
//            if(pool != null) {
//                pool.returnBrokenResource(jedis);
//            }
//        }

    }

}

