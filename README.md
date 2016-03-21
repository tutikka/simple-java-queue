# simple-java-queue
A simple messaging system for Java based on the publish/subscribe model.

## Features
* Nodes can subscribe to the queue to start receiving messages
* Nodes can publish messages so that all subscribed nodes will receive them
* Nodes can unsubscribe and resubscribe to stop and start receiving messages as needed

## Installation
The application is a library, intended to be used in existing Java-based projects. You can install it just by including the JAR files into your project. 

## Usage
The example below shows how to obtain a reference to a queue using the Builder pattern:
```java
Queue queue = new Queue.Builder()
	.address("224.0.0.0")
	.port(9999)
	.maxMessageLength(8192)
	.queueListener(this)
	.build();	
``
To receive messages, you need to subscribe to the queue:
```java
queue.subscribe();
``
To publish messages to each subscribed node, use for example:
```java
Message message = Message.Factory.userMessage("Hello, world!");
queue.publish(message);
```
If you want to stop receiving messages, you need to unsubscribe:
```java
queue.unsubscribe();
```
To close the queue and clean up resources, use:
```java
queue.close();

## Test Client
A full command line example application is provided in `com.tt.simplejavaqueue.test.QueueClient`.

## Logging
Logging is implemented using [SLF4J](http://www.slf4j.org), which allows a logging implementation of your choice to be binded and used (or use the provided simple logger).
