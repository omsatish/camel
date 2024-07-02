package com.example.camel.component;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleTimer extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:simpletimer?period=5000")
                .routeId("simpleTimerId")
                .setBody(constant("Hello World"))
                .log("${body}");
    }
}
