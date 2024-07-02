package com.example.camel.processor;

import com.example.camel.beans.Address;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Slf4j
public class InboundAddressProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Address data = exchange.getIn().getBody(Address.class);
        log.info("legacyFileTransfer received: {}", data.toString());
    }
}
