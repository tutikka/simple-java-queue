# Simple Java Queue
A simple messaging system for Java based on the publish/subscribe model. The implementation is based on broadcasting datagram packets for efficient communication.

## Features
* Nodes can subscribe to the queue to start receiving messages
* Nodes can publish messages so that all subscribed nodes will receive them
* Nodes can unsubscribe and resubscribe to stop and start receiving messages as needed

## Build
To build the library on the command line, follow the example below.
Clone the repository:
```
$ git clone https://github.com/tutikka/simple-java-queue.git
```
Change directory:
```
$ cd simple-java-queue
```
Build using `ant`:
```
$ ant
```

A `dist` directory will be created to contain the result.

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
```
To receive messages, you need to subscribe to the queue:
```java
queue.subscribe();
```
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
```

## Test Client
A full command line example application is provided in `com.tt.simplejavaqueue.test.QueueClient`. For Linux and Unix-like environments, as well as Mac OS, you can use the included shell script to start the client:
```
$ sh ./client.sh 
[pool-1-thread-1] INFO com.tt.simplejavaqueue.MulticastSocketListener - multicast socket listener has started
subscribe
[main] INFO com.tt.simplejavaqueue.Queue - sent message (packet length = 264)
[main] INFO com.tt.simplejavaqueue.Queue - sent message (packet length = 257)
[main] INFO com.tt.simplejavaqueue.Queue - subscribed to queue at 224.0.0.0
** { serialVersionUID: 1, node: { address: 10.0.1.21 }, time: 21.03.2016 16:40:25, type: 10002, data: node has subscribed }
[pool-1-thread-1] INFO com.tt.simplejavaqueue.MulticastSocketListener - received message (packet length = 257)
publish hello!
>> { serialVersionUID: 1, node: { address: 10.0.1.21 }, time: 21.03.2016 16:40:32, type: 0, data: { message: hello!, transientMessage: hello! } }
[main] INFO com.tt.simplejavaqueue.Queue - sent message (packet length = 314)
<< { serialVersionUID: 1, node: { address: 10.0.1.21 }, time: 21.03.2016 16:40:32, type: 0, data: { message: hello!, transientMessage: null } }
[pool-1-thread-1] INFO com.tt.simplejavaqueue.MulticastSocketListener - received message (packet length = 314)
unsubscribe
[main] INFO com.tt.simplejavaqueue.Queue - sent message (packet length = 266)
** { serialVersionUID: 1, node: { address: 10.0.1.21 }, time: 21.03.2016 16:40:35, type: 10001, data: node is about to unsubscribe }
[pool-1-thread-1] INFO com.tt.simplejavaqueue.MulticastSocketListener - received message (packet length = 266)
[main] INFO com.tt.simplejavaqueue.Queue - sent message (packet length = 259)
[main] INFO com.tt.simplejavaqueue.Queue - unsubscribed from queue at address 224.0.0.0
quit
Have a nice day!
[pool-1-thread-1] INFO com.tt.simplejavaqueue.MulticastSocketListener - multicast socket is closed
[pool-1-thread-1] INFO com.tt.simplejavaqueue.MulticastSocketListener - multicast socket listener has stopped
```

## Logging
Logging is done using [SLF4J](http://www.slf4j.org), which allows a logging implementation of your choice to be binded and used (or you can use the provided simple logger). Please check the link for details.
