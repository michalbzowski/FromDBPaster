package pl.bzowski.smspaster;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Properties;

/**
 * @author mbzowski
 */
public class SmsPaster2 extends Application {

	public static void main(final String... args) {
		System.out.println("SmsPaster2");
		final PropertiesReader propertiesReader = new PropertiesReader();
		System.out.println(propertiesReader.getAppVersion());
		final Properties properties = propertiesReader.read();
		final DatabaseConnector dbc = new DatabaseConnector(propertiesReader);
		final Runner runner = new Runner(properties, dbc);
		runner.run();
		Application.launch(args);
	}

	@Override
	public void start(final Stage primaryStage) {
		//No UI - only shortcuts
	}

}
