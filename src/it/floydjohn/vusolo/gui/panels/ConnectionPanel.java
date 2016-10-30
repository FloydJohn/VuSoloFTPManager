package it.floydjohn.vusolo.gui.panels;

import it.floydjohn.vusolo.gui.frames.MainFrame;
import it.floydjohn.vusolo.net.FTPManager;
import it.floydjohn.vusolo.utils.Settings;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alessandro on 10/25/16.
 */
public class ConnectionPanel extends JPanel {

    private JTextField ipField = new JTextField(10);
    private JTextField userField = new JTextField(10);
    private JTextField pwdField = new JPasswordField(10);
    private JButton connectButton = new JButton("Connect");
    private JButton disconnectButton = new JButton("Disconnect");

    public ConnectionPanel(MainFrame mainFrame) {
        super(new FlowLayout());

        ipField.setText(Settings.getInstance().get(Settings.FTP_IP, ""));
        userField.setText(Settings.getInstance().get(Settings.FTP_USR, ""));
        pwdField.setText(Settings.getInstance().get(Settings.FTP_PWD, ""));

        this.add(new JLabel("IP"));
        this.add(ipField);
        this.add(new JLabel("User"));
        this.add(userField);
        this.add(new JLabel("Password"));
        this.add(pwdField);
        this.add(connectButton);
        this.add(disconnectButton);

        setBorder(BorderFactory.createTitledBorder("FTP Connection"));

        setConnectingEnabled(true);

        connectButton.addActionListener(actionEvent -> {
            try {

                Settings.getInstance().set(Settings.FTP_IP, ipField.getText());
                Settings.getInstance().set(Settings.FTP_USR, userField.getText());
                Settings.getInstance().set(Settings.FTP_PWD, pwdField.getText());

                FTPManager.getInstance().connect();
                this.setConnectingEnabled(false);
                mainFrame.getActionPanel().setActionsEnabled(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        disconnectButton.addActionListener(actionEvent -> {
            FTPManager.getInstance().disconnect();
            this.setConnectingEnabled(true);
            mainFrame.getActionPanel().setActionsEnabled(true);
        });
    }

    private void setConnectingEnabled(boolean enabled) {
        connectButton.setEnabled(enabled);
        ipField.setEnabled(enabled);
        userField.setEnabled(enabled);
        pwdField.setEnabled(enabled);
        disconnectButton.setEnabled(!enabled);
    }

}
