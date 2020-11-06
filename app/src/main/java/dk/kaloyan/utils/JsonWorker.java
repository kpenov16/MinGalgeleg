package dk.kaloyan.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class JsonWorker<T> {
    public Set<String> toStringSet(List<T> toParse){
        Set<String> jsonObjects = new LinkedHashSet<>();
        for(T o : toParse){
            String jsonObj = "default";
            try {
                jsonObj = new ObjectMapper().writeValueAsString(o);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                jsonObj = "JsonProcessingException::Object::" + o.toString();
            }finally {
                jsonObjects.add(jsonObj);
            }
        }
        return jsonObjects;
    }

    public List<T> toList(Set<String> set, Class<T> c) {
        List<T> objects = new ArrayList<>();
        for (String jsonObj : set){
            try {
                objects.add(new ObjectMapper().readValue(jsonObj, c));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }
}
