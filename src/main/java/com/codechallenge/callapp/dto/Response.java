package com.codechallenge.callapp.dto;

public class Response {
    String locationName;
    String telephoneNo;

    public Response(String locationName, String telephoneNo) {
        this.locationName = locationName;
        this.telephoneNo = telephoneNo;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }
}
