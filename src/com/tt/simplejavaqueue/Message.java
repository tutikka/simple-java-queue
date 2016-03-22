package com.tt.simplejavaqueue;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class representing a message that is published to the queue.
 * 
 * @author Tuomas Tikka
 */
public class Message implements Serializable {

	public static final long serialVersionUID = 1L;

	// user (application) generated message
	public static final int TYPE_USER = 0;
	
	// system message that is automatically published just before a node subscribes to the queue
	public static final int TYPE_SYSTEM_NODE_PRE_SUBSCRIBE = 10000;
	
	// system message that is automatically published just before a node unsubscribes from the queue
	public static final int TYPE_SYSTEM_NODE_PRE_UNSUBSCRIBE = 10001;
	
	// system message that is automatically published right after a node has subscribed to the queue
	public static final int TYPE_SYSTEM_NODE_POST_SUBSCRIBE = 10002;
	
	// system message that is automatically published right after a node has unsubscribed from the queue
	public static final int TYPE_SYSTEM_NODE_POST_UNSUBSCRIBE = 10003;
	
	// the node that authored the message
	private Node node;
	
	// timestamp when the message was created
	private long time;
	
	// the message type (user generated or system)
	private int type;
	
	// the message data
	private Serializable data;
	
	/**
	 * Class that can be used to obtain new message instances.
	 */
	public static class Factory {
		
		/**
		 * Construct a new message from any serializable data.
		 * 
		 * @param 	data The message data
		 * @return 	The message
		 */
		public static Message userMessage(Serializable data) {
			Message message = new Message();
			message.setNode(Node.Factory.fromCurrentHost());
			message.setTime(System.currentTimeMillis());
			message.setType(TYPE_USER);
			message.setData(data);
			return (message);
		}
		
		/**
		 * Construct a new message with the data as a <code>String</code> value.
		 * 
		 * @param 	data The message data
		 * @return 	The message
		 */
		public static Message userMessage(String data) {
			return (userMessage((Serializable) data));
		}
		
		/**
		 * Construct a new message with the data as an <code>Integer</code> value.
		 * 
		 * @param 	data The message data
		 * @return 	The message
		 */
		public static Message userMessage(Integer data) {
			return (userMessage((Serializable) data));
		}
		
		/**
		 * Construct a new message with the data as a <code>Long</code> value.
		 * 
		 * @param 	data The message data
		 * @return 	The message
		 */
		public static Message userMessage(Long data) {
			return (userMessage((Serializable) data));
		}
		
		/**
		 * Construct a new message with the data as a <code>Float</code> value.
		 * 
		 * @param 	data The message data
		 * @return 	The message
		 */
		public static Message userMessage(Float data) {
			return (userMessage((Serializable) data));
		}
		
		/**
		 * Construct a new message with the data as a <code>Double</code> value.
		 * 
		 * @param 	data The message data
		 * @return 	The message
		 */
		public static Message userMessage(Double data) {
			return (userMessage((Serializable) data));
		}
		
		/**
		 * Construct a new message with the data as a <code>Byte</code> value.
		 * 
		 * @param 	data The message data
		 * @return 	The message
		 */
		public static Message userMessage(Byte data) {
			return (userMessage((Serializable) data));
		}
		
		/**
		 * Construct a new message with the data as a <code>Boolean</code> value.
		 * 
		 * @param 	data The message data
		 * @return 	The message
		 */
		public static Message userMessage(Boolean data) {
			return (userMessage((Serializable) data));
		}
		
		/**
		 * Construct a new message with the data as a <code>Character</code> value.
		 * 
		 * @param 	data The message data
		 * @return 	The message
		 */
		public static Message userMessage(Character data) {
			return (userMessage((Serializable) data));
		}
		
		/**
		 * Construct a new message with the data as a <code>Date</code> value.
		 * 
		 * @param 	data The message data
		 * @return 	The message
		 */
		public static Message userMessage(Date data) {
			return (userMessage((Serializable) data));
		}
		
		static Message systemNodePreSubscribe(Serializable data) {
			Message message = new Message();
			message.setNode(Node.Factory.fromCurrentHost());
			message.setTime(System.currentTimeMillis());
			message.setType(TYPE_SYSTEM_NODE_PRE_SUBSCRIBE);
			message.setData(data);
			return (message);
		}
		
		static Message systemNodePreUnsubscribe(Serializable data) {
			Message message = new Message();
			message.setNode(Node.Factory.fromCurrentHost());
			message.setTime(System.currentTimeMillis());
			message.setType(TYPE_SYSTEM_NODE_PRE_UNSUBSCRIBE);
			message.setData(data);
			return (message);
		}
		
		static Message systemNodePostSubscribe(Serializable data) {
			Message message = new Message();
			message.setNode(Node.Factory.fromCurrentHost());
			message.setTime(System.currentTimeMillis());
			message.setType(TYPE_SYSTEM_NODE_POST_SUBSCRIBE);
			message.setData(data);
			return (message);
		}
		
		static Message systemNodePostUnsubscribe(Serializable data) {
			Message message = new Message();
			message.setNode(Node.Factory.fromCurrentHost());
			message.setTime(System.currentTimeMillis());
			message.setType(TYPE_SYSTEM_NODE_POST_UNSUBSCRIBE);
			message.setData(data);
			return (message);
		}
		
		/**
		 * Return the message data.
		 * 
		 * @param 	message The message
		 * @return 	The message data
		 */
		public static Serializable serializableData(Message message) {
			return (message.getData());
		}
		
		/**
		 * Return the message data as <code>String</code>.
		 * 
		 * @param 	message The message
		 * @return	The message data
		 * @throws 	ClassCastException If the data cannot be cast to a <code>String</code>
		 */
		public static String stringData(Message message) throws ClassCastException {
			return ((String) message.getData());
		}
		
		/**
		 * Return the message data as <code>Long</code>.
		 * 
		 * @param 	message The message
		 * @return	The message data
		 * @throws 	ClassCastException If the data cannot be cast to a <code>Long</code>
		 */
		public static Long longData(Message message) throws ClassCastException {
			return ((Long) message.getData());
		}
		
		/**
		 * Return the message data as <code>Float</code>.
		 * 
		 * @param 	message The message
		 * @return	The message data
		 * @throws 	ClassCastException If the data cannot be cast to a <code>Float</code>
		 */
		public static Float floatData(Message message) throws ClassCastException {
			return ((Float) message.getData());
		}
		
		/**
		 * Return the message data as <code>Double</code>.
		 * 
		 * @param 	message The message
		 * @return	The message data
		 * @throws 	ClassCastException If the data cannot be cast to a <code>Double</code>
		 */
		public static Double doubleData(Message message) throws ClassCastException {
			return ((Double) message.getData());
		}
		
		/**
		 * Return the message data as <code>Byte</code>.
		 * 
		 * @param 	message The message
		 * @return	The message data
		 * @throws 	ClassCastException If the data cannot be cast to a <code>Byte</code>
		 */
		public static Byte byteData(Message message) throws ClassCastException {
			return ((Byte) message.getData());
		}
		
		/**
		 * Return the message data as <code>Boolean</code>.
		 * 
		 * @param 	message The message
		 * @return	The message data
		 * @throws 	ClassCastException If the data cannot be cast to a <code>Boolean</code>
		 */
		public static Boolean booleanData(Message message) throws ClassCastException {
			return ((Boolean) message.getData());
		}
		
		/**
		 * Return the message data as <code>Character</code>.
		 * 
		 * @param 	message The message
		 * @return	The message data
		 * @throws 	ClassCastException If the data cannot be cast to a <code>Character</code>
		 */
		public static Character characterData(Message message) throws ClassCastException {
			return ((Character) message.getData());
		}
		
		/**
		 * Return the message data as <code>Date</code>.
		 * 
		 * @param 	message The message
		 * @return	The message data
		 * @throws 	ClassCastException If the data cannot be cast to a <code>Date</code>
		 */
		public static Date dateData(Message message) throws ClassCastException {
			return ((Date) message.getData());
		}
		
	}
	
	/*** getters and setters ***/

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Serializable getData() {
		return data;
	}

	public void setData(Serializable data) {
		this.data = data;
	}
	
	/*** override ***/
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ serialVersionUID: ");
		sb.append(serialVersionUID);
		sb.append(", node: ");
		sb.append(node);
		sb.append(", time: ");
		sb.append(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(time)));
		sb.append(", type: ");
		sb.append(type);
		sb.append(", data: ");
		sb.append(data);
		sb.append(" }");
		return (sb.toString());
	}
	
	/** private ***/
	
	private Message() {
	}
	
}
