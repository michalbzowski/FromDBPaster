package pl.bzowski.smspaster;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author mbzowski
 */
public class NotificationAbs {

    public void notifyAction(String message) {
        if (SystemTray.isSupported()) {
            try {
                displayTray(message);
            } catch (AWTException ex) {
                Logger.getLogger(NotificationAbs.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(NotificationAbs.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.err.println("System tray not supported!");
        }
    }

    public void displayTray(String message) throws AWTException, java.net.MalformedURLException {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = new ImageIcon(this.getClass().getResource("/ikona.ico")).getImage();
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        trayIcon.setImageAutoSize(true);
        TrayIcon[] trayIcons = tray.getTrayIcons();
        for (TrayIcon tc : trayIcons) {
            tray.remove(tc);
        }
        tray.add(trayIcon);
        trayIcon.displayMessage("SmsPaster", message, MessageType.INFO);
    }

}
