package com.core.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConfigManager {

	private static final Log logger = LogFactory.getLog(ConfigManager.class);

	private static final String[] files = {"/config.properties","/default.properties","/validators/applicationResources.properties"};
	
    private static Properties properties = new Properties();
    
    static{
		for(String file : files){
			try {
				properties.load(ConfigManager.class.getResourceAsStream(file));
			} catch (IOException e) {
				logger.debug("Error to load file "+ file);
				e.printStackTrace();
			}
		}
    }
    public static String getDbName() {
        return properties.getProperty("dbName");
    }

    public static String getValue(String key){
    	return properties.getProperty(key);
    }
    
}
