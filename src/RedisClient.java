

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;



public class RedisClient {

    private Jedis jedis;//非切片额客户端连接
    public Jedis getJedis() {
		return jedis;
	}


	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	private JedisPool jedisPool;//非切片连接池
    private ShardedJedis shardedJedis;//切片额客户端连接
    private ShardedJedisPool shardedJedisPool;//切片连接池
    
    public RedisClient() 
    { 
        initialPool(); 
        initialShardedPool(); 
        shardedJedis = shardedJedisPool.getResource(); 
        jedis = jedisPool.getResource(); 
    } 
 
    
    public static void main(String[] args) {
    	RedisClient client = new RedisClient();
    	Jedis jedis = client.getJedis();
    	client.getJedis().flushDB();
    	System.out.println(new Date());
    	for(int i=0 ;i < 10000000;i ++)
		{
			jedis.set(i+"","test" +i);
		}
    	System.out.println(new Date());
    }
    /**
     * 初始化非切片池
     */
    private void initialPool() 
    { 
        // 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxIdle(5); 
        config.setMaxWaitMillis(1000000l); 
        config.setTestOnBorrow(false); 
        jedisPool = new JedisPool(config,"127.0.0.1",6379);
    }
    
    /** 
     * 初始化切片池 
     */ 
    private void initialShardedPool() 
    { 
        // 池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxIdle(5); 
        config.setMaxWaitMillis(1000l); 
        config.setTestOnBorrow(false); 
        // slave链接 
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>(); 
        shards.add(new JedisShardInfo("127.0.0.1", 6379, "master")); 

        // 构造池 
        shardedJedisPool = new ShardedJedisPool(config, shards); 
    } 

    public void show() {     
        jedisPool.returnResource(jedis);
        shardedJedisPool.returnResource(shardedJedis);
    } 

}