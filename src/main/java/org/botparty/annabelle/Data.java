package org.botparty.annabelle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sun.deploy.util.OrderedHashSet;
import org.botparty.annabelle.domain.Favorites;
import org.botparty.annabelle.domain.Script;
import org.botparty.annabelle.domain.ScriptList;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Data {

    static Data instance = null;

    public static Data getInstance() {
        if(instance == null) {
            instance = new Data();
        }

        return instance;
    }

    ScriptList masterList;
    Map<String, Script> scripts;
    // <FileName, Title>
    Map<String, String> scriptFileMap;
    Favorites favorites;
    String puppetText;
    String scriptContentText;
    Collection<String> emotionList;
    String currentScriptTitle;

    DefaultListModel<String> historyModel;
    DefaultListModel<String> scriptModel;
    DefaultListModel<String> scriptContentModel;

    public DefaultListModel<String> getScriptModel() {
        return scriptModel;
    }

    public DefaultListModel<String> getScriptContentModel() {
        return scriptContentModel;
    }

    public Map<String, Script> getScripts() {
        return scripts;
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public String getPuppetText() {
        return puppetText;
    }

    public void setPuppetText(String puppetText) {
        this.puppetText = puppetText;
    }

    public String getScriptContentText() {
        return scriptContentText;
    }

    public void setScriptContentText(String scriptContentText) {
        this.scriptContentText = scriptContentText;
    }

    public String[] getEmotionList() {
        return emotionList.toArray(new String[0]);
    }
    private Data() {

        scriptModel = new DefaultListModel<>();
        scriptContentModel = new DefaultListModel<>();
        favorites = Favorites.create("Favorites.json");
        historyModel = new DefaultListModel<>();
        emotionList = new HashSet<>();

        loadScriptList("Scripts.json");
        loadEmotionList("Faces.json");
    }

    private void loadScriptFromMasterList(String parentPath) {
        List<String> titleList2 = masterList.getScripts();

        scripts = new HashMap<>();
        scriptFileMap = new HashMap<>();
        for(String title : titleList2) {
            java.nio.file.Path path = java.nio.file.Paths.get(parentPath, title);
            String absolutePath = path.toString();
            final Script script = Script.create(absolutePath);
            scripts.put(script.title, script);
            scriptFileMap.put(title, script.title);
        }

        Set<String> titles = scripts.keySet();
        scriptModel.clear();
        titles.forEach(scriptModel::addElement);

        String currentScriptTitle = (String) titles.toArray()[0];
        Script currentScript = scripts.get(currentScriptTitle);
        scriptContentModel.clear();
        currentScript.lines.forEach(scriptContentModel::addElement);
        this.setCurrentScriptTitle(currentScriptTitle);
    }

    private void loadScriptList(String fileName) {
        masterList = ScriptList.create(fileName);
        loadScriptFromMasterList("");
    }

    void loadScriptList(File fileToOpen) {
        masterList = ScriptList.open(fileToOpen);
        String parentPath = fileToOpen.getParentFile().getAbsolutePath();
        System.out.println("Parent path: " + parentPath);
        loadScriptFromMasterList(parentPath);
    }

    public void loadEmotionList(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            // load in json
            ObjectMapper mapper = new ObjectMapper();
            StringBuilder builder = new StringBuilder();
            String aux = "";
            while ((aux = reader.readLine()) != null) {
                builder.append(aux);
            }
            String text = builder.toString();
            reader.close();
            JsonNode rootNode = mapper.readValue(text, JsonNode.class);
            if(rootNode.get("faces").isArray()) {
                ArrayNode arrayNode = (ArrayNode) rootNode.get("faces");
                emotionList = new OrderedHashSet();
                for (JsonNode node: arrayNode) {
                    emotionList.add(node.asText());
                }
            }
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
       // masterList = ScriptList.create(fileName);
       // emotionList = new String[] {"happy","angry","bedroom","begging","buckteeth","dead","disgust","dizzy","eyeroll","heart","laughter","mischevious","money","neutral","peeved","sad","stars","stoned","surprised","thinking","worry"};
    }

    public void loadEmotionList(File fileToOpen) {
        masterList = ScriptList.open(fileToOpen);
        emotionList = masterList.getScripts();
    }

    public DefaultListModel<String> getHistoryModel() {
        return historyModel;
    }

    public String getCurrentScriptTitle() {
        return currentScriptTitle;
    }

    public void setCurrentScriptTitle(String currentScriptTitle) {
        this.currentScriptTitle = currentScriptTitle;
    }
}
