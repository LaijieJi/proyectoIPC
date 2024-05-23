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
    
    private Acount account;
    
    Image avatar = null;
    
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
    private Text usernameMessageText;
    
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
    private Text passwordLabel1;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Text confirmPasswordMessage;

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
        
        viewPasswordButton.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            password = passwordField.getText();
            passwordField.clear();
            passwordField.setPromptText(password);
        });
        viewPasswordButton.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            passwordField.setText(password);
            passwordField.setPromptText("Password");
        });
        
        sixCharLengthText.setText("→ At least 6 characters long");
        sixCharLengthText.setFill(Color.BLACK);
        alphanumCharOnlyText.setText("→ Alphanumeric characters only");
        alphanumCharOnlyText.setFill(Color.BLACK);
        confirmPasswordMessage.setFill(Color.BLACK);
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
    private void onCreateAccountButtonPressed(ActionEvent event) {
        everythingOK = true;
        
        passwordConfirmation = confirmPasswordField.getText();
        if(nameField.getText().isEmpty()) {
            nameLabel.setFill(Color.RED);
            everythingOK &= false;
        } else {
            nameLabel.setFill(Color.BLACK);
        }
        
        if(surnameField.getText().isEmpty()) {
            surnameLabel.setFill(Color.RED);
            everythingOK &= false; 
        } else {
            surnameLabel.setFill(Color.BLACK);
        }
        
        if(usernameField.getText().isEmpty()) {
            usernameLabel.setFill(Color.RED);
            everythingOK &= false;
        } else {
            usernameLabel.setFill(Color.BLACK);
        }
        
        if(emailField.getText().isEmpty()) {
            mailLabel.setFill(Color.RED);
            everythingOK &= false;
        } else {
            mailLabel.setFill(Color.BLACK);
        }
        
        password = passwordField.getText();
        
        if(password.isEmpty()) {
            everythingOK &= false;
            passwordLabel.setFill(Color.RED);
        } else {
            passwordLabel.setFill(Color.BLACK);
        }
        
        if(passwordConfirmation.isEmpty()) {
            everythingOK &= false;
            passwordLabel.setFill(Color.RED);
        } else {
            if(passwordConfirmation.equals(password)) {
                confirmPasswordMessage.setFill(Color.GREEN);
                confirmPasswordMessage.setText("🗸 Passwords match");
            } else {
                everythingOK &= false;
                confirmPasswordMessage.setText("→ Passwords must match");
                confirmPasswordMessage.setFill(Color.RED);
            }
            passwordLabel.setFill(Color.BLACK);
        }
        
        if(account.existsLogin(usernameField.getText())) {
            everythingOK &= false;
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error during account creation");
            alert.setContentText("An account with this username already exists, try with another");
            alert.showAndWait();
        }
        
        if(everythingOK) {
            System.out.println("everything ok");
            boolean registered = false;
            try{
                registered = account.registerUser(nameField.getText(), surnameField.getText() , emailField.getText() , usernameField.getText() , password, avatar, LocalDate.now());
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
    
    public boolean isAlphanumeric(String str) {
        for (int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if (c < 0x30 || (c >= 0x3a && c <= 0x40) || (c > 0x5a && c <= 0x60) || c > 0x7a)
                return false;
        }
        return true;
    }

@FXML
    private void onSelectImageButtonPressed(ActionEvent event) {
        // TODO: implement profile picture selection 
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
        
        avatar = new Image(selectedFile.toURI().toString(), 200, 200, false, false);
		
	profilePicture.imageProperty().setValue(avatar);
        
    }

    /**
    *  Listens to the event in which something is written in the password field and
    *  evaluates the different cases
    **/
    @FXML
    private void onTextWritten(KeyEvent event) {
        password = passwordField.getText();
        
        if(password.isEmpty()) {
            passwordLabel.setFill(Color.BLACK);
            sixCharLengthText.setText("→ At least 6 characters long");
            sixCharLengthText.setFill(Color.BLACK);
            alphanumCharOnlyText.setText("→ Alphanumeric characters only");
            alphanumCharOnlyText.setFill(Color.BLACK);
        } else {
            passwordLabel.setFill(Color.BLACK);
            if(password.length() < 6) {
                sixCharLengthText.setText("→ At least 6 characters long");
                sixCharLengthText.setFill(Color.RED);
                everythingOK = false;
            } else {
                sixCharLengthText.setText("🗸 At least 6 characters long");
                sixCharLengthText.setFill(Color.GREEN);
                everythingOK = true;
            }
            
            if(isAlphanumeric(password)) {
                alphanumCharOnlyText.setText("🗸 Alphanumeric characters only");
                alphanumCharOnlyText.setFill(Color.GREEN);
                if(everythingOK) everythingOK = true;
            } else {
                alphanumCharOnlyText.setText("→ Alphanumeric characters only");
                alphanumCharOnlyText.setFill(Color.RED);
                everythingOK = false;
            }
        }
    }

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
