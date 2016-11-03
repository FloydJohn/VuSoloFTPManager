package it.floydjohn.vusolo.gui.panels;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    private static final InfoPanel instance = new InfoPanel();
    private static final Color defaultColor = UIManager.getColor("Label.foreground");
    private JLabel infoLabel = new JLabel("  ");

    private InfoPanel() {
        super();
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(infoLabel);
    }

    public static InfoPanel getInstance() {
        return instance;
    }

    public void update(String message, boolean isError) {
        infoLabel.setForeground(isError ? Color.RED : defaultColor);
        infoLabel.setText(message);
    }
}
