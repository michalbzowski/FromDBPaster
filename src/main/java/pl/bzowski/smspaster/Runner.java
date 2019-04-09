/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bzowski.smspaster;

import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mbzowski
 */
class Runner {

	private final Properties properties;
	private final DatabaseConnector dbc;

	Runner(final Properties properties, final DatabaseConnector dbc) {
		this.properties = properties;
		this.dbc = dbc;
	}

	void run() {
		final boolean useSwingEventQueue = false;
		final Provider provider = Provider.getCurrentProvider(useSwingEventQueue);
		final KeyConverter kc = new KeyConverter();
		final HotKeyListener pasteSmsListener = (HotKey hotkey) -> {
			final Robot robot = new MyRobot().build();
			final String code = getCode();
			System.out.println("Code is: " + code);
			final char[] split = code.toCharArray();
			for (final char s : split) {
				System.out.print("Splitet char: " + s);
				final int key = kc.typeNumPad(String.valueOf(s));
				System.out.println(" = " + key);
				robot.keyPress(key);
				robot.delay(75);
			}

		};

		final HotKeyListener switchDatabaseListener = (HotKey hotkey) -> {
			final PropertiesReader pr = new PropertiesReader();
			pr.switchDatabase();
			System.out.println(pr.getConnectionAdress());
			new NotificationAbs().notifyAction(pr.getConnectionAdress());
		};

		final HotKeyListener switchUserId = (HotKey hotkey) -> {
			final PropertiesReader pr = new PropertiesReader();
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
			final String code = dbc.getCode();
			dbc.disconnect();
			return code;
		} catch (final SQLException ex) {
			Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

}
