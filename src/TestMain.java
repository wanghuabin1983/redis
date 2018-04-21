import java.util.List;

import redis.clients.jedis.Jedis;


public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Jedis jedis = new Jedis("127.0.0.1",6379);
		//存储数据到列表中
		jedis.lpush("list1", "Java");
		jedis.lpush("list1", "Html5");
		jedis.lpush("list1", "Python");
		// 获取存储的数据并输出
		List<String> list = jedis.lrange("list1", 0 ,-1);
		for(int i=0; i<list.size(); i++) {
		  System.out.println("列表项为: "+list.get(i));
		}
		/*
		 * 占用空间：304 MB (318,823,371 字节)
		for(int i=0 ;i < 10000000;i ++)
		{
			jedis.lpush(i+"","test" +i);
		}
		*/
		List<String> list1 = jedis.lrange("1000", 0 ,-1);
		for(int i=0; i<list1.size(); i++) {
		  System.out.println("列表项为: "+list1.get(i));
		}
	}

}
