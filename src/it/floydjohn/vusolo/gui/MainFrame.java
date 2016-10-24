package it.floydjohn.vusolo.gui;

import it.floydjohn.vusolo.net.FTPManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.prefs.Preferences;

/**
 * Created by alessandro on 10/16/16.
 */
public class MainFrame extends JFrame{
    private final String CCAM_PATH = "/var/etc/ccam.config";
    private JPanel panel;
    private JTextArea uploadTArea = new JTextArea(20,40);
    private JButton readButton = new JButton("Read");
    private JButton writeButton = new JButton("Write");
    private JButton webViewButton = new JButton("WebView");
    private JButton fileZillaButton = new JButton("FileZilla");
    private JButton connectButton = new JButton("Connect");
    private JTextField ipField = new JTextField(10);
    private JTextField userField = new JTextField(10);
    private JTextField pwField = new JPasswordField(10);

    public MainFrame() {

        super();
        setFTPEnabled(false);
        panel = new JPanel(new GridBagLayout());

        loadPreferences();

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 10, 5, 10);
        c.fill = 1;
        c.gridx=0;
        c.gridy=1;
        c.weightx=1;
        c.weighty=1;
        panel.add(new JLabel("IP"), c);
        c.gridx=1;
        panel.add(ipField, c);
        c.gridx=2;
        panel.add(new JLabel("User"), c);
        c.gridx=3;
        panel.add(userField, c);
        c.gridx=4;
        panel.add(new JLabel("Password"), c);
        c.gridx=5;
        panel.add(pwField, c);
        c.gridx=6;
        panel.add(connectButton, c);
        c.gridx=0;
        c.gridy=2;
        c.gridwidth=6;
        c.gridheight=4;
        c.weightx = 1;
        panel.add(new JScrollPane(uploadTArea), c);

        c.gridx=6;
        c.gridwidth=1;
        c.gridheight=1;
        panel.add(readButton, c);
        c.gridy = 3;
        panel.add(writeButton, c);
        c.gridy = 4;
        panel.add(webViewButton, c);
        c.gridy = 5;
        panel.add(fileZillaButton, c);


        this.setContentPane(panel);
        setResizable(true);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                FTPManager.getInstance().disconnect();
            }
        });

        readButton.addActionListener(actionEvent -> {
            try {
                uploadTArea.setText(FTPManager.getInstance().receiveFile(CCAM_PATH));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "FTP Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        writeButton.addActionListener(actionEvent -> {
            try {
                FTPManager.getInstance().sendFile(CCAM_PATH, uploadTArea.getText());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "FTP Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        webViewButton.addActionListener(actionEvent -> {
            if (Desktop.isDesktopSupported()) {
                Desktop dt = Desktop.getDesktop();
                if (dt.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        dt.browse(new URI("http://" + ipField.getText() + ":80"));
                    } catch (IOException | URISyntaxException e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "WebView Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        fileZillaButton.addActionListener(actionEvent -> {
            try {
                Runtime.getRuntime().exec("filezilla");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "FileZilla Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        connectButton.addActionListener(actionEvent -> {
            storePreferences();
            FTPManager.getInstance().connect(ipField.getText(), userField.getText(), pwField.getText());
        });
    }

    private void loadPreferences() {
        Preferences prefs = Preferences.userNodeForPackage(it.floydjohn.vusolo.gui.MainFrame.class);
        ipField.setText(prefs.get("ip", ""));
        userField.setText(prefs.get("user", ""));
        pwField.setText(prefs.get("pwd", ""));
    }

    private void storePreferences() {
        Preferences prefs = Preferences.userNodeForPackage(it.floydjohn.vusolo.gui.MainFrame.class);
        prefs.put("ip", ipField.getText());
        prefs.put("user", userField.getText());
        prefs.put("pwd", pwField.getText());
    }

    public void setFTPEnabled(boolean enabled) {
        uploadTArea.setEnabled(enabled);
        readButton.setEnabled(enabled);
        writeButton.setEnabled(enabled);
        webViewButton.setEnabled(enabled);
    }
}
