package com.tt.simplejavaqueue;

import java.io.Serializable;
import java.net.InetAddress;

public class Node implements Serializable {

	public static final long serialVersionUID = 1L;
	
	// The address of the node (hostname, IP address, etc.)
	private String address;
	
	/**
	 * Class that can be used to obtain new node instances.
	 */
	public static class Factory {
		
		/**
		 * Return a new node representing the current host.
		 * 
		 * @return The new node
		 */
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
	
	/*** getters and setters ***/
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	/*** override ***/
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ address: ");
		sb.append(address);
		sb.append(" }");
		return (sb.toString());
	}
	
	/*** private ***/
	
	private Node() {
	}
	
}
