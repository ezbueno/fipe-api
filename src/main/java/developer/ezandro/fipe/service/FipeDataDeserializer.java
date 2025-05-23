package developer.ezandro.fipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UncheckedIOException;

public class FipeDataDeserializer implements IFipeDataDeserializer {
    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T getData(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }
}