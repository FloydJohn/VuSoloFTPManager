package it.floydjohn.vusolo.gui.frames;

import it.floydjohn.vusolo.gui.panels.ActionPanel;
import it.floydjohn.vusolo.gui.panels.ConnectionPanel;
import it.floydjohn.vusolo.net.FTPManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * Created by alessandro on 10/16/16.
 */
public class MainFrame extends JFrame{


    private ActionPanel actionPanel = new ActionPanel(this);
    private ConnectionPanel connectionPanel = new ConnectionPanel(this);


    public MainFrame() {

        super();

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 10, 5, 10);
        c.fill = 1;
        c.gridx=0;
        c.gridy = 0;
        c.weightx=1;
        c.weighty=1;
        panel.add(connectionPanel, c);
        c.gridy = 1;
        panel.add(actionPanel, c);

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
    }


    public ConnectionPanel getConnectionPanel() {
        return connectionPanel;
    }

    public ActionPanel getActionPanel() {
        return actionPanel;
    }
}
