/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.bzowski.smspaster;

import java.awt.event.KeyEvent;

/**
 *
 * @author mbzowski
 */
public class KeyConverter   {
    
    int typeNumPad(String digit) {
        switch (digit) {
            case "0":
                return KeyEvent.VK_0;
            case "1":
                return KeyEvent.VK_1;
            case "2":
                return KeyEvent.VK_2;
            case "3":
                return KeyEvent.VK_3;
            case "4":
                return KeyEvent.VK_4;
            case "5":
                return KeyEvent.VK_5;
            case "6":
                return KeyEvent.VK_6;
            case "7":
                return KeyEvent.VK_7;
            case "8":
                return KeyEvent.VK_8;
            case "9":
                return KeyEvent.VK_9;
        }
        return -1;
    }

}
