package com.tt.simplejavaqueue;

import java.io.Serializable;
import java.net.InetAddress;

public class Node implements Serializable {

	public static final long serialVersionUID = 1L;
	
	private String address;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ address: ");
		sb.append(address);
		sb.append(" }");
		return (sb.toString());
	}
	
	public static class Factory {
		
		public static Node fromCurrentHost() {
			Node node = new Node();
			try {
				node.setAddress(InetAddress.getLocalHost().getHostAddress());
			} catch (Exception e) {
				node.setAddress("127.0.0.1");
			}
			return (node);
		}
		
	}
	
	private Node() {
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
