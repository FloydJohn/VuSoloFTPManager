package it.floydjohn.vusolo.gui;

import it.floydjohn.vusolo.net.FTPManager;

import javax.swing.*;

/**
 * Created by alessandro on 10/16/16.
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        }
        catch (Exception ignored) {}

        MainFrame frame = new MainFrame();
        FTPManager.getInstance().setMainFrame(frame);
        frame.setVisible(true);
    }
}
