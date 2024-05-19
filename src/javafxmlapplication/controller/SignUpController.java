/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ICATFOR
 */
public class SignUpController implements Initializable {
    
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;
    @FXML
    private Button goBackButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private Text usernameMessageText;
    @FXML
    private TextField passwordField;
    @FXML
    private Button viewPasswordButton;
    @FXML
    private Text sixCharLengthText;
    @FXML
    private Text alphanumCharOnlyText;
    @FXML
    private Button createAccountButton;
    @FXML
    private ImageView profilePicture;
    @FXML
    private Button selectImageButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initSignUp(Stage stage){
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
    }
    
            

    @FXML
    private void onGobackButtonPressed(ActionEvent event) {
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle); 
    }

    @FXML
    private void onViewPasswordButtonPressed(ActionEvent event) {
    }

    @FXML
    private void onCreateAccountButtonPressed(ActionEvent event) {
    }

    @FXML
    private void onSelectImageButtonPressed(ActionEvent event) {
    }
    
    
}
