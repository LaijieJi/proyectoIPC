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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
   
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;
    
    private Acount account;
    private User user;
    
    private String name;
    private String surname;
    private String email;
    private String password;
    
    Image emptyAvatar = new Image(getClass().getResource(
            "../styles/resources/Profile_avatar_placeholder_large.png").toString()
                );
    private Image avatar;
	
    @FXML
    private ImageView profilePicture;
    @FXML
    private Text usernameText;
    @FXML
    private Button selectImageButton;
    @FXML
    private Button goBackButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button viewPasswordButton;
    @FXML
    private Text wrongPasswordText;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private Button viewNewPasswordButton;
    @FXML
    private Text sixCharLengthText;
    @FXML
    private Text alphanumCharOnlyText;
    @FXML
    private PasswordField confirmNewPasswordField;
    @FXML
    private Text confirmPasswordMessage;
    @FXML
    private Button saveChangesButton;
    @FXML
    private Text removePicture;

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
        nameField.setText(user.getName());
        surnameField.setText(user.getSurname());
        emailField.setText(user.getEmail());
        if(SignUpController.getIsAvatEmpty()) profilePicture.setImage(emptyAvatar);
        else profilePicture.setImage(user.getImage());
        removePicture.toBack();
        
    }
    
    public void initProfileSettingsPage(Stage stage){
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
    }
    
    @FXML
    private void onGoBackButtonPressed(ActionEvent event) {
        //Need to reload the Main page again to get the shown data updated
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Main.fxml"));
            BorderPane root = loader.load();
            
            MainController mainController = loader.<MainController>getController();
            mainController.initMain(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Main");
        } catch (IOException ioe) {
            System.err.println("Unable to load that page: " + ioe);
        }
    }
    
    @FXML //!!! YET TO SOLVE THE NON-UPDATE IN OTHER WINDOWS
    private void onSaveChangesButtonPressed(ActionEvent event) {
        name = nameField.getText();
        surname = surnameField.getText();
        email = emailField.getText();
        password = newPasswordField.getText();
        avatar = profilePicture.getImage();
        
        
        if(!name.isEmpty()) user.setName(name);
        if(!surname.isEmpty()) user.setSurname(surname);
        if(!email.isEmpty()) user.setEmail(email);
        if(!password.isEmpty()) user.setPassword(password);
        user.setImage(avatar);
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
        SignUpController.setIsAvatEmpty(false);
    }

    
    /***ALL: Profile Picture removal***/
    @FXML
    private void onProfilePictureEntered(MouseEvent event) {
        if(profilePicture.getImage().equals(emptyAvatar)) return;
        else {   
            profilePicture.setOpacity(0.5);
        }
    }
    
    @FXML
    private void onProfilePictureClicked(MouseEvent event){
        if(profilePicture.getImage().equals(emptyAvatar)) return;
        else {   
            profilePicture.setImage(emptyAvatar);
            profilePicture.setOpacity(1);
            SignUpController.setIsAvatEmpty(true);
        }
    }
      
    @FXML
    private void onProfilePictureExited(MouseEvent event) {
        profilePicture.setOpacity(1);
    }
    
}