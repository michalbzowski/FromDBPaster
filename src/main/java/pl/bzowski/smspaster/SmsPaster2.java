package pl.bzowski.smspaster;

import java.util.Properties;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author mbzowski
 */
public class SmsPaster2 extends Application {

    public static void main(String... args) {
        System.out.println("SmsPaster2");
        PropertiesReader propertiesReader = new PropertiesReader();
        System.out.println(propertiesReader.getAppVersion());
        Properties properties = propertiesReader.read();
        DatabaseConnector dbc = new DatabaseConnector(propertiesReader);
        Runner runner = new Runner(properties, dbc);
        runner.run();
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
