package com.codechallenge.callapp.service;

import com.codechallenge.callapp.repository.CallLogRepository;
import com.codechallenge.callapp.configuration.AppConfig;
import com.codechallenge.callapp.data.CallLog;
import com.codechallenge.callapp.data.TransactionLocation;
import com.codechallenge.callapp.dto.Location;
import com.codechallenge.callapp.dto.Response;
import com.codechallenge.callapp.dto.TransferTypeRequest;
import com.codechallenge.callapp.repository.TransactionLocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class CallManagerImpl implements CallManager {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private CallLogRepository callLogRepository;

    @Autowired
    private TransactionLocationRepository transactionLocationRepository;

    // Map to store all current request type objects based on the transfer type name
    Map<String, TransferTypeRequest> currentTransferTypeRequests = new HashMap<>();

    @Autowired
    List<FileGenerator> fileGenerators;

    @Override
    public Response getLocationAndTelephoneNo(TransferTypeRequest transferRequest) throws Exception {
        log.info("Received request Transfer_type : {}", transferRequest.getRequest());

        // 1. Variable definitions
        TransferTypeRequest mapRequest = null;
        TransferTypeRequest newRequest = null;
        Location mapLocation = null;
        Location nextLocation = null;

        // 2. Set the transfer type request (current one and new one)
        if (currentTransferTypeRequests.containsKey(transferRequest.getRequest())) {
            mapRequest = currentTransferTypeRequests.get(transferRequest.getRequest());
        }
        newRequest = transferRequest;


        // 3. Find the relevant priority location
        mapLocation = (null != mapRequest ? mapRequest.getLocation() : null);
        List<Location> locationList = appConfig.getLocations().get(newRequest.getRequest());

        if (null == mapRequest) {
            for (int i = 0; i < locationList.size(); i++) {
                if (locationList.get(i).isEnabled()) {
                    nextLocation = locationList.get(i);
                    break;
                }
            }
        } else {
            int end = locationList.size();
            for (int i = 0; i < end; i++) {
                if (locationList.get(i).getName().equals(mapLocation.getName())) {
                    for (int j = i + 1; j < end; j++) {
                        if (locationList.get(j).isEnabled()) {
                            nextLocation = locationList.get(j);
                            break;
                        }
                    }
                    if (null == nextLocation) {
                        for (int j = 0; j < i; j++) {
                            if (locationList.get(j).isEnabled()) {
                                nextLocation = locationList.get(j);
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
        newRequest.setLocation(nextLocation);
        currentTransferTypeRequests.put(newRequest.getRequest(), newRequest);
        Response response = new Response(newRequest.getLocation().getName(), newRequest.getLocation().getNumber());

        // 4. Save request to the database
        saveCallLog(newRequest);

        return response;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    private void saveCallLog(TransferTypeRequest newRequest ) throws Exception {
        TransactionLocation transactionLocation = transactionLocationRepository.findByName(newRequest.getLocation().getName());
        if (null == transactionLocation) {
            transactionLocation =
                    new TransactionLocation(
                            newRequest.getLocation().getName(),
                            newRequest.getLocation().getNumber(),
                            newRequest.getLocation().getWeight(),
                            newRequest.getLocation().isEnabled());
            transactionLocationRepository.save(transactionLocation);
        }

        CallLog callLog = new CallLog(newRequest.getRequest(), LocalDateTime.now(), transactionLocation);
        callLogRepository.save(callLog);
    }

    @Override
    public boolean updateConfig(String location_name) throws Exception {
        return appConfig.updateConfig(location_name);
    }

    @Override
    public byte[] getCallDetails(String type) throws Exception {
        Optional<FileGenerator> generator = fileGenerators.stream().filter(f -> f.getType().equals(type)).findFirst();
        byte[] file = generator.get().generateFile();
        return file;
    }

    @Override
    public byte[] getConfigs(String type) throws Exception {
        Optional<FileGenerator> generator = fileGenerators.stream().filter(f -> f.getType().equals(type)).findFirst();
        byte[] file = generator.get().getConfigs();
        return file;
    }
}
