package com.codechallenge.callapp.service;

public interface FileGenerator {
    String getType();
    byte[] generateFile() throws Exception;
    byte[] getConfigs() throws Exception;
}
