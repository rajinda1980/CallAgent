package com.codechallenge.callapp.controller;

import com.codechallenge.callapp.configuration.AppConfig;
import com.codechallenge.callapp.dto.Response;
import com.codechallenge.callapp.dto.TransferTypeRequest;
import com.codechallenge.callapp.service.CallManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/agent/")
@Slf4j
public class TransferTypeController {

    @Autowired
    private CallManager callManager;

    @Autowired
    private AppConfig appConfig;


    @GetMapping(value = "location_and_telephone/{transfer_type}")
    public ResponseEntity<Object> getLocationAndTelephoneNo(@PathVariable String transfer_type) {
        try {
            if (appConfig.getTransferTypeNames().contains(transfer_type.strip())) {
                TransferTypeRequest request = new TransferTypeRequest(transfer_type.strip());
                Response response = callManager.getLocationAndTelephoneNo(request);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Invalid transfer type");

        } catch (Exception e) {
            log.error("Call cannot be accepted. Error : {}", ExceptionUtils.getStackTrace(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred while processing the request");
        }
    }

    @GetMapping(value = "update_config/{location_name}")
    public ResponseEntity<Object> updateConfiguration(@PathVariable String location_name) {
        try {
            boolean result = callManager.updateConfig(location_name);
            if (result) {
                return ResponseEntity.status(HttpStatus.OK).body("Location name is successfully updated");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Invalid location name");
            }

        } catch (Exception e) {
            log.error("Location name cannot be updated. Error : {}", ExceptionUtils.getStackTrace(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred while processing the request");
        }
    }

    @GetMapping(value = "download/call_details")
    public ResponseEntity<Object> getCallDetails() {
        try {
            byte[] file = callManager.getCallDetails("PDF");
            HttpHeaders headers = new HttpHeaders();
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE));
            headers.set("Content-Disposition", "inline; filename=" + "Call_Details.pdf");
            return new ResponseEntity(file, headers, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Cannot generate file. Error : {}", ExceptionUtils.getStackTrace(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred while processing the request");
        }
    }

    @GetMapping(value = "download/config")
    public ResponseEntity<Object> getConfigurations() {
        try {
            byte[] file = callManager.getConfigs("PDF");
            HttpHeaders headers = new HttpHeaders();
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE));
            headers.set("Content-Disposition", "inline; filename=" + "Config.pdf");
            return new ResponseEntity(file, headers, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Cannot generate file. Error : {}", ExceptionUtils.getStackTrace(e));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred while processing the request");
        }
    }
}
