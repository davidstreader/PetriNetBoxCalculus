package com.changenode.plugin;

import com.changenode.Log;
import com.changenode.PetriNet;
import com.changenode.Plugin;
import com.changenode.ShortNet;
import com.google.gson.Gson;

import javafx.geometry.Insets;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;

import java.util.TreeMap;

public class FileDrop implements Plugin {

    public void setupFileDropTarget(TextArea textArea, Log log) {
        textArea.setOnDragOver(event -> {
            if (event.getGestureSource() != textArea && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();

        });

        textArea.setOnDragEntered(event -> textArea.setBackground(
                new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY))));

        textArea.setOnDragExited(event -> textArea.setBackground(
                new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))));

        textArea.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                for (File file : db.getFiles()) {                                   
                 PetriNet pn = PetriNet.file2PetriNet(file, log).get() ;  
                 System.out.println(pn.myString()   );     
                }
                success = true;
            }
            /* let the source know whether the information was successfully transferred and used */
            event.setDropCompleted(success);
            event.consume();
        });
    }
    public String setShortNet(){ //String n, int[] ls, ArrayList<String> v
        String ns = "nameShort";
        int[] ii = new int[2];
        ii[1] = 2;
        TreeMap<String,String> ar = new TreeMap<String,String>(); ar.put("x", "1+2");ar.put("y", "x+ 7");
        ShortNet sn = new ShortNet(ns, ii, ar);
        Gson g = new Gson();
        String gs = g.toJson(sn);
        
        return gs;
    }
    
    @Override
    public void setup(Stage stage, TextArea textArea, ToolBar toolBar, Log log, MenuBar menuBar) {
        log.log("Dragg JSON formatted Petri Nets to this Window");
        setupFileDropTarget(textArea, log);
    }
}
