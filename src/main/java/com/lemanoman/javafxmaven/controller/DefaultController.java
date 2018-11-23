package com.lemanoman.javafxmaven.controller;

import com.lemanoman.javafxmaven.Starter;
import com.lemanoman.javafxmaven.interfaces.FXController;
import javafx.scene.Node;
import javafx.scene.Parent;

public class DefaultController implements FXController {
    protected Parent parent;
    protected Starter starter;

    @Override
    public void initialize(Parent parent, Starter starter) {
        this.parent = parent;
        this.starter = starter;
        onLoad();
    }

    public Node getElementById(String id) {
        if(id.contains("#")){
            return parent.lookup(id);
        }else{
            return parent.lookup("#"+id);
        }

    }

    public void onLoad() {}


}
