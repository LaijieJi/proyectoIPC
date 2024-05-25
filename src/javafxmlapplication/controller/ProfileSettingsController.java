/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
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
    
    private Image avatar = null;
	
    @FXML
    private TextField nametextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField mailTextField;
    @FXML
    private ImageView profilePicture;
    @FXML
    private Text usernameText;
    @FXML
    private Button selectImageButton;
    @FXML
    private Button goBackButton;

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
        
        user = account.getLoggedUser();
        
        usernameText.setText(user.getNickName());
        nametextField.setText(user.getName());
        surnameTextField.setText(user.getSurname());
        mailTextField.setText(user.getEmail());
        profilePicture.setImage(user.getImage());
        
    }
    
    public void initProfileSettingsPage(Stage stage){
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
    }
    
    @FXML
    private void onGoBackButtonPressed(ActionEvent event) {
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle); 
    }

    /***ALL: Profile Picture selection***/    
    @FXML
    private void onSelectImageButtonPressed(ActionEvent event) {
        //ALL: implement profile picture selection
        FileChooser fileChooser = new FileChooser();
		
        //Selection extension filters (only images supported by ImageView natively)
        fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("All Image Files", "*.jpg", "*.jpeg", "*.png", "*.bmp"),
        new FileChooser.ExtensionFilter("JPG, JPEG", "*.jpg", "*.jpeg"),
        new FileChooser.ExtensionFilter("PNG", "*.png"),
        new FileChooser.ExtensionFilter("BMP", "*.bmp")
        );
		
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        
        if(selectedFile == null) {
            return;
        }
        
        avatar = new Image(selectedFile.toURI().toString());
        //The avatar gets cut to fit the ImageView squared shape
        profilePicture.setImage(SignUpController.cropSquaredCenter(avatar)); 
    }
    
}