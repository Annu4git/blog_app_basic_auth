package com.anurag.blogapp.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;

    private String fieldName;

    private long value;

    public ResourceNotFoundException(String resourceName, String fieldName, long value) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, value));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.value = value;
    }
}
