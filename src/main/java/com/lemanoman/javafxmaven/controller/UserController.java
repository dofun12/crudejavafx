package com.lemanoman.javafxmaven.controller;

import com.lemanoman.javafxmaven.SQLiteJDBCDriverConnection;
import com.lemanoman.javafxmaven.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserController extends DefaultController {
    private SQLiteJDBCDriverConnection sqLite;

    @FXML
    private TableView<User> tabela;
    @FXML
    private TableColumn idCol;
    @FXML
    private TableColumn nameCol;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtName1;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnSalvar;

    private User selectedUser = null;

    @Override
    public void onLoad() {
        sqLite = new SQLiteJDBCDriverConnection();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        refreshTable();
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                adicionar();
            }
        });

        tabela.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                if (tabela.getSelectionModel().getSelectedItem() != null) {
                    selectedUser = newValue;
                    txtName1.setText(newValue.getName());
                }
            }
        });

        btnDeletar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deletar();
                refreshTable();
            }
        });


        btnSalvar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                update();
                refreshTable();
            }
        });


    }

    public void refreshTable(){
        ObservableList<User> lista = listaDeClientes();
        tabela.setItems(lista);
    }

    private ObservableList<User> listaDeClientes() {
        List<User> userList = new ArrayList<>();
        for (Map<String, Object> map : sqLite.getListUser()) {
            userList.add(new User((Integer) map.get("id"), (String) map.get("name")));
        }

        return FXCollections.observableList(userList);
    }

    public void adicionar() {
        String name = txtName.getText();
        if (name != null && !name.isEmpty()) {
            //User user = new User(null,txtName.getText());
            sqLite.addUser(name);
        }
        tabela.setItems(listaDeClientes());
    }

    public void deletar() {
        if(selectedUser!=null) {
            sqLite.deleteUser(selectedUser.getId());
        }
    }

    public void update() {
        if(selectedUser!=null) {
            sqLite.updateUser(txtName1.getText(),selectedUser.getId());
        }
    }
}
