package org.botparty.annabelle;

import javax.swing.*;

public class Application {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        //TODO - not make run against localhost


                        AnnabelleFrame.createAndShowGUI();
                    }
                }
        );
    }
}
