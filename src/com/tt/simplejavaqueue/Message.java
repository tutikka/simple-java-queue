package com.tt.simplejavaqueue;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable {

	public static final long serialVersionUID = 1L;

	public static final int TYPE_USER = 0;
	
	public static final int TYPE_SYSTEM_NODE_PRE_SUBSCRIBE = 10000;
	
	public static final int TYPE_SYSTEM_NODE_PRE_UNSUBSCRIBE = 10001;
	
	public static final int TYPE_SYSTEM_NODE_POST_SUBSCRIBE = 10002;
	
	public static final int TYPE_SYSTEM_NODE_POST_UNSUBSCRIBE = 10003;
	
	private Node node;
	
	private long time;
	
	private int type;
	
	private Serializable data;
	
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

	public static class Factory {
		
		public static Message systemNodePreSubscribe(Serializable data) {
			Message message = new Message();
			message.setNode(Node.Factory.fromCurrentHost());
			message.setTime(System.currentTimeMillis());
			message.setType(TYPE_SYSTEM_NODE_PRE_SUBSCRIBE);
			message.setData(data);
			return (message);
		}
		
		public static Message systemNodePreUnsubscribe(Serializable data) {
			Message message = new Message();
			message.setNode(Node.Factory.fromCurrentHost());
			message.setTime(System.currentTimeMillis());
			message.setType(TYPE_SYSTEM_NODE_PRE_UNSUBSCRIBE);
			message.setData(data);
			return (message);
		}
		
		public static Message systemNodePostSubscribe(Serializable data) {
			Message message = new Message();
			message.setNode(Node.Factory.fromCurrentHost());
			message.setTime(System.currentTimeMillis());
			message.setType(TYPE_SYSTEM_NODE_POST_SUBSCRIBE);
			message.setData(data);
			return (message);
		}
		
		public static Message systemNodePostUnsubscribe(Serializable data) {
			Message message = new Message();
			message.setNode(Node.Factory.fromCurrentHost());
			message.setTime(System.currentTimeMillis());
			message.setType(TYPE_SYSTEM_NODE_POST_UNSUBSCRIBE);
			message.setData(data);
			return (message);
		}
		
		public static Message userMessage(Serializable data) {
			Message message = new Message();
			message.setNode(Node.Factory.fromCurrentHost());
			message.setTime(System.currentTimeMillis());
			message.setType(TYPE_USER);
			message.setData(data);
			return (message);
		}
		
		public static Message userMessage(String data) {
			return (userMessage((Serializable) data));
		}
		
		public static Message userMessage(Integer data) {
			return (userMessage((Serializable) data));
		}
		
		public static Message userMessage(Long data) {
			return (userMessage((Serializable) data));
		}
		
		public static Message userMessage(Float data) {
			return (userMessage((Serializable) data));
		}
		
		public static Message userMessage(Double data) {
			return (userMessage((Serializable) data));
		}
		
		public static Message userMessage(Byte data) {
			return (userMessage((Serializable) data));
		}
		
		public static Message userMessage(Boolean data) {
			return (userMessage((Serializable) data));
		}
		
		public static Message userMessage(Character data) {
			return (userMessage((Serializable) data));
		}
		
		public static Message userMessage(Date data) {
			return (userMessage((Serializable) data));
		}
		
		public static Serializable serializableData(Message message) {
			return (message.getData());
		}
		
		public static String stringData(Message message) {
			return ((String) message.getData());
		}
		
		public static Long longData(Message message) {
			return ((Long) message.getData());
		}
		
		public static Float floatData(Message message) {
			return ((Float) message.getData());
		}
		
		public static Double doubleData(Message message) {
			return ((Double) message.getData());
		}
		
		public static Byte byteData(Message message) {
			return ((Byte) message.getData());
		}
		
		public static Boolean booleanData(Message message) {
			return ((Boolean) message.getData());
		}
		
		public static Character characterData(Message message) {
			return ((Character) message.getData());
		}
		
		public static Date dateData(Message message) {
			return ((Date) message.getData());
		}
		
	}
	
	private Message() {
	}

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
	
}
