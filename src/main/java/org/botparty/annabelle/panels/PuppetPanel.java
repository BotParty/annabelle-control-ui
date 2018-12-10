package org.botparty.annabelle.panels;

import org.botparty.annabelle.Controller;
import org.botparty.annabelle.controller.SendController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PuppetPanel extends JPanel implements ActionListener {

    private JTextField puppetTextField;

    private PuppetPanel() {

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        puppetTextField = new JTextField(50);
        final SendController puppetController = Controller.getInstance().getPuppetController();
        puppetTextField.getDocument().addDocumentListener(puppetController);
        puppetTextField.addActionListener(puppetController);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(puppetController);

        JButton clearButton = new JButton("Clear Text");
        clearButton.addActionListener(this);

        JButton addButton = new JButton("Add To Script");
        addButton.addActionListener(Controller.getInstance().getAddTextController());

        GridBagConstraints textFieldContrainsts = new GridBagConstraints();
        textFieldContrainsts.fill = GridBagConstraints.HORIZONTAL;
        textFieldContrainsts.gridx = 0;
        textFieldContrainsts.gridy = 0;
        textFieldContrainsts.gridwidth = 3;
        textFieldContrainsts.gridheight = 1;
        textFieldContrainsts.weightx = 0.5;
        textFieldContrainsts.weighty = 0.5;
        this.add(puppetTextField, textFieldContrainsts);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 3;
        buttonConstraints.gridwidth = 1;
        this.add(sendButton, buttonConstraints);

        GridBagConstraints addButtonConstraints = new GridBagConstraints();
        addButtonConstraints.gridx = 1;
        addButtonConstraints.gridy = 1;
        addButtonConstraints.gridwidth = 1;
        this.add(addButton, addButtonConstraints);

        GridBagConstraints clearButtonConstraints = new GridBagConstraints();
        clearButtonConstraints.gridx = 0;
        clearButtonConstraints.gridy = 1;
        clearButtonConstraints.gridwidth = 1;
        this.add(clearButton, clearButtonConstraints);
    }

    static public JPanel create() {
        return new PuppetPanel();
    }

    // TODO - move this into a text manipulation controller
    @Override
    public void actionPerformed(ActionEvent e) {
        puppetTextField.setText("");
    }
}
