package com.coreantech.data.model;

import lombok.Data;

@Data
public class GenericRequest {
    private GenericRequestContext requestContext;
    private String body;
}

