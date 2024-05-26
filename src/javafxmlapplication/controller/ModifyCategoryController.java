/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
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
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Pane pane;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try{
            account = Acount.getInstance();
        } catch (AcountDAOException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Exception Dialog");
            error.setHeaderText(null);
            error.setContentText("An error has occurred while loading your account");
            
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionText = sw.toString();
            
            Label label = new Label("Exception:");
            
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea,Priority.ALWAYS);
            GridPane.setHgrow(textArea,Priority.ALWAYS);
            
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0 ,1);
            
            error.getDialogPane().setExpandableContent(expContent);
            error.showAndWait();
        } catch (IOException ioe) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Exception Dialog");
            error.setHeaderText(null);
            error.setContentText("An error has occurred while loading your account");
            
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ioe.printStackTrace(pw);
            String exceptionText = sw.toString();
            
            Label label = new Label("Exception:");
            
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea,Priority.ALWAYS);
            GridPane.setHgrow(textArea,Priority.ALWAYS);
            
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0 ,1);
            
            error.getDialogPane().setExpandableContent(expContent);
            error.showAndWait();
        }
        
        pane.setPrefHeight(250);
        pane.setPrefWidth(600);
        pane.setMinHeight(250);
        pane.setMinWidth(600);
        
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
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Exception Dialog");
            error.setHeaderText(null);
            error.setContentText("An error has occurred adding the category");
            
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String exceptionText = sw.toString();
            
            Label label = new Label("Exception:");
            
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea,Priority.ALWAYS);
            GridPane.setHgrow(textArea,Priority.ALWAYS);
            
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0 ,1);
            
            error.getDialogPane().setExpandableContent(expContent);
            error.showAndWait();
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
