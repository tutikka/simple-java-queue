package com.tt.simplejavaqueue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class representing the queue.
 * 
 * @author Tuomas Tikka
 */
public class Queue {

	private static final Logger logger = LoggerFactory.getLogger(Queue.class);
	
	private String address;
	
	private int port;
	
	private long maxMessageLength;
	
	private Set<QueueListener> queueListeners;
	
	private MulticastSocket multicastSocket;
	
	private MulticastSocketListener multicastSocketListener;
	
	private ExecutorService executorService;
	
	/**
	 * Class that can be used to obtain new queue instances.
	 */
	public static class Builder {
		
		private static final Logger logger = LoggerFactory.getLogger(Builder.class);
		
		private String address = "224.0.0.0";
		
		private int port = 9999;
		
		private long maxMessageLength = 8192;
		
		private Set<QueueListener> queueListeners = new HashSet<QueueListener>();
		
		public Builder() {
			logger.trace("init");
		}
		
		public Builder address(String address) {
			logger.trace("address");
			logger.debug("address = " + address);
			this.address = address;
			return (this);
		}
		
		public Builder port(int port) {
			logger.trace("port");
			logger.debug("port = " + port);
			this.port = port;
			return (this);
		}
		
		public Builder maxMessageLength(long maxMessageLength) {
			logger.trace("maxMessageLength");
			logger.debug("maxMessageLength = " + maxMessageLength);
			this.maxMessageLength = maxMessageLength;
			return (this);
		}
		
		public Builder queueListener(QueueListener queueListener) {
			logger.trace("queueListener");
			logger.debug("queueListener = " + queueListener.getClass().getName());
			this.queueListeners.add(queueListener);
			return (this);
		}
		
		public Queue build() throws IOException {
			logger.trace("build");
			return (new Queue(address, port, maxMessageLength, queueListeners));
		}
		
	}
	
	/**
	 * Clean up resources (multicast socket and listener).
	 */
	public void close() {
		logger.trace("close");
		multicastSocket.close();
		executorService.shutdown();
	}
	
	/**
	 * Subscribe to the queue to start receiving messages.
	 */
	public void subscribe() {
		logger.trace("subscribe");
		try {
			sendMessage(Message.Factory.systemNodePreSubscribe("node is about to subscribe"));
			multicastSocket.joinGroup(InetAddress.getByName(address));
			sendMessage(Message.Factory.systemNodePostSubscribe("node has subscribed"));
			logger.info("subscribed to queue at " + address);
		} catch (Exception e) {
			logger.error("error subscribing to queue: " + e.getMessage());
		}
	}
	
	/**
	 * Unsubscribe from the queue to stop receiving messages.
	 */
	public void unsubscribe() {
		logger.trace("unsubscribe");
		try {
			sendMessage(Message.Factory.systemNodePreUnsubscribe("node is about to unsubscribe"));
			multicastSocket.leaveGroup(InetAddress.getByName(address));
			sendMessage(Message.Factory.systemNodePostUnsubscribe("node has unsubscribed"));
			logger.info("unsubscribed from queue at address " + address);
		} catch (Exception e) {
			logger.error("error unsubscribing from queue: " + e.getMessage());
		}
	}
	
	/**
	 * Publish a message to the queue (received by all subscribers).
	 * 
	 * @param message The message to publish
	 */
	public void publish(Message message) {
		logger.trace("publish");
		sendMessage(message);
	}
	
	/*** private ***/
	
	private Queue(String address, int port, long maxMessageLength, Set<QueueListener> queueListeners) throws IOException {
		logger.trace("Queue");
		logger.debug("address = " + address);
		logger.debug("port = " + port);
		logger.debug("maxMessageLength = " + maxMessageLength);
		logger.debug("queueListeners = " + queueListeners.size());
		this.address = address;
		this.port = port;
		this.maxMessageLength = maxMessageLength;
		this.queueListeners = queueListeners;
		multicastSocket = new MulticastSocket(port);
		executorService = Executors.newSingleThreadScheduledExecutor();
		multicastSocketListener = new MulticastSocketListener(multicastSocket, maxMessageLength, queueListeners);
		executorService.submit(multicastSocketListener);
	}
	
	private void sendMessage(Message message) {
		logger.trace("sendMessage");
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(message);
			byte[] data = baos.toByteArray();
			if (data.length > maxMessageLength) {
				logger.error("message too long (" + data.length + " vs " + maxMessageLength + ")");
				return;
			}
	        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(address), port);
	        multicastSocket.send(datagramPacket);
	        logger.info("sent message (packet length = " + data.length + ")");
		} catch (IOException e) {
			logger.error("error sending message: " + e.getMessage());
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				logger.warn("error closing output stream (byte array): " + e.getMessage());
			}
			try {
				baos.close();
			} catch (IOException e) {
				logger.warn("error closing output stream (object): " + e.getMessage());
			}
		}
	}
	
}
