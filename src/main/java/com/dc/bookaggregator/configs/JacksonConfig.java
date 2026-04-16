package com.dc.bookaggregator.configs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

final class JacksonConfig {
    private JacksonConfig(){ }

    private static class JacksonSingletonHolder {
        private static final ObjectMapper JACKSON_CONFIG_SINGLETON_INSTANCE =
                JsonMapper.builder()
                        .findAndAddModules()
                        .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                        .build();
    }

    public static ObjectMapper getMapper(){
        return  JacksonSingletonHolder.JACKSON_CONFIG_SINGLETON_INSTANCE;
    }

}