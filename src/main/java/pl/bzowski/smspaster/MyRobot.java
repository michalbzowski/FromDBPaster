/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bzowski.smspaster;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mbzowski
 */
class MyRobot {

	Robot build() {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (final AWTException ex) {
			Logger.getLogger(SmsPaster2.class.getName()).log(Level.SEVERE, null, ex);
		}
		return robot;
	}
}
