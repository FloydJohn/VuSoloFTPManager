package it.floydjohn.vusolo.gui.panels;

import it.floydjohn.vusolo.gui.components.CopyPastePopUp;
import it.floydjohn.vusolo.gui.dialogs.SettingsDialog;
import it.floydjohn.vusolo.gui.frames.MainFrame;
import it.floydjohn.vusolo.net.FTPManager;
import it.floydjohn.vusolo.settings.Setting;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ActionPanel extends JPanel {

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
                uploadTArea.setText(FTPManager.getInstance().receiveFile());
                InfoPanel.getInstance().update("File read successfully!", false);
            } catch (IOException e) {
                InfoPanel.getInstance().update(e.getMessage(), true);
            }
        });

        writeButton.addActionListener(actionEvent -> {
            try {
                FTPManager.getInstance().sendFile(uploadTArea.getText());
                InfoPanel.getInstance().update("File written successfully!", false);
            } catch (IOException e) {
                InfoPanel.getInstance().update(e.getMessage(), true);
            }
        });

        webViewButton.addActionListener(actionEvent -> {
            if (Desktop.isDesktopSupported()) {
                Desktop dt = Desktop.getDesktop();
                if (dt.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        dt.browse(new URI("http://" + Setting.FTP_IP.v() + ":" + Setting.WEB_PRT.v()));
                    } catch (IOException | URISyntaxException e) {
                        InfoPanel.getInstance().update(e.getMessage(), true);
                    }
                }
            }
        });

        fileZillaButton.addActionListener(actionEvent -> {
            try {
                Runtime.getRuntime().exec("filezilla");
            } catch (IOException e) {
                InfoPanel.getInstance().update(e.getMessage(), true);
            }
        });

        settingsButton.addActionListener(actionEvent -> {
            SettingsDialog settingsDialog = new SettingsDialog(mainFrame);
            settingsDialog.setVisible(true);
        });

        uploadTArea.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger())
                    doPop(e);
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger())
                    doPop(e);
            }

            private void doPop(MouseEvent e) {

                if (!uploadTArea.isEnabled()) return;

                CopyPastePopUp menu = new CopyPastePopUp();

                Action copyAction = new DefaultEditorKit.CopyAction();
                Action pasteAction = new DefaultEditorKit.PasteAction();

                menu.setCopyAction(copyAction);
                menu.setPasteAction(pasteAction);

                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }

    void setActionsEnabled(boolean connectingEnabled) {
        uploadTArea.setEnabled(!connectingEnabled);
        readButton.setEnabled(!connectingEnabled);
        writeButton.setEnabled(!connectingEnabled);
        webViewButton.setEnabled(!connectingEnabled);
    }

}
