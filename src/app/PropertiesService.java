package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesService {

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