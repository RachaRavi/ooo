package com.coreantech.data.model;

import lombok.Data;

@Data
public class  GenericRequestedHTTP {
    private String method;
    public String getMethod() { return  this.method; }
}
