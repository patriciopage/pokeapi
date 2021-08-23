package com.example.PokeApi.exception;

public class ElementNotFoundException extends Exception {

    private String value;
    private String resourceType;

    public ElementNotFoundException(String value, String resourceType) {
        super();
        this.value = value;
        this.resourceType = resourceType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}
