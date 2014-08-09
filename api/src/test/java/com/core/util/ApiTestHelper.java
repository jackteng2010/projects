package com.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class ApiTestHelper {

    private static final Log logger = LogFactory.getLog(ApiTestHelper.class);

    public static Map<String, Object> loadJsonMapTestData(Class<?> clz, String resource, String key) {

        InputStream inputStream = clz.getResourceAsStream(resource + ".properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {

            logger.error("Load test data failed!", e);
        }

        try {
            return new ObjectMapper().readValue(properties.getProperty(key), HashMap.class);
        } catch (JsonParseException e) {
            logger.error("Load test data failed!", e);
        } catch (JsonMappingException e) {
            logger.error("Load test data failed!", e);
        } catch (IOException e) {
            logger.error("Load test data failed!", e);
        }

        return null;

    }
   
}
