/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bzowski.smspaster;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mbzowski
 */
class PropertiesReader {

	private Properties properties;

	Properties read() {
		final Properties mainProperties = new Properties();
		try {
			final InputStream file = getClass().getResourceAsStream("/smspaster.properties");
			mainProperties.load(file);
			file.close();
			this.properties = mainProperties;
			return mainProperties;
		} catch (final IOException ex) {
			Logger.getLogger(PropertiesReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		return mainProperties;
	}

	/**
	 * Gets the app.version property value from the ./main.properties file of
	 * the base folder
	 *
	 * @return app.version string
	 */
	public String getAppVersion() {
		try {
			final String versionString;
			//to load application's properties, we use this class
			final Properties mainProperties = new Properties();

			//load the file handle for main.properties
			final InputStream file = getClass().getResourceAsStream("/smspaster.properties");
			//load all the properties from this file
			mainProperties.load(file);
			//we have loaded the properties, so close the file handle
			file.close();
			//retrieve the property we are intrested, the app.version
			versionString = mainProperties.getProperty("app.version");
			return versionString;
		} catch (final IOException ex) {
			Logger.getLogger(PropertiesReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "ERROR";
	}

	String getUserAlias() {
		read();
		final String property = properties.getProperty("USR_ALIASES");
		final String[] split = property.split("\\|");
		final String actualUser = properties.getProperty("ACTUAL_USER_ID");
		return split[Integer.valueOf(actualUser)];
	}

	String getConnectionAdress() {
		read();
		final String property = properties.getProperty("DB_URLS");
		final String[] split = property.split("\\|");
		final Integer actualDatabase = Integer.valueOf(getActualDatabase());
		return split[actualDatabase];
	}

	String getDatabaseUser() {
		read();
		final String property = properties.getProperty("DB_USERS");
		final String[] split = property.split("\\|");
		final Integer actualDatabase = Integer.valueOf(getActualDatabase());
		return split[actualDatabase];
	}

	String getDatabaseUserPassword() {
		read();
		final String property = properties.getProperty("DB_PASSWORDS");
		final String[] split = property.split("\\|");
		final Integer actualDatabase = Integer.valueOf(getActualDatabase());
		return split[actualDatabase];
	}

	void switchDatabase() {
		read();
		final String urls = properties.getProperty("DB_URLS");
		final String[] splitedUrls = urls.split("\\|");
		Integer actualDatabase = Integer.valueOf(properties.getProperty("ACTUAL_DATABASE"));
		actualDatabase++;
		if (actualDatabase >= splitedUrls.length) {
			actualDatabase = 0;
		}
		properties.setProperty("ACTUAL_DATABASE", String.valueOf(actualDatabase));

		final URL fileResource = getClass().getResource("/smspaster.properties");
		storeOutputToProperties(fileResource);
	}

	private void storeOutputToProperties(final URL fileResource) {
		final File file;
		try {
			file = new File(fileResource.toURI());
			final OutputStream output = new FileOutputStream(file);
			properties.store(output, "");
		} catch (final URISyntaxException | IOException ex) {
			Logger.getLogger(PropertiesReader.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private String getActualDatabase() {
		read();
		return properties.getProperty("ACTUAL_DATABASE");
	}

	void switchUser() {
		read();
		final String urls = properties.getProperty("USR_ALIASES");
		final String[] splitedUrls = urls.split("\\|");
		Integer actualUserId = Integer.valueOf(properties.getProperty("ACTUAL_USER_ID"));
		actualUserId++;
		if (actualUserId >= splitedUrls.length) {
			actualUserId = 0;
		}
		properties.setProperty("ACTUAL_USER_ID", String.valueOf(actualUserId));

		final URL fileResource = getClass().getResource("/smspaster.properties");
		storeOutputToProperties(fileResource);
	}

	String getSelectStatement() {
		read();
		return properties.getProperty("SELECT_STATEMENT");
	}

	String getSmsCodeColumnAlias() {
		read();
		return properties.getProperty("SMS_CODE_COLUMN_ALIAS");
	}
}
