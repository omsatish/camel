package com.example.camel.processor;

import com.example.camel.beans.InboundAddress;
import com.example.camel.beans.OutboundAddress;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Slf4j
public class AddressProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        InboundAddress data = exchange.getIn().getBody(InboundAddress.class);
        exchange.getIn().setBody(new OutboundAddress(data.getCity(), data.getState(), data.getZip()));
    }
}
