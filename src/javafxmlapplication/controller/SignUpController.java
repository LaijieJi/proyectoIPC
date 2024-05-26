/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author ICATFOR
 */
public class SignUpController implements Initializable {
    
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;
    
    private String password;
    private String passwordConfirmation;
    private String username;
    
    private Acount account;
    //toString() needed to convert the URL object
    Image emptyAvatar = new Image(getClass().getResource(
            "../styles/resources/Profile_avatar_placeholder_large.png").toString()
                );
    private Image avatar;
    static boolean avEmpty = true;
         
    // variable used for checking all fields are correct
    boolean everythingOK = false;
    
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
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text nameLabel;
    @FXML
    private Text surnameLabel;
    @FXML
    private Text mailLabel;
    @FXML
    private Text usernameLabel;
    @FXML
    private Text passwordLabel;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Text confirmPasswordMessage;
    @FXML
    private Text usernameWarningText;
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
        
        /***Profile Picture related init config***/
        removePicture.toBack();
        profilePicture.setImage(emptyAvatar);
        
        /***viewPasswordButton config***/
        viewPasswordButton.setDisable(true);
        viewPasswordButton.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            password = passwordField.getText();
            //After saving the password in a var, it's removed so as to show it as a prompt 
            passwordField.clear();
            passwordField.setPromptText(password);
        });
        viewPasswordButton.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            passwordField.setText(password);
            passwordField.setPromptText("");
        });
        
        /***Password-related & Username warnings init config***/
        sixCharLengthText.setText("→ More than 6 characters long");
        sixCharLengthText.setFill(Color.BLACK);
        alphanumCharOnlyText.setText("→ Alphanumeric characters only");
        alphanumCharOnlyText.setFill(Color.BLACK);
        confirmPasswordField.setDisable(true);
        confirmPasswordMessage.setVisible(false);
        usernameWarningText.setVisible(false);
    }    
    
    public void initSignUpPage(Stage stage){
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
    private void onCreateAccountButtonPressed(ActionEvent event) {
        everythingOK = true;
        
        //1st: check Name
        if(nameField.getText().isEmpty()) {
            nameLabel.setFill(Color.INDIANRED);
            everythingOK &= false;
        } else {
            nameLabel.setFill(Color.BLACK);
        }
        
        //2nd: check Surname
        if(surnameField.getText().isEmpty()) {
            surnameLabel.setFill(Color.INDIANRED);
            everythingOK &= false; 
        } else {
            surnameLabel.setFill(Color.BLACK);
        }
        
        //3rd: check Email
        if(emailField.getText().isEmpty()) {
            mailLabel.setFill(Color.INDIANRED);
            everythingOK &= false;
        } else {
            mailLabel.setFill(Color.BLACK);
        }
        
        //4th: check Username
        if(usernameField.getText().isEmpty()) {
            usernameLabel.setFill(Color.INDIANRED);
            everythingOK &= false;
        } else if(account.existsLogin(usernameField.getText())) {
                everythingOK &= false;
                usernameLabel.setFill(Color.INDIANRED);
                usernameWarningText.setText("Username not available, try another one");
                usernameWarningText.setVisible(true);
        } else {
            usernameLabel.setFill(Color.BLACK);
        }
        
        //5th: check Password & Password Confirmation
        password = passwordField.getText();
        passwordConfirmation = confirmPasswordField.getText();
        
        if(password.isEmpty()) {
            everythingOK &= false;
            passwordLabel.setFill(Color.INDIANRED);
        } else {
            passwordLabel.setFill(Color.BLACK);
        }
        
        if(passwordConfirmation.isEmpty()) {
            everythingOK &= false;
            passwordLabel.setFill(Color.INDIANRED);
        } else if(!passwordConfirmation.equals(password)) {
                everythingOK &= false;
                confirmPasswordMessage.setVisible(true);
                passwordLabel.setFill(Color.INDIANRED);
        } else {
            passwordLabel.setFill(Color.BLACK);
        }
        
        //6th: register user
        if(everythingOK) {
            System.out.println("everything ok");
            boolean registered = false;
            //In order for Picture removal in Profile Settings to go smoothly
            if(isAvatEmpty()) avEmpty = true;
            
            try{
                registered = account.registerUser(nameField.getText(), surnameField.getText() , emailField.getText() ,
                        usernameField.getText() , password, profilePicture.getImage(), LocalDate.now());
            } catch (Exception e) {
                System.err.println(e);
            }

            if(registered) {
                /*
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/LogIn.fxml"));
                    //Stage stage = new Stage();
                    BorderPane root = loader.load();

                    LogInController logInController = loader.<LogInController>getController();
                    logInController.initLogin(primaryStage);

                    Scene scene = new Scene(root);
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("Log in");
                } catch (Exception e) {
                    System.err.println(e);
                }*/
                primaryStage.setScene(primaryScene);
                primaryStage.setTitle(primaryTitle); 
            }
        }
    }
    
    
    /***ALL: Text input listeners (Password & Username's also check requisites)***/
    @FXML
    private void onNameWritten(KeyEvent event) {
        nameLabel.setFill(Color.BLACK);
    }

    @FXML
    private void onSurnameWritten(KeyEvent event) {
        surnameLabel.setFill(Color.BLACK);
    }

    @FXML
    private void onMailWritten(KeyEvent event) {
        mailLabel.setFill(Color.BLACK);
    }
    
    @FXML
    private void onUsernameWritten(KeyEvent event) {
        username = usernameField.getText();
        usernameLabel.setFill(Color.BLACK);
        //Char requisite
        if(event.getCharacter().equals(" ") || username.contains(" ")) {
            usernameWarningText.setText("Cannot contain blank spaces");
            usernameWarningText.setVisible(true);
        } else {
            usernameWarningText.setVisible(false);
        }
    }

    @FXML
    private void onPasswordWritten(KeyEvent event) {
        password = passwordField.getText();
        passwordLabel.setFill(Color.BLACK);
        confirmPasswordField.setDisable(true);
        viewPasswordButton.setDisable(true);
        boolean bothHold = true;
        
        if(password.isEmpty()) {
            sixCharLengthText.setText("→ More than 6 characters long");
            sixCharLengthText.setFill(Color.BLACK);
            alphanumCharOnlyText.setText("→ Alphanumeric characters only");
            alphanumCharOnlyText.setFill(Color.BLACK);
        } else {
            confirmPasswordMessage.setVisible(false);
            viewPasswordButton.setDisable(false);
            //Length requisite
            if(password.length() > 6) {
                sixCharLengthText.setText("🗸 More than 6 characters long");
                sixCharLengthText.setFill(Color.FORESTGREEN);
            } else {
                sixCharLengthText.setText("→ More than 6 characters long");
                sixCharLengthText.setFill(Color.RED);
                bothHold &= false;
            }
            //Char requisite
            if(isAlphanumeric(password)) {
                alphanumCharOnlyText.setText("🗸 Alphanumeric characters only");
                alphanumCharOnlyText.setFill(Color.FORESTGREEN);
            } else {
                alphanumCharOnlyText.setText("→ Alphanumeric characters only");
                alphanumCharOnlyText.setFill(Color.RED);
                bothHold &= false;
            }
            
            if(bothHold) confirmPasswordField.setDisable(false);
        }
    }

    public static boolean isAlphanumeric(String str) {
        for (int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if (c < 0x30 || (c >= 0x3a && c <= 0x40) || (c > 0x5a && c <= 0x60) || c > 0x7a)
                return false;
        }
        return true;
    }
    
    
    /***ALL: Profile Picture selection***/
    @FXML
    private void onSelectImageButtonPressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
		
        //Selection extension filters (only images supported by ImageView natively)
        fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("All Image Files", "*.jpg", "*.jpeg", "*.png", "*.bmp"),
        new FileChooser.ExtensionFilter("JPG, JPEG", "*.jpg", "*.jpeg"),
        new FileChooser.ExtensionFilter("PNG", "*.png"),
        new FileChooser.ExtensionFilter("BMP", "*.bmp")
        );
		
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        
        if(selectedFile == null) return;
        
        avatar = new Image(selectedFile.toURI().toString());
        //The avatar gets cut to fit the ImageView squared shape
        profilePicture.setImage(cropSquaredCenter(avatar));
    }
    
    //To crop the given image into a centered square, if not square-shaped already
    public static Image cropSquaredCenter(Image image) {
        int imW = (int) image.getWidth();
        int imH = (int) image.getHeight();

        if(imH == imW) return image; //Already a square
        else {
            int sideLength = Math.min(imW, imH);
            int initX = (imW - sideLength) / 2;
            int initY = (imH - sideLength) / 2;

            //CROPPING (centered)
            PixelReader reader = image.getPixelReader();
            WritableImage croppedImage = new WritableImage(reader, initX, initY, sideLength, sideLength);
            
            return croppedImage;
        }
    }
    
    
    /***ALL: Profile Picture removal***/
    @FXML
    private void onProfilePictureEntered(MouseEvent event) {
        if(isAvatEmpty()) return;
        else {   
            profilePicture.setOpacity(0.5);
        }
    }
    
    @FXML
    private void onProfilePictureClicked(MouseEvent event){
        if(isAvatEmpty()) return;
        else {   
            profilePicture.setImage(emptyAvatar);
            profilePicture.setOpacity(1);
        }
    }
      
    @FXML
    private void onProfilePictureExited(MouseEvent event) {
        profilePicture.setOpacity(1);
    }
    
    boolean isAvatEmpty() {
        if(profilePicture.getImage().getUrl() == null) return false; //If cropped image (local resource, no URL)
        //equals() is not overriden by the Image class to compare the content of the images, so their URL is compared instead
        else return profilePicture.getImage().getUrl().equals(emptyAvatar.getUrl());
    }
    
    
    /***ALL: Window navigation via ENTER key***/
    @FXML
    private void onKeyPressedOnName(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code != null && code.equals(KeyCode.ENTER)) {
            surnameField.requestFocus();
        }
    }

    @FXML
    private void onKeyPressedOnSurname(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code != null && code.equals(KeyCode.ENTER)) {
            emailField.requestFocus();
        }
    }

    @FXML
    private void onKeyPressedOnMail(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code != null && code.equals(KeyCode.ENTER)) {
            usernameField.requestFocus();
        }
    }

    @FXML
    private void onKeyPressedOnUsername(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code != null && code.equals(KeyCode.ENTER)) {
            passwordField.requestFocus();
        }
    }

    @FXML
    private void onKeyPressedOnPassword(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code != null && code.equals(KeyCode.ENTER)) {
            confirmPasswordField.requestFocus();
        }
    }

    @FXML
    private void onKeyPressedOnConfirmPassword(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code != null && code.equals(KeyCode.ENTER)) {
            selectImageButton.requestFocus();
        }
    }

    @FXML
    private void onKeyPressedOnAccount(KeyEvent event) {
        onCreateAccountButtonPressed(new ActionEvent());
    }

    @FXML
    private void onKeyPressedOnImage(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code != null && code.equals(KeyCode.ENTER)) {
            onSelectImageButtonPressed(new ActionEvent());
            createAccountButton.requestFocus();
        }
    }
}
