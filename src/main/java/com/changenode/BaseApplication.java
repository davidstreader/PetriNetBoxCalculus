package com.changenode;

import com.changenode.plugin.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
//import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
//import java.util.ArrayList;
import java.util.TreeMap;

import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

import static javax.swing.filechooser.FileSystemView.getFileSystemView;

public class BaseApplication extends Application implements Log {

    public static File outputFile;
    /**
     * This is the very simple "registry" for the various demonstration features of this application.
     */
    private final Plugin[] plugins = new Plugin[]{
           new StandardMenus(),
            new HelloWorld(), 
            new FileDrop(),
            new DesktopIntegration(),
             new LogFile(), 
          //   new DarkMode()
            };

    private TextArea textArea;
    private Label statusLabel;

    // public String getJson(Place p) {
    //     Gson g = new Gson();
    //     g.toJson(p);
    //     return "";
    // }

    public static void main(String[] args) {
        /*
         * This little bit of code causes this application to route the debugging output for this application to a
         * log file in your "default" directory.
         * */
        
        try {
            //mkNet();       
            outputFile = File.createTempFile("debug", ".log", getFileSystemView().getDefaultDirectory());
         //   PrintStream output = new PrintStream(new BufferedOutputStream(new FileOutputStream(outputFile)), true);
          //  System.setOut(output);
          //  System.setErr(output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        launch(args);
    }
    public static void testPn() {
        int[] ixs = new int[2];
        ixs[0] = 12;
        Place p1 = new Place("Pl1", ixs);
        String ps = p1.PlacetoJson();
        System.out.println(ps);
    }
    public static PetriNet mkNet() {
        int[] ixs = new int[2];
        ixs[0] = 12;
        Place p1 = new Place("Pl1", ixs);
        Place p2 = new Place("Pl2", ixs);
        Transition t1 = new Transition("Tr1", "ping", TrType.active );
        arcP2T a1 = new arcP2T("arc1",p1.name,"x< 0",t1.name);
        arcT2P a2 = new arcT2P("arc2",t1.name," x = 2",p2.name);
        p1.addarcP2T(a1);
        p2.addarcT2P(a2);
        t1.addarcP2T(a1);
        t1.addarcT2P(a2);
        TreeMap<String,String> eval = new TreeMap<String,String>();
        eval.put("x", "3 +5");
        TreeMap<String,Transition> trs = new TreeMap<String,Transition>(); trs.put(t1.name,t1);
        TreeMap<String,Place> pls = new TreeMap<String,Place>(); pls.put(p1.name,p1);pls.put(p2.name,p2);
        PetriNet pn = new PetriNet("FirstNet",ixs,eval);
        pn.setTransitions(trs);
        pn.setPlaces(pls);
        pn.markPlace(p1);
        pn.addarcP2T(a1); pn.addarcT2P(a2);
        System.out.println(pn.toString()+"\n****");
       


        System.out.println(p1);
        //String ps = p1.PlacetoJson();
        System.out.println("**myString()**");
        System.out.println(pn.myString());
        System.out.println("**JSON**");
        Gson g = new Gson();
        String js =   g.toJson(pn);
        System.out.println(js);
        Place pGot = Place.getJson();
        System.out.println("pGot "+pGot.myString());
        Transition tGot = Transition.getJson();
        System.out.println("tGot "+tGot.myString());
        
        return pn;
    }
   
   

    public void log(String s) {
        textArea.appendText(s);
        textArea.appendText(System.lineSeparator());
        statusLabel.setText(s);
    }

    @Override
    public void start(Stage stage) {

        BorderPane borderPane = new BorderPane();

        VBox topElements = new VBox();

        // MenuBar menuBar = new MenuBar();
        // topElements.getChildren().add(menuBar);

        ToolBar toolbar = new ToolBar(); MenuBar menuBar = new MenuBar();
        topElements.getChildren().addAll(toolbar,menuBar);   
       
        //topElements.getChildren().add(menuBar);add(toolbar);

        textArea = new TextArea();
        textArea.setWrapText(true);

        statusLabel = new Label();
        statusLabel.setPadding(new Insets(5.0f, 5.0f, 5.0f, 5.0f));
        statusLabel.setMaxWidth(Double.MAX_VALUE);

        borderPane.setTop(topElements);
        borderPane.setBottom(statusLabel);
        borderPane.setCenter(textArea);

        Scene scene = new Scene(borderPane, 800, 600);

        stage.setTitle("I Found The Men Sir");
        stage.setScene(scene);

        for (Plugin plugin : plugins) {
            try {
                plugin.setup(stage, textArea, toolbar, this, menuBar);
            } catch (Exception e) {
                System.err.println("Unable to start plugin");
                System.err.println(plugin.getClass().getName());
                e.printStackTrace();
                log("Unable to start plugin");
                log(plugin.getClass().getName());
                log(e.getMessage());
            }
        }

        statusLabel.setText("Ready.");

        stage.show();
        //put window to front to avoid it to be hide behind other.
        stage.setAlwaysOnTop(true);
        stage.requestFocus();
        stage.toFront();
        stage.setAlwaysOnTop(false);
        /* Fils choosing is in StandardMenues.java */
       
    }
}
