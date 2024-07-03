package com.example.camel.component;

import com.example.camel.beans.InboundAddress;
import com.example.camel.beans.OutboundAddress;
import com.example.camel.processor.AddressProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RestRout extends RouteBuilder {

    BeanIODataFormat addressDataFormat = new BeanIODataFormat("InboundAddressBeanIOMapping.xml", "addressStream");

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("jetty")
                .host("0.0.0.0")
                .port(8080)
                .enableCORS(true);

        rest("/camel")
                .produces("application/json")
                .post("/inbound").type(InboundAddress.class)
                .routeId("inboundRouteId")
                .to("direct:processInboundAddress");

        // Define the processing route
        from("direct:processInboundAddress")
                .log("Received request with body: ${body}")
                .unmarshal().json(JsonLibrary.Jackson, InboundAddress.class)
                .process(new AddressProcessor())
                .log(LoggingLevel.INFO, "processed body ${body}")
                .convertBodyTo(String.class)
                .to("file:data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n");

    }
}
