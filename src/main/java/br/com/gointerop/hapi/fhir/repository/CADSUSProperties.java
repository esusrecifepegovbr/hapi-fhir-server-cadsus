package br.com.gointerop.hapi.fhir.repository;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import ca.uhn.fhir.context.ConfigurationException;

public class CADSUSProperties {
	static final String PERSISTENCE_PROPERTIES = "cadsus.properties";
	
	static final String ENVIRONMENT = "environment";
	
	static final String URL = getProperty(ENVIRONMENT)+".url";
	static final String USERNAME = getProperty(ENVIRONMENT)+".username";
	static final String PASSWORD = getProperty(ENVIRONMENT)+".password";
	
	private static Properties ourProperties;

	private static String getProperty(String propertyName) {
		String env = "HAPI_" + propertyName.toUpperCase(Locale.US);
		env = env.replace(".", "_");
		env = env.replace("-", "_");

		String propertyValue = System.getenv(env);
		if (propertyValue != null) {
			return propertyValue;
		}

		Properties properties = CADSUSProperties.getProperties();
		if (properties != null) {
			propertyValue = properties.getProperty(propertyName);
		}

		return propertyValue;
	}

	private static Properties getProperties() {
		if (ourProperties == null) {
			Properties properties = loadProperties();
			CADSUSProperties.ourProperties = properties;
		}

		return ourProperties;
	}

	@NotNull
	private static Properties loadProperties() {
		// Load the configurable properties file
		Properties properties;
		try (InputStream in = CADSUSProperties.class.getClassLoader().getResourceAsStream(PERSISTENCE_PROPERTIES)) {
			properties = new Properties();
			properties.load(in);
		} catch (Exception e) {
			throw new ConfigurationException("Could not load HAPI properties", e);
		}

		Properties overrideProps = loadOverrideProperties();
		if (overrideProps != null) {
			properties.putAll(overrideProps);
		}
		properties.putAll(System.getenv().entrySet().stream()
				.filter(e -> e.getValue() != null && properties.containsKey(e.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
		return properties;
	}

	private static Properties loadOverrideProperties() {
		String confFile = System.getProperty(PERSISTENCE_PROPERTIES);
		if (confFile != null) {
			try {
				Properties props = new Properties();
				props.load(new FileInputStream(confFile));
				return props;
			} catch (Exception e) {
				throw new ConfigurationException("Could not load HAPI properties file: " + confFile, e);
			}
		}

		return null;
	}

	private static String getProperty(String propertyName, String defaultValue) {
		String value = getProperty(propertyName);

		if (value != null && value.length() > 0) {
			return value;
		}

		return defaultValue;
	}

	private static Integer getIntegerProperty(String propertyName, Integer defaultValue) {
	    String value = CADSUSProperties.getProperty(propertyName);

	    if (value == null || value.length() == 0) {
	      return defaultValue;
	    }

	    return Integer.parseInt(value);
	  }

	public static String getURL() {
		return CADSUSProperties.getProperty(URL, "https://servicoshm.saude.gov.br/cadsus/PDQSupplier");
	}
	
	public static String getUSERNAME() {
		return CADSUSProperties.getProperty(USERNAME, "CADSUS.CNS.PDQ.PUBLICO");
	}
	
	public static String getPASSWORD() {
		return CADSUSProperties.getProperty(PASSWORD, "kUXNmiiii#RDdlOELdoe00966");
	}
}
