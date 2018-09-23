package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

public class EnvironmentConstansts {
	private static String PROP_FILE_NAME = "application.properties";
	private static Properties prop =  new Properties();
	static {
		initializeProperties();
	}
	
	// Variables to initialize
	public static final String CHARSET = prop.getProperty("charset", "UTF-8");
	public static final String[] INSIGNIFICANT_WORDS = prop.getProperty("insignificantWords", "").split(",");
	public static final String[] NEGETIVE_WORDS = prop.getProperty("negativeWords", "").split(",");
	public static final String[] EARLY_WORDS = prop.getProperty("earlyWords", "").split(",");
	public static final String[] LATE_WORDS = prop.getProperty("lateWords", "").split(",");

	
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
	
	private static void initializeProperties(){
		String fileName = PROP_FILE_NAME;
		InputStream fis = null;
		try {
		    fis = new FileInputStream(fileName);
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    prop.load(isr);
		} catch (IOException e) {
			System.err.println("Could not read propeties file");
		}
	}
}
