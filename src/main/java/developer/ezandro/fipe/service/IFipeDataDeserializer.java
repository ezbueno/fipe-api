package developer.ezandro.fipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

public interface IFipeDataDeserializer {
    <T> T getData(String json, TypeReference<T> type) throws JsonProcessingException;
}