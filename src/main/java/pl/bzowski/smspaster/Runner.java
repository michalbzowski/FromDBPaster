package pl.bzowski.smspaster;

import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;
import java.awt.Robot;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.KeyStroke;

/**
 *
 * @author mbzowski
 */
public class Runner {

    private final Properties properties;
    private DatabaseConnector dbc;

    Runner(Properties properties, DatabaseConnector dbc) {
        this.properties = properties;
        this.dbc = dbc;
    }

    public void run() {
        boolean useSwingEventQueue = false;
        Provider provider = Provider.getCurrentProvider(useSwingEventQueue);
        KeyConverter kc = new KeyConverter();
        HotKeyListener pasteSmsListener = (HotKey hotkey) -> {
            Robot robot = new MyRobot().build();
            String code = getCode();
            System.out.println("Code is: " + code);
            char[] split = code.toCharArray();
            for (char s : split) {
                System.out.print("Splitet char: " + s);
                int key = kc.typeNumPad(String.valueOf(s));
                System.out.println(" = " + key);
                robot.keyPress(key);
                robot.delay(75);
            }

        };

        HotKeyListener switchDatabaseListener = (HotKey hotkey) -> {
            PropertiesReader pr = new PropertiesReader();
            pr.switchDatabase();
            System.out.println(pr.getConnectionAdress());
            new NotificationAbs().notifyAction(pr.getConnectionAdress());
        };

        HotKeyListener switchUserId = (HotKey hotkey) -> {
            PropertiesReader pr = new PropertiesReader();
            pr.switchUser();
            System.out.println(pr.getUserAlias());
            new NotificationAbs().notifyAction(String.valueOf(pr.getUserAlias()));
        };
        provider.register(KeyStroke.getKeyStroke(properties.getProperty("SHORTCUT")), pasteSmsListener);
        provider.register(KeyStroke.getKeyStroke(properties.getProperty("SWITCH_DATABASE")), switchDatabaseListener);
        provider.register(KeyStroke.getKeyStroke(properties.getProperty("SWITCH_USER")), switchUserId);

    }

    private String getCode() {
        try {
            dbc.connect();
            String code = dbc.getCode();
            dbc.disconnect();
            return code;
        } catch (SQLException ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

}
