package it.floydjohn.vusolo.gui.dialogs;

import it.floydjohn.vusolo.utils.Settings;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {

    private JTextField filPath = new JTextField(20);
    private JTextField ftpPort = new JTextField(20);
    private JTextField webPort = new JTextField(20);

    public SettingsDialog(JFrame parent) {
        super(parent, "Settings", true);
        this.setLayout(new GridBagLayout());

        filPath.setText(Settings.getInstance().get(Settings.FTP_FIL, "/var/etc/CCam.cfg"));
        ftpPort.setText(Settings.getInstance().get(Settings.FTP_PRT, "21"));
        webPort.setText(Settings.getInstance().get(Settings.WEB_PRT, "80"));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = 1;
        c.gridwidth = 2;
        c.weightx = 1;

        this.add(new JLabel("CCam config file"), c);
        c.gridy = 1;
        this.add(filPath, c);
        c.gridy = 2;
        this.add(new JLabel("FTP Port"), c);
        c.gridy = 3;
        this.add(ftpPort, c);
        c.gridy = 4;
        this.add(new JLabel("WebServer Port"), c);
        c.gridy = 5;
        this.add(webPort, c);

        c.gridwidth = 1;
        c.gridy = 6;
        JButton ok = new JButton("Ok");
        this.add(ok, c);
        c.gridx = 1;
        JButton cancel = new JButton("Cancel");
        this.add(cancel, c);

        pack();

        ok.addActionListener(actionEvent -> {
            Settings.getInstance().set(Settings.FTP_FIL, filPath.getText());
            Settings.getInstance().set(Settings.FTP_PRT, ftpPort.getText());
            Settings.getInstance().set(Settings.WEB_PRT, webPort.getText());
            dispose();
        });

        cancel.addActionListener(actionEvent -> dispose());
    }

}
