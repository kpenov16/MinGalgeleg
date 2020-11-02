package dk.kaloyan.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import dk.kaloyan.mingalgeleg.ViewablePlayer;

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
}
