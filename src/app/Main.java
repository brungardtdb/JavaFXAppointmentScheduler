package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

//public  class  Main {
//    public static void main(String[] args) throws Exception
//    {
//        app.PropertiesService propertiesService = new app.PropertiesService();
//        Properties projectProperties = propertiesService.GetProperties();
//        System.out.println(projectProperties.getProperty("cnnstring"));
//        System.out.println(projectProperties.getProperty("port"));
//        System.out.println(projectProperties.getProperty("dbname"));
//        System.out.println(projectProperties.getProperty("username"));
//        System.out.println(projectProperties.getProperty("password"));
//    }
//}
