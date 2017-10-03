package pl.bzowski.smspaster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mbzowski
 */
public class PropertiesReader {

    public Properties properties;

    Properties read() {
        Properties mainProperties = new Properties();
        try {
            InputStream file = getClass().getResourceAsStream("/smspaster.properties");
            mainProperties.load(file);
            file.close();
            this.properties = mainProperties;
            return mainProperties;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
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
            String versionString = null;
            //to load application's properties, we use this class
            Properties mainProperties = new Properties();

            //load the file handle for main.properties
            InputStream file = getClass().getResourceAsStream("/smspaster.properties");
            //load all the properties from this file
            mainProperties.load(file);
            //we have loaded the properties, so close the file handle
            file.close();
            //retrieve the property we are intrested, the app.version
            versionString = mainProperties.getProperty("app.version");
            return versionString;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ERROR";
    }

    String getUserAlias() {
        read();
        String property = properties.getProperty("USR_ALIASES");
        String[] split = property.split("\\|");
        String actualUser = properties.getProperty("ACTUAL_USER_ID");
        return split[Integer.valueOf(actualUser)];
    }

    String getConnectionAdress() {
        read();
        String property = properties.getProperty("DB_URLS");
        String[] split = property.split("\\|");
        Integer actualDatabase = Integer.valueOf(getActualDatabase());
        return split[actualDatabase];
    }

    String getDatabaseUser() {
        read();
        String property = properties.getProperty("DB_USERS");
        String[] split = property.split("\\|");
        Integer actualDatabase = Integer.valueOf(getActualDatabase());
        return split[actualDatabase];
    }

    String getDatabaseUserPassword() {
        read();
        String property = properties.getProperty("DB_PASSWORDS");
        String[] split = property.split("\\|");
        Integer actualDatabase = Integer.valueOf(getActualDatabase());
        return split[actualDatabase];
    }

    void switchDatabase() {
        read();
        String urls = properties.getProperty("DB_URLS");
        String[] splitedUrls = urls.split("\\|");
        Integer actualDatabase = Integer.valueOf(properties.getProperty("ACTUAL_DATABASE"));
        actualDatabase++;
        if (actualDatabase >= splitedUrls.length) {
            actualDatabase = 0;
        }
        properties.setProperty("ACTUAL_DATABASE", String.valueOf(actualDatabase));

        URL fileResource = getClass().getResource("/smspaster.properties");
        File file;
        try {
            file = new File(fileResource.toURI());
            OutputStream output = new FileOutputStream(file);
            properties.store(output, "");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(PropertiesReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String getActualDatabase() {
        read();
        return properties.getProperty("ACTUAL_DATABASE");
    }

    void switchUser() {
        read();
        String urls = properties.getProperty("USR_ALIASES");
        String[] splitedUrls = urls.split("\\|");
        Integer actualUserId = Integer.valueOf(properties.getProperty("ACTUAL_USER_ID"));
        actualUserId++;
        if (actualUserId >= splitedUrls.length) {
            actualUserId = 0;
        }
        properties.setProperty("ACTUAL_USER_ID", String.valueOf(actualUserId));

        URL fileResource = getClass().getResource("/smspaster.properties");
        File file;
        try {
            file = new File(fileResource.toURI());
            OutputStream output = new FileOutputStream(file);
            properties.store(output, "");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(PropertiesReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
