package it.floydjohn.vusolo.gui.dialogs;

import it.floydjohn.vusolo.settings.Setting;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SettingsDialog extends JDialog {

    public SettingsDialog(JFrame parent) {
        super(parent, "Settings", true);
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        HashMap<Setting.Type, JPanel> panels = new HashMap<>();
        HashMap<Setting, JTextField> textFields = new HashMap<>();

        for (Setting.Type type : Setting.Type.values()) {
            if (type == Setting.Type.DO_NOT_SHOW) continue;
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(0, 2));
            panel.setBorder(BorderFactory.createTitledBorder(type.name()));
            panels.put(type, panel);
        }

        for (Setting setting : Setting.values()) {
            if (setting.getType() == Setting.Type.DO_NOT_SHOW) continue;
            JPanel panel = panels.get(setting.getType());
            JLabel label = new JLabel(setting.getDesc());
            JTextField field = new JTextField(setting.getDeft(), 20);
            panel.add(label);
            panel.add(field);
            textFields.put(setting, field);
        }

        for (JPanel panel : panels.values()) {
            this.add(panel);
            this.add(Box.createRigidArea(new Dimension(5, 0)));
        }

        JButton ok = new JButton("Ok"), cancel = new JButton("Cancel");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(ok);
        buttonPanel.add(cancel);

        this.add(buttonPanel);


        pack();

        ok.addActionListener(actionEvent -> {
            for (Map.Entry entry : textFields.entrySet())
                ((Setting) entry.getKey()).setValue(((JTextField) entry.getValue()).getText());
            dispose();
        });

        cancel.addActionListener(actionEvent -> dispose());
    }

}
