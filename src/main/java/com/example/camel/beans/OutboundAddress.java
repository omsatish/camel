package com.example.camel.beans;

import lombok.Data;

@Data
public class OutboundAddress {
    private String city;
    private String state;
    private String zip;

    public OutboundAddress(String city, String state, String zip) {
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
}

