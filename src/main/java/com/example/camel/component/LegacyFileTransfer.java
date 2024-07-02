package com.example.camel.component;

import com.example.camel.processor.AddressProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class LegacyFileTransfer extends RouteBuilder {

    Logger logger = LoggerFactory.getLogger(LegacyFileTransfer.class);

    BeanIODataFormat addressDataFormat = new BeanIODataFormat("InboundAddressBeanIOMapping.xml", "addressStream");

    @Override
    public void configure() throws Exception {
        from("file:data/input?fileName=inputFile.csv")
                .routeId("legacyFileTransferId")
                .split(body().tokenize("\n", 1, true))
                .unmarshal(addressDataFormat)
                .process(new AddressProcessor())
                .log(LoggingLevel.INFO, "processed body ${body}")
                .convertBodyTo(String.class)
                .to("file:data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n")
                .end();

    }
}
