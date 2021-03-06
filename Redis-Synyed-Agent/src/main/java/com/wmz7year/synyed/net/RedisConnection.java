package com.wmz7year.synyed.net;

import java.io.Closeable;

import com.wmz7year.synyed.entity.RedisCommand;
import com.wmz7year.synyed.exception.RedisProtocolException;
import com.wmz7year.synyed.packet.redis.RedisPacket;

/**
 * 封装Redis连接对象的接口<br>
 * 
 * @Title: RedisConnection.java
 * @Package com.wmz7year.synyed.net
 * @author jiangwei (ydswcy513@gmail.com)
 * @date 2015年12月10日 下午3:20:31
 * @version V1.0
 */
public interface RedisConnection extends Closeable {

	/**
	 * 连接到Redis的方法<br>
	 * 
	 * @param address
	 *            redis服务器地址
	 * @param port
	 *            redis端口
	 * @param timeout
	 *            连接超时时间
	 * @return true为连接成功 false为连接失败
	 * @throws RedisProtocolException
	 *             当连接发生问题时抛出该异常
	 */
	public boolean connect(String address, int port, long timeout) throws RedisProtocolException;

	/**
	 * 连接到Redis的方法<br>
	 * 
	 * @param address
	 *            redis服务器地址
	 * @param port
	 *            redis端口
	 * @param password
	 *            redis密码
	 * @param timeout
	 *            连接超时时间
	 * @return true为连接成功 false为连接失败
	 * @throws RedisProtocolException
	 *             当连接发生问题时抛出该异常
	 */
	public boolean connect(String address, int port, String password, long timeout) throws RedisProtocolException;

	/**
	 * 判断是否连接上的标识位
	 * 
	 * @return true为连接上 fasle为未连接
	 */
	public boolean isConnected();

	/**
	 * 发送Redis命令的方法<br>
	 * 该方法会阻塞当前线程直到获取到响应内容或者超时<br>
	 * 超时时间为默认连接超时时间
	 * 
	 * @param command
	 *            需要执行的redis命令对象
	 * @return redis响应包
	 * @throws RedisProtocolException
	 *             当发生错误时抛出该异常
	 */
	public RedisPacket sendCommand(RedisCommand command) throws RedisProtocolException;

	/**
	 * 发送Redis命令的方法<br>
	 * 调用该方法后会监听所有响应内容<br>
	 * 并且会独占该Redis连接对象 无法执行其他Redis命令<br>
	 * 所有Redis服务器发送的数据包内容均会发送到RedisCommandResponseListener中<br>
	 * 直到调用cancalResponseListener后会恢复该连接的正常状态
	 * 
	 * @param command
	 *            需要执行的redis命令对象
	 * @param listener
	 *            响应内容监听器
	 * @throws RedisProtocolException
	 *             当发生错误时抛出该异常
	 */
	public void sendCommand(RedisCommand command, RedisResponseListener listener) throws RedisProtocolException;

	/**
	 * 移除响应监听器的方法
	 * 
	 * @param listener
	 *            监听器对象
	 */
	public void cancalResponseListener(RedisResponseListener listener);
}
