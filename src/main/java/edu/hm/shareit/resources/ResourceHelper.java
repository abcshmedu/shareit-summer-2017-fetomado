package edu.hm.shareit.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Helper class for methods needed in all services.
 */
public final class ResourceHelper {

    /**
     * Private constructor for helper classes.
     */
    private ResourceHelper() {
    }

    /**
     * This function converts an Object to JSON and returns the string-representation.
     * @param obj Object to convert
     * @return String representation of the JSON-Object
     */
    public static String toJson(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
