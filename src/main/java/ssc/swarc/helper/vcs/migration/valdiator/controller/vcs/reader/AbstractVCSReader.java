package ssc.swarc.helper.vcs.migration.valdiator.controller.vcs.reader;

import java.util.Properties;
import java.util.logging.Logger;

import ssc.swarc.helper.vcs.migration.valdiator.controller.util.PropertyLoader;
import ssc.swarc.helper.vcs.migration.valdiator.model.VCSTreeModel;

/**
 * Abstract class for  VCS Readers
 * @author Benjamin Honke
 *
 */
public abstract class AbstractVCSReader {
	static Logger loggerVCS = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	//The name of the properties files
	private static String resourceName = "app.properties"; 
	protected Properties properties = null;
	
	// Attributes requested from the properties file for svn credentials
	protected String urlProp = "";
	protected String userProp = "";
	protected String pwdProp = "";

	// Attributes received from the properties file for svn credentials
	protected String url = "";
	protected String user = "";
	protected char[] pwd;
	
	//The model read from VCS
	protected VCSTreeModel readedInfoTreeModel;

	/**
	 * Default Constructor for any VCS Reader
	 */
	protected AbstractVCSReader(){
		properties = PropertyLoader.getProperties(resourceName);
		
	}
	
	/**
	 * Method to initialize the properties to be loaded for credentials
	 */
	protected abstract void loadProperties();
	
	/**
	 * Method to initialize a reader for later usage
	 */
	protected abstract void initReader();
	
	/**
	 * Method to control and to retrieve relevant information from a VCS
	 */
	public abstract void startReading();
	
	/**
	 * Method to shutdown a reader 
	 */
	protected abstract void shutdownReader();	
	
	/**
	 * return the information from the VCS in form of a model
	 * @return
	 */
	public VCSTreeModel getModel() {
		return readedInfoTreeModel;
	}
}
