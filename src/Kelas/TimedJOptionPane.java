/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kelas;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author rizan
 */
public class TimedJOptionPane {

    public void showTimedMessage(String message, String title, int messageType, int timeout) {
        JOptionPane pane = new JOptionPane(message, messageType, JOptionPane.DEFAULT_OPTION);
        JDialog dialog = pane.createDialog(title);

        Timer timer = new Timer(timeout, e -> dialog.dispose());
        timer.setRepeats(false);
        timer.start();

        dialog.setVisible(true);
    }

    public static void showTimedMessageStatic(String message, String title, int messageType, int timeout) {
        TimedJOptionPane timedPane = new TimedJOptionPane();
        timedPane.showTimedMessage(message, title, messageType, timeout);
    }

}
