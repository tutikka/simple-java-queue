package com.tt.simplejavaqueue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class responsible for listening to and receiving messages via the multicast socket.
 * 
 * @author Tuomas Tikka
 */
public class MulticastSocketListener implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(MulticastSocketListener.class);
	
	// the multicast socket
	private MulticastSocket multicastSocket;
	
	// maximum acceptable message length
	private long maxMessageLength;
	
	// set of registered listeners for receiving messages
	private Set<QueueListener> queueListeners;
	
	/**
	 * Constructor.
	 * 
	 * @param multicastSocket	The multicast socket
	 * @param maxMessageLength	The maximum acceptable message length
	 * @param queueListeners	The set of registered listeners for receiving messages
	 */
	public MulticastSocketListener(MulticastSocket multicastSocket, long maxMessageLength, Set<QueueListener> queueListeners) {
		logger.trace("init");
		this.multicastSocket = multicastSocket;
		this.maxMessageLength = maxMessageLength;
		this.queueListeners = queueListeners;
	}
	
	/*** override ***/
	
	@Override
	public void run() {
		logger.trace("run");
		DatagramPacket datagramPacket;
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		logger.info("multicast socket listener has started");
		while (true) {
			try {
				byte[] data = new byte[(int) maxMessageLength];
				datagramPacket = new DatagramPacket(data, data.length);
			    multicastSocket.receive(datagramPacket);
			    bais = new ByteArrayInputStream(data);
			    ois = new ObjectInputStream(bais);
			    Message message = (Message) ois.readObject();
			    for (QueueListener queueListener : queueListeners) {
			    	switch (message.getType()) {
			    	case Message.TYPE_USER : queueListener.onMessagePublish(message); break;
			    	case Message.TYPE_SYSTEM_NODE_PRE_SUBSCRIBE : queueListener.onNodePreSubscribe(message); break;
			    	case Message.TYPE_SYSTEM_NODE_PRE_UNSUBSCRIBE : queueListener.onNodePreUnsubscribe(message); break;
			    	case Message.TYPE_SYSTEM_NODE_POST_SUBSCRIBE : queueListener.onNodePostSubscribe(message); break;
			    	case Message.TYPE_SYSTEM_NODE_POST_UNSUBSCRIBE : queueListener.onNodePostUnsubscribe(message); break;
			    	}
			    }
			    logger.info("received message (packet length = " + datagramPacket.getLength() + ")");
			} catch (Exception e) {
				if (multicastSocket.isClosed()) {
					logger.info("multicast socket is closed");
					break;
				}
				logger.error("error receiving datagram packet: " + e.getMessage());
			} finally {
				try {
					ois.close();
				} catch (IOException e) {
					logger.warn("error closing input stream (byte array): " + e.getMessage());
				}
				try {
					bais.close();
				} catch (IOException e) {
					logger.warn("error closing input stream (object): " + e.getMessage());
				}
			}
		}
		logger.info("multicast socket listener has stopped");
	}
	
}
