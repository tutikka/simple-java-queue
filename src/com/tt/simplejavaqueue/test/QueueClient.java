package com.tt.simplejavaqueue.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.tt.simplejavaqueue.Message;
import com.tt.simplejavaqueue.Queue;
import com.tt.simplejavaqueue.QueueListener;

public class QueueClient implements QueueListener {

	private QueueClient() throws IOException {
		Queue queue = new Queue.Builder()
			.address("224.0.0.0")
			.port(9999)
			.maxMessageLength(8192)
			.queueListener(this)
			.build();		
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			String line;
			while ((line = in.readLine()) != null) {
				String[] args = line.split(" ");
				if (args == null || args.length < 1) {
					continue;
				}
				switch (args[0]) {
				case "subscribe" : subscribe(queue, args); break;
				case "unsubscribe" : unsubscribe(queue, args); break;
				case "publish" : publish(queue, args); break;
				case "quit" : quit(queue, args); return;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void subscribe(Queue queue, String[] args) {
		queue.subscribe();
	}
	
	private void unsubscribe(Queue queue, String[] args) {
		queue.unsubscribe();
	}
	
	private void publish(Queue queue, String[] args) {
		if (args.length == 2) {
			Message message = Message.Factory.userMessage(new TestObject(args[1], args[1]));
			System.out.println(">> " + message);
			queue.publish(message);
		}
	}
	
	private void quit(Queue queue, String[] args) {
		queue.close();
		System.out.println("Have a nice day!");
	}
	
	public static void main(String[] args) throws IOException {
		new QueueClient();
	}

	@Override
	public void onMessagePublish(Message message) {
		System.out.println("<< " + message);
	}

	@Override
	public void onNodePreSubscribe(Message message) {
		System.out.println("** " + message);
	}

	@Override
	public void onNodePreUnsubscribe(Message message) {
		System.out.println("** " + message);
	}
	
	@Override
	public void onNodePostSubscribe(Message message) {
		System.out.println("** " + message);
	}

	@Override
	public void onNodePostUnsubscribe(Message message) {
		System.out.println("** " + message);
	}
	
}
