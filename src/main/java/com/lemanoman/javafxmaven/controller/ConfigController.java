package com.lemanoman.javafxmaven.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;

public class ConfigController extends DefaultController {

    public void gotoRaiz(ActionEvent event) {
        starter.changeStage("sample");
    }

    public void chooseFile(MouseEvent event){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Resource File");

        File out = directoryChooser.showDialog(starter.getPrimaryStage());
        if(out!=null){
            TextField textField = (TextField) getElementById("fileselector");
            textField.setText(out.getAbsolutePath());
        }




    }
}
