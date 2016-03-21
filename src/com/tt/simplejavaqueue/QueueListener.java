package com.tt.simplejavaqueue;

public interface QueueListener {

	public void onMessagePublish(Message message);
	
	public void onNodePreSubscribe(Message message);
	
	public void onNodePreUnsubscribe(Message message);
	
	public void onNodePostSubscribe(Message message);
	
	public void onNodePostUnsubscribe(Message message);
	
}
