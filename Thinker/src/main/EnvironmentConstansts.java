package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvironmentConstansts {
	public static String PROJECT_DIR = System.getProperty("user.dir");
	private static String PROP_FILE_NAME = "application.properties";
	
	// Variables to initialize
	public static String CHARSET = "";

	static {
		initializeConstants();
	}
	
	// Initialize google api key separately, 
	// because it cannot be apart of configuration control
	private static String GOOGLE_PROP_FILE_NAME = "google_api_key";
	public static String GOOGLE_API_KEY = getGoogleApiKey();
	
	private static String getGoogleApiKey(){
		Properties prop = new Properties();
		String fileName = GOOGLE_PROP_FILE_NAME;
		InputStream is = null;
		String googleApiKey = "";
		try {
		    is = new FileInputStream(fileName);
		    prop.load(is);
		    
		    googleApiKey = prop.getProperty("google_api_key");
		} catch (IOException e) {
			System.err.println("Could not read google api key file");
		}
		
		return googleApiKey;
	}
	
	private static void initializeConstants(){
		Properties prop = new Properties();
		String fileName = PROP_FILE_NAME;
		InputStream is = null;
		try {
		    is = new FileInputStream(fileName);
		    prop.load(is);
		    
		    CHARSET = prop.getProperty("charset");
		} catch (IOException e) {
			System.err.println("Could not read propeties file");
		}
	}
}
