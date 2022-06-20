package com.codechallenge.callapp.dto;

public class TransferTypeRequest {

    private Location location;
    private String request;

    public TransferTypeRequest(String request) {
        this.request = request;
    }

    public Location getLocation() {return location; }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) { this.request = request; }
}
