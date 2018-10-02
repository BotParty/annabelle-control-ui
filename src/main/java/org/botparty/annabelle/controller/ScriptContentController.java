package org.botparty.annabelle.controller;

import org.botparty.annabelle.Controller;
import org.botparty.annabelle.Data;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class ScriptContentController implements ListSelectionListener, ActionListener {

    public Action sendAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                send();
            }
        };
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){

            ListSelectionModel listSelectionModel = (ListSelectionModel) e.getSource();


            int firstIndex = listSelectionModel.getMinSelectionIndex();
            System.out.println("Index: " + firstIndex);

            if(firstIndex >= 0) {
                String currentLine = Data.getInstance().getScriptContentModel().getElementAt(firstIndex);
                System.out.println("Current Line - " + currentLine);
                Data.getInstance().setScriptContentText(currentLine);
            } else {
                Data.getInstance().setScriptContentText(null);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        send();
    }

    public void send() {
        String scriptContextText = Data.getInstance().getScriptContentText();
        if(scriptContextText != null) {
            Controller.getInstance().send(scriptContextText);
        }
    }
}
