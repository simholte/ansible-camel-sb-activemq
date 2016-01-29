# ansible-camel-sb-activemq

Simple Spring Boot application that implements the [Request Reply Enterprise Integration Pattern](http://www.enterpriseintegrationpatterns.com/patterns/messaging/RequestReply.html)

The request-reply-standalone folder contains the source code for the simple springboot application that exposes a simple servlet API.  Once a client issues a post to the API a request is added to the ActiveMQ's request queue using Camel.  Camel then caches the request and monitors the response queue for responses.  Another Camel route is monitoring the request queue, once it finds one it sleeps for 3 seconds to simulate processing and responds on the response queue.  The original request finds the response and responds back to the original servlet request.

The binary is very small so it's been copied into the provision/files directory for your convenience.  in order to see it run simply clone the repo and type **vagrant up**

Test it out by issuing a curl request
``` shell
curl -v localhost:8080/hello
```
will respond with a simple *Hello From Camel*

To initiate a request/response 
``` shell
curl -v localhost:8080/request
```
will respond with a simple *Response from queue*

Take a look at the activeMQ UI by opening a web browser `http://localhost:8161/admin` use the default credentials of admin/admin to view the traffic on the queues.
