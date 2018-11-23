package com.lemanoman.javafxmaven.controller;

import com.lemanoman.javafxmaven.Starter;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SampleController extends DefaultController {
    private Label lblData;
    private int position = 0;
    private List<String> paths;

    private void loadImages() {
        paths = new ArrayList<>();

        Map<String, String> env = System.getenv();
        File file = new File("/home/"+env.get("USER")+"/Imagens");
        for (File f : file.listFiles()) {
            if(f.getName().endsWith("png") ||f.getName().endsWith("jpeg")) {
                paths.add(f.getAbsolutePath());
            }
        }

    }

    @Override
    public void onLoad(){
        loadImages();
    }

    public void gotoConfig(ActionEvent event) {
        try {
            Starter starter = this.starter;
            starter.changeStage("configuration");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private void loadImage(int position) {
        ImageView imageView = (ImageView) getElementById("image");
        String path = paths.get(position);

        FileInputStream input = null;
        try {
            input = new FileInputStream(path);
            Image image = new Image(input);
            imageView.setImage(image);
            if (image != null) {
                double w = 0;
                double h = 0;

                double ratioX = imageView.getFitWidth() / image.getWidth();
                double ratioY = imageView.getFitHeight() / image.getHeight();

                double reducCoeff = 0;
                if(ratioX >= ratioY) {
                    reducCoeff = ratioY;
                } else {
                    reducCoeff = ratioX;
                }

                w = image.getWidth() * reducCoeff;
                h = image.getHeight() * reducCoeff;

                imageView.setX((imageView.getFitWidth() - w) / 2);
                imageView.setY((imageView.getFitHeight() - h) / 2);

            }
            imageView.setPreserveRatio(true);
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void nextImage(ActionEvent event) {
        if (position + 1 > paths.size()) {
            position = (paths.size() - 1);
        } else {
            position++;
        }
        loadImage(position);
    }

    public void previousImage(ActionEvent event) {
        if (position - 1 < 0) {
            position = 0;
        } else {
            position--;
        }
        loadImage(position);
    }
}
