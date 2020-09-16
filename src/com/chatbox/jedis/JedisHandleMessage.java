package com.chatbox.jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisHandleMessage {

	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	public static List<String> getHistoryMsg(String sender, String receiver) {
		String key = new StringBuilder(sender).append(":").append(receiver).toString();
		Jedis jedis = null;
		List<String> historyData = null;
		try {
		jedis = pool.getResource(); 
		jedis.auth("123456");
		historyData = jedis.lrange(key, 0, -1);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			jedis.close();
		}
		return historyData;
	}

	public static void saveChatMessage(String sender, String receiver, String message) {
		HashMap<String, String> senderData = new HashMap<>();
		HashMap<String, String> receiverData = new HashMap<>();
		String senderKey = new StringBuilder(sender).append(":").append(receiver).toString();
		String receiverKey = new StringBuilder(receiver).append(":").append(sender).toString();
		System.out.println("senderKey = "+senderKey);
		
		senderData.put(sender, message);
		receiverData.put(receiver, message);
		
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		
		jedis.rpush(senderKey, message);
		jedis.rpush(receiverKey, message);

		jedis.close();
		
		jedis = pool.getResource();
		jedis.auth("123456");
		
		jedis.hmset(sender, receiverData);
		jedis.hmset(receiver, senderData);
		
		jedis.close();
	}
	
	public static Map<String, String> getHistoryChatMember(String userName){
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		
		Map<String, String> chatMemberMap = jedis.hgetAll(userName);
		
		
		return null;
	}
}
