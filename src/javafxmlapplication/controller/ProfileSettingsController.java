/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.Priority;
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
    private String passwordConfirmation;
    
    Image emptyAvatar = new Image(getClass().getResource(
            "../styles/resources/Profile_avatar_placeholder_large.png").toString()
                );
    private Image avatar;
    //For isAvatEmpty() to work correctly the 1st time
    private boolean avEmpty = SignUpController.avEmpty;
    
    private boolean bothHold;
	
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
    @FXML
    private Text nameWarningText;
    @FXML
    private Text surnameWarningText;
    @FXML
    private Text emailWarningText;
    @FXML
    private Text newPasswordLabel;
    @FXML
    private Text passwordLabel;

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
        
        user = account.getLoggedUser();
        
        usernameText.setText(user.getNickName());
        nameField.setText(user.getName());
        surnameField.setText(user.getSurname());
        emailField.setText(user.getEmail());
        
        /***Profile Picture related init config***/
        if(avEmpty) profilePicture.setImage(emptyAvatar);
        else profilePicture.setImage(user.getImage());
        removePicture.toBack();
        
        /***viewPasswordButton & viewNewPasswordButton config***/
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
        
        viewNewPasswordButton.setDisable(true);
        viewNewPasswordButton.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            password = newPasswordField.getText(); 
            newPasswordField.clear();
            newPasswordField.setPromptText(password);
        });
        viewNewPasswordButton.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            newPasswordField.setText(password);
            newPasswordField.setPromptText("");
        });
        
        /***Password-related & other warnings init config***/
        sixCharLengthText.setText("? More than 6 characters long");
        sixCharLengthText.setFill(Color.BLACK);
        sixCharLengthText.setVisible(false);
        alphanumCharOnlyText.setText("? Alphanumeric characters only");
        alphanumCharOnlyText.setFill(Color.BLACK);
        alphanumCharOnlyText.setVisible(false);
        
        nameWarningText.setVisible(false);
        surnameWarningText.setVisible(false);
        emailWarningText.setVisible(false);
        wrongPasswordText.setVisible(false);
        confirmPasswordMessage.setVisible(false);
        
        passwordLabel.setOpacity(1);
        passwordField.setDisable(false);
        newPasswordLabel.setOpacity(0.2);
        newPasswordField.setDisable(true);
        confirmNewPasswordField.setDisable(true);
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
            primaryStage.setResizable(true);
        } catch (IOException ioe) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Exception Dialog");
            error.setHeaderText(null);
            error.setContentText("Unable to load the page");
            
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
    }
    
    @FXML
    private void onSaveChangesButtonPressed(ActionEvent event) {
        name = nameField.getText();
        surname = surnameField.getText();
        email = emailField.getText();
        password = newPasswordField.getText();
        passwordConfirmation = confirmNewPasswordField.getText();
        avatar = profilePicture.getImage();
        
        //1st: check the compulsory fields in case they're left blank
        if(name.isEmpty()) {
            nameField.setText(user.getName());
            nameWarningText.setVisible(true);
        }
        else user.setName(name);
        
        if(surname.isEmpty()) {
            surnameField.setText(user.getSurname());
            surnameWarningText.setVisible(true);
        }
        else user.setSurname(surname);
         
        if(email.isEmpty()) {
            emailField.setText(user.getEmail());
            emailWarningText.setVisible(true);
        }
        else user.setEmail(email);
        
        //2nd: check New Password field && New Password Confirmation     
        if(!password.isEmpty()) { 
            if(confirmNewPasswordField.isDisabled() && !bothHold) newPasswordLabel.setFill(Color.FIREBRICK);
            else {
                if(passwordConfirmation.isEmpty()) {
                    newPasswordLabel.setFill(Color.FIREBRICK);
                    confirmPasswordMessage.setText("Password confirmation required");
                    confirmPasswordMessage.setFill(Color.RED);
                    confirmPasswordMessage.setVisible(true);
                } else if(!passwordConfirmation.equals(password)) {
                    newPasswordLabel.setFill(Color.FIREBRICK);
                    confirmPasswordMessage.setText("The passwords don't match");
                    confirmPasswordMessage.setFill(Color.RED);
                    confirmPasswordMessage.setVisible(true);
                } else {
                    newPasswordLabel.setFill(Color.BLACK);
                    confirmPasswordMessage.setText("Password changed!");
                    confirmPasswordMessage.setFill(Color.FORESTGREEN);
                    confirmPasswordMessage.setVisible(true);
                    user.setPassword(password);
                }
            }    
        }
        
        //3rd: set Profile Picture
        user.setImage(avatar);
    }
    
    
    /***ALL: Text input listeners (Password & Username's also check requisites)***/
    @FXML
    private void onNameWritten(KeyEvent event) {
        nameWarningText.setVisible(false);
    }

    @FXML
    private void onSurnameWritten(KeyEvent event) {
        surnameWarningText.setVisible(false);
    }

    @FXML
    private void onMailWritten(KeyEvent event) {
        emailWarningText.setVisible(false);
    }
    
    @FXML
    private void onPasswordWritten(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code.equals(KeyCode.ENTER)) {
            onKeyPressedOnPassword(event);
        } else {
            wrongPasswordText.setVisible(false);
            viewPasswordButton.setDisable(true);
            if(!passwordField.getText().isEmpty()) viewPasswordButton.setDisable(false);
        }
    }
    
    @FXML
    private void onConfirmNewPasswordWritten(KeyEvent event) {
        confirmPasswordMessage.setVisible(false);
        newPasswordLabel.setFill(Color.BLACK);
    }
    
    @FXML
    private void onNewPasswordWritten(KeyEvent event) {
        password = newPasswordField.getText();
        newPasswordLabel.setFill(Color.BLACK);
        confirmNewPasswordField.setDisable(true);
        viewNewPasswordButton.setDisable(true);
        bothHold = true;
        
        if(password.isEmpty()) {
            sixCharLengthText.setText("? More than 6 characters long");
            sixCharLengthText.setFill(Color.BLACK);
            alphanumCharOnlyText.setText("? Alphanumeric characters only");
            alphanumCharOnlyText.setFill(Color.BLACK);
        } else {
            confirmPasswordMessage.setVisible(false);
            viewNewPasswordButton.setDisable(false);
            //Length requisite
            if(password.length() > 6) {
                sixCharLengthText.setText("?? More than 6 characters long");
                sixCharLengthText.setFill(Color.FORESTGREEN);
            } else {
                sixCharLengthText.setText("? More than 6 characters long");
                sixCharLengthText.setFill(Color.RED);
                bothHold &= false;
            }
            //Char requisite
            if(isAlphanumeric(password)) {
                alphanumCharOnlyText.setText("?? Alphanumeric characters only");
                alphanumCharOnlyText.setFill(Color.FORESTGREEN);
            } else {
                alphanumCharOnlyText.setText("? Alphanumeric characters only");
                alphanumCharOnlyText.setFill(Color.RED);
                bothHold &= false;
            }
            
            if(bothHold) confirmNewPasswordField.setDisable(false);
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
    //Personal data section
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
            saveChangesButton.requestFocus();
        }
    }
    
    //Change password section
    @FXML
    private void onKeyPressedOnPassword(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code != null && code.equals(KeyCode.ENTER)) {
            if(passwordField.getText().equals(user.getPassword())) {
                sixCharLengthText.setVisible(true);
                alphanumCharOnlyText.setVisible(true);
                
                passwordField.setDisable(true);
                viewPasswordButton.setDisable(true);
                passwordLabel.setOpacity(0.2);
                
                newPasswordLabel.setOpacity(1);
                newPasswordField.setDisable(false);          
                newPasswordField.requestFocus();  
                
            } else wrongPasswordText.setVisible(true);
        }
    }

    @FXML
    private void onKeyPressedOnNewPassword(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code != null && code.equals(KeyCode.ENTER)) {
            confirmNewPasswordField.requestFocus();
        }
    }

    @FXML
    private void onKeyPressedOnConfirmNewPassword(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code != null && code.equals(KeyCode.ENTER)) {
            saveChangesButton.requestFocus();
        }
    }
    
    //Buttons
    @FXML
    private void onKeyPressedOnImage(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code != null && code.equals(KeyCode.ENTER)) {
            onSelectImageButtonPressed(new ActionEvent());
        }
    }

    @FXML
    private void onKeyPressedOnSaveChanges(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code != null && code.equals(KeyCode.ENTER)) {
            onSaveChangesButtonPressed(new ActionEvent());
        }
    }

}