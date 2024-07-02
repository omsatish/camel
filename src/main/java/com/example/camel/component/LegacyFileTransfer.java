package com.example.camel.component;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class LegacyFileTransfer extends RouteBuilder {

    Logger logger = LoggerFactory.getLogger(LegacyFileTransfer.class);

    @Override
    public void configure() throws Exception {
        from("file:data/input?fileName=inputFile.txt")
                .routeId("legacyFileTransferId")
                .process(exchange -> {
                    String body = exchange.getIn().getBody(String.class);
                    logger.info("legacyFileTransfer received: {}", body);
                })
                .to("file:data/output?fileName=outputFile.txt");
    }
}
