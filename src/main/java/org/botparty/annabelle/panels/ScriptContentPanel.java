package org.botparty.annabelle.panels;

import org.botparty.annabelle.Controller;
import org.botparty.annabelle.Data;
import org.botparty.annabelle.controller.ScriptContentController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ScriptContentPanel extends JPanel {

    private JButton button;
    private JList<String> list;

    public ScriptContentPanel() {
        super(new BorderLayout());
        button = new JButton("Send");

        ScriptContentController scriptContentController = Controller.getInstance().getScriptContentController();

        button.addActionListener(scriptContentController);
        this.add(button, BorderLayout.NORTH);

        list = new JList<>();
        list.setModel(Data.getInstance().getScriptContentModel());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.getSelectionModel().addListSelectionListener(scriptContentController);

        JScrollPane scrollPane = new JScrollPane(list);

        this.add(scrollPane, BorderLayout.CENTER);

        list.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0), "send");
        list.getActionMap().put("send", scriptContentController.sendAction() );
        list.addMouseListener(
                new MouseAdapter() {
                    public void mouseClicked(MouseEvent mouseEvent) {
                        if (mouseEvent.getClickCount() == 2) {
                            int index = list.locationToIndex(mouseEvent.getPoint());
                            if (index >= 0) {
                                Object o = list.getModel().getElementAt(index);
                                System.out.println("Double-clicked on: " + o.toString());
                                scriptContentController.send();
                            }
                        }
                    }
                }
        );
    }

    public JButton getButton() {
        return button;
    }

    public JList<String> getList() { return list; }
}
