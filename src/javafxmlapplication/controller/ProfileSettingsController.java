/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Acount;
import model.User;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author laijie
 */
public class ProfileSettingsController implements Initializable {
    
    private Acount account;
    private User user;
    
    private String password;
    
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;
	
    @FXML
    private TextField nametextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField mailTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            account = Acount.getInstance();
            user = account.getLoggedUser();
        } catch (AcountDAOException e) {
            System.err.println(e);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
    
    public void initProfileSettingsPage(Stage stage){
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
		
        nametextField.setText(user.getName());
        surnameTextField.setText(user.getSurname());
        mailTextField.setText(user.getEmail());
    }
    
}