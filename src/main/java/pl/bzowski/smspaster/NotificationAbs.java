/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bzowski.smspaster;

import javax.swing.*;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mbzowski
 */
class NotificationAbs {

	void notifyAction(final String message) {
		if (SystemTray.isSupported()) {
			try {
				displayTray(message);
			} catch (final AWTException ex) {
				Logger.getLogger(NotificationAbs.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			System.err.println("System tray not supported!");
		}
	}

	private void displayTray(final String message) throws AWTException {
		final SystemTray tray = SystemTray.getSystemTray();
		final Image image = new ImageIcon(this.getClass().getResource("/ikona.ico")).getImage();
		final TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
		trayIcon.setImageAutoSize(true);
		final TrayIcon[] trayIcons = tray.getTrayIcons();
		for (final TrayIcon tc : trayIcons) {
			tray.remove(tc);
		}
		tray.add(trayIcon);
		trayIcon.displayMessage("SmsPaster", message, MessageType.INFO);
	}

}
