package com.changenode;

import com.changenode.plugin.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Sphere;
//import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

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
   

    public void log(String s) {
        textArea.appendText(s);
        textArea.appendText(System.lineSeparator());
        statusLabel.setText(s);
    }

    @Override
    public void start(Stage stage) {

        TabPane tabPane = new TabPane();

        // Tab tab2 = new Tab("Cars"  , new Label("Show all cars available"));
      //  tabPane.getTabs().add(tab2);
       
       // VBox vBox = new VBox(tabPane);
        //Scene scene = new Scene(vBox);
        
        BorderPane borderPane = new BorderPane();

        VBox topElements = new VBox();

        // MenuBar menuBar = new MenuBar();
        // topElements.getChildren().add(menuBar);

        ToolBar toolbar = new ToolBar();
         MenuBar menuBar = new MenuBar();
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
        Pane  pane  = new Pane();
        pane.getChildren().add(new Label("Hello Pane"));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(pane);
        Tab tab1 = new Tab("View Petri", scrollPane);      
        tabPane.getTabs().add(tab1);
        HBox hb = new HBox();
        scrollPane.setContent(hb);
        Sphere sphere = new Sphere(40);
        hb.getChildren().add(sphere);
        sphere.setTranslateX(200); 
        sphere.setTranslateY(150); 
        stage.setTitle("I Found The Men Sir");
        Tab tab3 = new Tab("Drop json" , borderPane);
        tabPane.getTabs().add(tab3);
        Scene scene = new Scene(tabPane, 800, 600);

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
