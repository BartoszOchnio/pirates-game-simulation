package com.example.piratesgames.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public class ConfigIO {

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public static void saveToJson(SimulationConfig config, File file) throws IOException {
        mapper.writeValue(file, config);
    }

    public static SimulationConfig loadFromJson(File file) throws IOException {
        return mapper.readValue(file, SimulationConfig.class);
    }
}