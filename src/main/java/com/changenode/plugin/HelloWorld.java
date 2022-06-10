package com.changenode.plugin;

import java.io.File;
import java.util.Scanner;

import com.changenode.Log;
import com.changenode.Plugin;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HelloWorld implements Plugin {

    Button button;

    @Override
    public void setup(Stage stage, TextArea textArea, ToolBar toolBar, Log log, MenuBar menuBar) {
        button = new Button();
        button.setText("Get Petri Net");
        button.setOnAction(event -> log.log("Get Petri Net! " + java.util.Calendar.getInstance().getTime()));
        button.setFocusTraversable(false);

        toolBar.getItems().add(button);
        FileChooser fileChooser = new FileChooser();

        Button butt = new Button("Select File");
        butt.setOnAction(e -> {
            try {
            File selectedFile = fileChooser.showOpenDialog(stage);
            Scanner myReader = new Scanner(selectedFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
              }
              myReader.close();
            } catch(Exception ex) {
                System.out.println("File Reder "+ ex);
            }
        });
      
        toolBar.getItems().add(butt);
    }
}
