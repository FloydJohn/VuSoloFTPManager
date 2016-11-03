package it.floydjohn.vusolo.gui.panels;

import it.floydjohn.vusolo.gui.components.ScanButton;
import it.floydjohn.vusolo.gui.frames.MainFrame;
import it.floydjohn.vusolo.net.FTPManager;
import it.floydjohn.vusolo.settings.Setting;
import it.floydjohn.vusolo.utils.NetScanner;
import it.floydjohn.vusolo.utils.ObservableInterface;
import it.floydjohn.vusolo.utils.ObserverInterface;

import javax.swing.*;
import java.awt.*;

public class ConnectionPanel extends JPanel implements ObserverInterface {

    private JTextField ipField = new JTextField(10);
    private JTextField userField = new JTextField(10);
    private JTextField pwdField = new JPasswordField(10);
    private JButton connectButton = new JButton("Connect");
    private JButton disconnectButton = new JButton("Disconnect");
    private JButton scanButton = new ScanButton();

    public ConnectionPanel(MainFrame mainFrame) {
        super(new FlowLayout());
        NetScanner.addObserver(this);
        ipField.setText(Setting.FTP_IP.v());
        userField.setText(Setting.FTP_USR.v());
        pwdField.setText(Setting.FTP_PWD.v());

        this.add(new JLabel("IP"));
        this.add(ipField);
        this.add(new JLabel("User"));
        this.add(userField);
        this.add(new JLabel("Password"));
        this.add(pwdField);
        this.add(connectButton);
        this.add(disconnectButton);
        this.add(scanButton);

        setBorder(BorderFactory.createTitledBorder("FTP Connection"));

        setConnectingEnabled(true);

        connectButton.addActionListener(actionEvent -> {
            try {

                Setting.FTP_IP.setValue(ipField.getText());
                Setting.FTP_USR.setValue(userField.getText());
                Setting.FTP_PWD.setValue(pwdField.getText());

                FTPManager.getInstance().connect();
                this.setConnectingEnabled(false);
                mainFrame.getActionPanel().setActionsEnabled(false);
                InfoPanel.getInstance().update("Connected to host!", false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        disconnectButton.addActionListener(actionEvent -> {
            FTPManager.getInstance().disconnect();
            this.setConnectingEnabled(true);
            mainFrame.getActionPanel().setActionsEnabled(true);
            InfoPanel.getInstance().update("Disconnected from host!", false);
        });
    }

    private void setConnectingEnabled(boolean enabled) {
        connectButton.setEnabled(enabled);
        scanButton.setEnabled(enabled);
        ipField.setEnabled(enabled);
        userField.setEnabled(enabled);
        pwdField.setEnabled(enabled);
        disconnectButton.setEnabled(!enabled);
    }

    @Override
    public void update(ObservableInterface observable, Object o) {
        //NetScanner found the IP!
        if (o != null && observable.getClass().equals(NetScanner.class))
            ipField.setText(o.toString());
    }
}
