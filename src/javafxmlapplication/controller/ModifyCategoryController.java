/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

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
    }    

    @FXML
    private void acceptAction(ActionEvent event) {
        nameText.getScene().getWindow().hide();
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        nameText.getScene().getWindow().hide();
    }
    
}