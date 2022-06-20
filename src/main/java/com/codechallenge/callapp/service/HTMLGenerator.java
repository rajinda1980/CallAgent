package com.codechallenge.callapp.service;

import org.springframework.stereotype.Service;

@Service
public class HTMLGenerator implements FileGenerator {

    @Override
    public String getType() {
        return "HTML";
    }

    @Override
    public byte[] generateFile() throws Exception {
        return new byte[0];
    }

    @Override
    public byte[] getConfigs() throws Exception {
        return new byte[0];
    }
}
