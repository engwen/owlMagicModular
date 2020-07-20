package com.owl.redis.function;

import redis.clients.jedis.Jedis;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2020/7/17.
 */

@FunctionalInterface
public interface RedisCommitLamda<T> {
    T commit(Jedis jedis);
}
