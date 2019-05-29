package cn.eblcu.sso.domain.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @ClassName RedisUtils
 * @Author 焦冬冬
 * @Date 2019/5/27 15:40
 **/

@Component
public class RedisUtils {
    private Logger logger=Logger.getLogger(RedisUtils.class);
    @Autowired
    private JedisPool jedisPool;
    //默认存储在第一个db,后期可以自行分库，或者按照数据状况来存储
    private static int indexdb=0;

    /**
     * 根据key查询value
     * @param key
     * @return
     */
    public String getByKey(String key){
        Jedis jedis=null;
        String s=null;
        try {
            jedis= jedisPool.getResource();
            jedis.select(indexdb);
            s= jedis.get(key);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            jedisPool.returnResource(jedis);
        }
        logger.info(s);
        return s;
    }

    /**
     * 设置key
     * @param key
     * @param value
     */
    public boolean setStr(String key, String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            if(jedis.exists(key)){
                System.out.println(key+"已经存在，请慎重操作");
                return false;
            }
            jedis.set(key, value);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            jedisPool.returnResource(jedis);
        }
        return true;
    }

    /**
     * 带时间的设置字符串
     * @param key
     * @param value
     * @param second
     */
    public void setStrByTime(String key, String value,int second){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            jedis.set(key, value);
            jedis.expire(key,second);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean isexists(String key){
        Jedis jedis = null;
        boolean isExists=false;
        try {
            jedis = jedisPool.getResource();
            jedis.select(indexdb);
            isExists=jedis.exists(key);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            jedisPool.returnResource(jedis);
        }
        return isExists;
    }

    /**
     * 刷新key时间
     * @param key
     * @param second
     */
    public void refreshTime(String key,int second){
        if(this.isexists(key)){
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                jedis.select(indexdb);
                jedis.expire(key,second);
            }catch (Exception e){
                System.out.println(e);
            }finally {
                jedisPool.returnResource(jedis);
            }
        }
    }

    /**
     * 根据键值删除
     * @param key
     * @return
     */
    public long delByKey(String key){
        if(this.isexists(key)) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();
                jedis.select(indexdb);
                return jedis.del(key);
            }catch (Exception e){
                System.out.println(e);
            }finally {
                jedisPool.returnResource(jedis);
            }
        }
        return 0;
    }
}
