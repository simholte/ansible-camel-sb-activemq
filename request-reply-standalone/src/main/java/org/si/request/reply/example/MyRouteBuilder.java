package org.si.request.reply.example;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by simholte on 1/6/16.
 */
@Component
public class MyRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("servlet:///hello").log("Git Here").transform().constant("Hello from Camel");

        from("servlet:///by").log("Git Here").transform().constant("Hello from Camel");



    }
}
