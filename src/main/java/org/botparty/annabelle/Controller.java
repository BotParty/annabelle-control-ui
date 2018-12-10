package org.botparty.annabelle;

import org.botparty.annabelle.controller.*;
import org.botparty.annabelle.domain.CommunicationData;

public class Controller {

    private static Controller instance;

    public static Controller getInstance() {
        if( instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    public FavoritesController getFavoritesController() {
        return favoritesController;
    }

    private FavoritesController favoritesController;
    private SendController puppetController;
    ScriptListController scriptListController;

    private ScriptContentController scriptContentController;
    private AddTextController addTextController;

    private Controller() {
        favoritesController = new FavoritesController();
        puppetController = new SendController();
        scriptListController = new ScriptListController();
        scriptContentController = new ScriptContentController();
        addTextController = new AddTextController();
    }

    public void send(String text) {

        final CommunicationData data = new CommunicationData("remote","face",text);

        System.out.println(text);
        ChatClient.getInstance().send(data);
        Data.getInstance().historyModel.addElement(text);
    }

    public SendController getPuppetController() {
        return puppetController;
    }

    public AddTextController getAddTextController() {
        return addTextController;
    }

    public ScriptContentController getScriptContentController() {
        return scriptContentController;
    }

}
