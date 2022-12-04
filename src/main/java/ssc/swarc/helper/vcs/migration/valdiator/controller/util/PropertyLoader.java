package ssc.swarc.helper.vcs.migration.valdiator.controller.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * The PropertyLoader loads values from properties file 
 * @author Benjamin Honke
 *
 */
public class PropertyLoader {
	static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/**
	 * Constructor
	 */
	private PropertyLoader() {
		
	}
	/**
	 * Method to load property values from properties file located in resources
	 * directory
	 * @param resourceName the name of the Property  File to be loaded
	 * @return the properties object
	 */
	public static Properties getProperties(String resourceName) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties props = new Properties();
		InputStream resourceStream;
		try {
			resourceStream = loader.getResourceAsStream(resourceName);
			props.load(resourceStream);
		} catch (IOException e) {
			logger.severe(e.getMessage());
		}
		return props;
	}

}
