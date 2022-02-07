package com.coreantech.data.model;

import lombok.Data;

@Data
public class GenericResponse<T> {

    private boolean status;
    private T data;

    public GenericResponse() {};

    public GenericResponse(boolean status,T data) {
        this.status = status;
        this.data = data;
    };

}
