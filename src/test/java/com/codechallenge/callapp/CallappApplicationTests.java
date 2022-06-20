package com.codechallenge.callapp;

import com.codechallenge.callapp.dto.Response;
import com.codechallenge.callapp.dto.TransferTypeRequest;
import com.codechallenge.callapp.service.CallManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CallappApplicationTests {

	@Autowired
	private CallManager callManager;

	@Test
	void contextLoads() {
	}

	@Test
	public void testGetLocationAndTelephoneNo() {
		try {
			TransferTypeRequest transferRequest = new TransferTypeRequest("lost and stolen");
			Response response = callManager.getLocationAndTelephoneNo(transferRequest);
			Assertions.assertTrue(null != response.getTelephoneNo() && null != response.getLocationName());

		} catch (Exception e) {}

	}
}
