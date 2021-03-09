package app.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Utility class for retrieving properties.
 */
public class PropertiesService
{

    /**
     * Retrieves properties from specified property file.
     *
     * @param fileName Properties file we wish to get properties for.
     * @return Properties for given properties file.
     * @throws Exception Java.io.FileNotFoundException.
     */
    public java.util.Properties GetProperties(String fileName) throws  Exception
    {
        String projectPath = new File("").getAbsolutePath();
        String propertiesPath = projectPath.concat("\\src\\res\\" + fileName);
        System.out.println(propertiesPath);
        java.util.Properties projectProperties = new java.util.Properties();
        InputStream stream = new FileInputStream(propertiesPath);
        projectProperties.load(stream);
        return projectProperties;
    }
}