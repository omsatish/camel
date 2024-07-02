package com.example.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spring.boot.actuate.endpoint.CamelRouteControllerEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CamelSpringBootTest
@UseAdviceWith
public class LegacyFileTransferTest {
    @Autowired
    CamelContext camelContext;

    @EndpointInject("mock:result")
    MockEndpoint mockEndpoint;

    @Autowired
    ProducerTemplate template;


    //@Test
    public void testLegacyFileTransfer() throws Exception {
        String expectedBody = "I am Satish";
        mockEndpoint.expectedBodiesReceived(expectedBody);
        mockEndpoint.expectedMessageCount(1);

        AdviceWith.adviceWith(camelContext, "legacyFileTransferId",
                routBuilder -> routBuilder.weaveByToUri("file:*").replace().to(mockEndpoint));

        camelContext.start();
        mockEndpoint.assertIsSatisfied();

    }

    @Test
    public void testLegacyFileTransferMockedInput() throws Exception {
        String mockedBody = "Satish, 1, Amz, UP, 23221";
        mockEndpoint.expectedBodiesReceived("OutboundAddress(city= Amz, state= UP, zip= 23221)");
        mockEndpoint.expectedMessageCount(1);

        AdviceWith.adviceWith(camelContext, "legacyFileTransferId",
                routBuilder -> {
                    routBuilder.replaceFromWith("direct:mockStart");
                    routBuilder.weaveByToUri("file:*").replace().to(mockEndpoint);
                });

        camelContext.start();
        template.sendBody("direct:mockStart", mockedBody);
        mockEndpoint.assertIsSatisfied();

    }
}
