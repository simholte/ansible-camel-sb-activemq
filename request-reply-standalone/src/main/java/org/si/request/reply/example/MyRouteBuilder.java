package org.si.request.reply.example;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by simholte on 1/6/16.
 */
// auto detected and injected by the spring-boot-camel component
@Component
public class MyRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // simple test to validate that Camel is up and the servlet is responsive
        from("servlet:///hello").log("Git Here").transform().constant("Hello from Camel");

        // The request side of the request-reply pattern
        from("servlet:///queue")
                // you must specify that the ExchangePattern is InOut so the jms component waits for our response
                .setExchangePattern(ExchangePattern.InOut)
                //push the body to the activemq named request
                // specify the queue to monitor for the response
                .to("activemq:queue:request?replyTo=reply");

        // note that the route blocks until a message appears on the reply queue with the some correlationId
        // this complexity is hidden from you by the Camel implementation of the request-reply pattern
        // http://camel.apache.org/jms.html

        // Monitor the request queue for messages
        // exchange -> is a lambda expression, intellij did that, I have a lot to learn about Java 8
        from("activemq:queue:request").process(exchange -> {
            // sleep for 3 seconds to simulate some sort of business logic occuring
            Thread.sleep(3000);
            // set a response message in the Body of the Out Message
            exchange.getOut().setBody("Response from queue");
            // this route ends and returns the value back to the camel jms component
            // Camel knows that a response to this message should be routed to the reply queue
            // with the appropriate correlationId, all of which happens auto-magically

        });


    }
}
