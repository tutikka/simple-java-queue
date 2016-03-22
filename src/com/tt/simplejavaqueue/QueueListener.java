package com.tt.simplejavaqueue;

public interface QueueListener {

	/**
	 * Called when a message is published to the queue.
	 * 
	 * @param message
	 */
	public void onMessagePublish(Message message);
	
	/**
	 * Called when a node is about to subscribe to the queue.
	 * 
	 * @param message
	 */
	public void onNodePreSubscribe(Message message);
	
	/**
	 * Called when a node is about to unsubscribe from the queue.
	 * 
	 * @param message
	 */
	public void onNodePreUnsubscribe(Message message);
	
	/**
	 * Called when a node has subscribed to the queue.
	 * 
	 * @param message
	 */
	public void onNodePostSubscribe(Message message);
	
	/**
	 * Called when a node has unsubscribed from the queue.
	 * 
	 * @param message
	 */
	public void onNodePostUnsubscribe(Message message);
	
}
