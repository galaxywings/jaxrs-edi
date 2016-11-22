package org.redis;

import com.google.common.collect.Sets;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

import java.util.Set;

public class Redis{
    private JedisPool jedisPool = null;

    public Redis(JedisPool jedisPool) throws Exception {
        this.jedisPool = jedisPool;
    }

    public byte[] get(String key) throws Exception {
        byte[] result = null;
        try (Jedis jedis = jedisPool.getResource();) {
            byte[] keyAsBytes = SafeEncoder.encode(key);
            result = jedis.get(keyAsBytes);
        }
        return result;
    }

    public String set(String key, String value, String encoding) throws Exception {
        String result = null;

        byte[] keyAsBytes = SafeEncoder.encode(key);
        byte[] valueAsBytes = RedisUtils.toBytes(value, encoding);
        result = this.set(keyAsBytes, valueAsBytes, -1);

        return result;
    }

    public String set(String key, byte[] valueAsBytes, String encoding) throws Exception {
        String result = null;

        byte[] keyAsBytes = SafeEncoder.encode(key);
        result = this.set(keyAsBytes, valueAsBytes, -1);

        return result;
    }

    public String set(String key, String value, int timeout, String encoding) throws Exception {
        String result = null;

        byte[] keyAsBytes = SafeEncoder.encode(key);
        byte[] valueAsBytes = RedisUtils.toBytes(value, encoding);
        result = this.set(keyAsBytes, valueAsBytes, timeout);

        return result;
    }

    public String set(String key, byte[] valueAsBytes, int timeout, String encoding) throws Exception {
        String result = null;

        byte[] keyAsBytes = SafeEncoder.encode(key);
        result = this.set(keyAsBytes, valueAsBytes, timeout);

        return result;
    }

    public String set(byte[] keyAsBytes, byte[] valueAsBytes, int timeout) throws Exception {
        String set_result = null;
        Long set_timeout = (long) -1;

        try (Jedis jedis = jedisPool.getResource();) {

            set_result = jedis.set(keyAsBytes, valueAsBytes);
            if (timeout > -1) {
                set_timeout = jedis.expire(keyAsBytes, timeout);
            }

        }

        String result = set_result + "," + set_timeout;
        return result;
    }

    public Long expire(String key, int timeout) throws Exception {
        Long set_timeout = (long) 0;

        try (Jedis jedis = jedisPool.getResource();) {

            byte[] keyAsBytes = SafeEncoder.encode(key);

            set_timeout = jedis.expire(keyAsBytes, timeout);

        }

        Long result = set_timeout;
        return result;
    }

    public Long persist(String key) throws Exception {
        Long set_timeout = (long) 0;

        try (Jedis jedis = jedisPool.getResource();) {

            byte[] keyAsBytes = SafeEncoder.encode(key);

            set_timeout = jedis.persist(keyAsBytes);

        }

        Long result = set_timeout;
        return result;
    }

    public Boolean exists(String key) throws Exception {
        Boolean result = false;


        try (Jedis jedis = jedisPool.getResource();) {

            byte[] keyAsBytes = SafeEncoder.encode(key);

            result = jedis.exists(keyAsBytes);

        }

        return result;
    }

    public Long delete(String key) throws Exception {
        Long result = (long) -1;

        try (Jedis jedis = jedisPool.getResource();) {

            byte[] keyAsBytes = SafeEncoder.encode(key);

            result = jedis.del(keyAsBytes);

        }

        return result;
    }

    public Long ttl(String key) throws Exception {
        Long result = (long) -1;


        try (Jedis jedis = jedisPool.getResource();) {

            byte[] keyAsBytes = SafeEncoder.encode(key);

            result = jedis.ttl(keyAsBytes);

        }

        return result;
    }

    public String ping() throws Exception {
        String result = null;

        try (Jedis jedis = jedisPool.getResource();) {
            result = jedis.ping();
        }

        return result;
    }

    public Set<String> zrangeByScore(String key, Double min, Double max) throws Exception {
        Set<String> result;

        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.zrangeByScore(key, min, max);
        }

        return (result != null) ? result : Sets.newHashSet();
    }

}
