package it.floydjohn.vusolo.gui.panels;

import it.floydjohn.vusolo.gui.MainFrame;
import it.floydjohn.vusolo.gui.dialogs.SettingsDialog;
import it.floydjohn.vusolo.net.FTPManager;
import it.floydjohn.vusolo.utils.Settings;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by alessandro on 10/26/16.
 */
public class ActionPanel extends JPanel {

    private final String defaultFile = "/var/etc/CCam.cfg";

    private JButton readButton = new JButton("Read");
    private JButton writeButton = new JButton("Write");
    private JButton webViewButton = new JButton("WebView");
    private JButton fileZillaButton = new JButton("FileZilla");
    private JButton settingsButton = new JButton("Settings");

    private JTextArea uploadTArea = new JTextArea(20, 40);

    public ActionPanel(MainFrame mainFrame) {
        super();
        setActionsEnabled(true);
        this.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 10, 5, 10);
        c.fill = 1;
        c.weightx = 1;
        c.weighty = 1;

        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 5;
        this.add(new JScrollPane(uploadTArea), c);

        c.gridx = 1;
        c.gridheight = 1;
        this.add(readButton, c);
        c.gridy = 1;
        this.add(writeButton, c);
        c.gridy = 2;
        this.add(webViewButton, c);
        c.gridy = 3;
        this.add(fileZillaButton, c);
        c.gridy = 4;
        this.add(settingsButton, c);


        readButton.addActionListener(actionEvent -> {
            try {
                uploadTArea.setText(FTPManager.getInstance().receiveFile(Settings.getInstance().get(Settings.FTP_FIL, defaultFile)));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "FTP Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        writeButton.addActionListener(actionEvent -> {
            try {
                FTPManager.getInstance().sendFile(Settings.getInstance().get(Settings.FTP_FIL, defaultFile), uploadTArea.getText());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "FTP Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        webViewButton.addActionListener(actionEvent -> {
            if (Desktop.isDesktopSupported()) {
                Desktop dt = Desktop.getDesktop();
                if (dt.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        dt.browse(new URI("http://" + Settings.getInstance().get(Settings.FTP_IP, "") + ":" + Settings.getInstance().get(Settings.WEB_PRT, "80")));
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

        settingsButton.addActionListener(actionEvent -> {
            SettingsDialog settingsDialog = new SettingsDialog(mainFrame);
            settingsDialog.setVisible(true);
        });
    }

    void setActionsEnabled(boolean connectingEnabled) {
        uploadTArea.setEnabled(!connectingEnabled);
        readButton.setEnabled(!connectingEnabled);
        writeButton.setEnabled(!connectingEnabled);
        webViewButton.setEnabled(!connectingEnabled);
    }

}
