package com.lemanoman.javafxmaven.interfaces;

import com.lemanoman.javafxmaven.Starter;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;

public interface FXController {
    public void initialize(Parent parent, Starter starter);
    public void onLoad();
}
