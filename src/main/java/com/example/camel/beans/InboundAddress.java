package com.example.camel.beans;

import lombok.Data;

@Data
public class InboundAddress {
    private String name;
    private String houseNumber;
    private String city;
    private String state;
    private String zip;
}

