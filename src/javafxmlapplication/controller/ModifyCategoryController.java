/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;

/**
 * FXML Controller class
 *
 * @author ICATFOR
 */
public class ModifyCategoryController implements Initializable {

    @FXML
    private TextField nameText;
    @FXML
    private TextField descriptionText;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Button acceptButton;
    @FXML
    private Button cancelButton;
    
    private Acount account;

    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;
    
    private boolean newC = false;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try{
            account = Acount.getInstance();
        } catch (AcountDAOException e) {
            System.err.println(e);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
        
        acceptButton.disableProperty().bind(nameText.textProperty().isEmpty());
        
    }  
    
    public void initFields(String name, String description){
        if (name != null && description != null){
            nameText.setText(name);
            String s [] = description.split("/");
            if (s.length > 1) descriptionText.setText(s[1]);
            else descriptionText.setPromptText("Write here a description for the category");
        }else{
            if (name == null){
                nameText.setPromptText("Write here a name for the category");
            }
            if (description == null){
                descriptionText.setPromptText("Write here a description for the category");
            }
        }
    }
    
    public void initModifyCategoriesPage(Stage stage){
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
    }
    
    @FXML
    private void acceptAction(ActionEvent event) {
        try {
            account.registerCategory(nameText.getText(), colorPicker.getValue().toString() + "/" + descriptionText.getText());
        } catch (AcountDAOException ex) {
            System.err.println(ex);
        }
        nameText.clear(); descriptionText.clear();
        acceptButton.getScene().getWindow().hide();
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        nameText.clear(); descriptionText.clear();
        cancelButton.getScene().getWindow().hide();
    }
    
}
