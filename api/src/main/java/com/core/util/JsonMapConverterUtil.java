package com.core.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.BasicDBObject;

public class JsonMapConverterUtil {

    /**
     * 
     * Convert a json string to a JAVA Map Object
     * 
     * @param json
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String json) throws JsonParseException, JsonMappingException, IOException {
        return new ObjectMapper().readValue(json, LinkedHashMap.class);
    }

    /**
     * Convert JAVA Map Object to JSON, the Object can be anything: string, int,
     * map etc
     * 
     * @param map
     * @return
     */
    public static String toJson(Map<String, Object> map) {
        BasicDBObject doc = new BasicDBObject(map);
        return doc.toString();
    }

    /**
     * Convert a MAP list to JSON string
     * 
     * @param key
     * @param mapList
     * @return
     */
    public static String toJson(String key, List<Map<String, Object>> mapList) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, mapList);

        return JsonMapConverterUtil.toJson(map);
    }

}
