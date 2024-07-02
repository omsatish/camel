package com.example.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CamelSpringBootTest
@UseAdviceWith
class CamelApplicationTests {
    @Autowired
    CamelContext camelContext;

    @EndpointInject("mock:result")
    MockEndpoint mockEndpoint;

    @Test
    public void testSimpleTimer() throws Exception {
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedBodiesReceived("Hello World");
        AdviceWith.adviceWith(camelContext, "simpleTimerId",
                routBuilder -> routBuilder.weaveAddLast()
                        .to(mockEndpoint));

        camelContext.start();
        mockEndpoint.assertIsSatisfied();
    }


}
