package com.codechallenge.callapp.service;

import com.codechallenge.callapp.dto.Response;
import com.codechallenge.callapp.dto.TransferTypeRequest;

public interface CallManager {

    Response getLocationAndTelephoneNo(TransferTypeRequest transferRequest) throws Exception;
    boolean updateConfig(String location_name) throws Exception;
    byte[] getCallDetails(String type) throws Exception;
    byte[] getConfigs(String type) throws Exception;
}
