package ssc.swarc.helper.MigrationValdiator;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

import ssc.swarc.helper.vcs.migration.valdiator.controller.util.PropertyLoader;

/**
 * Unit test for the App.
 */
public class AppTest 
{
    /**
     * Test loading properties from properties file
     */
    @Test
    public void testPropertiyLoading()
    {

    	Properties properties = PropertyLoader.getProperties("app.properties");
    	
    	assertEquals("https://localhost/svn/Test", properties.getProperty("svn_url") );
    }
    

}
