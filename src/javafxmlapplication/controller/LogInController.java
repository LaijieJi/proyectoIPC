/*a
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author laijie
 */
public class LogInController implements Initializable {
    
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;
    
    private Acount account;
    
    private String password;

    @FXML
    private TextField nickNameInput;
    @FXML
    private Label nickNameErrorText;
    @FXML
    private Label passwordErrorText;
    @FXML
    private Button loginButton;
    @FXML
    private Text signupText;
    @FXML
    private Button signupButton;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Button seePasswordButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // testing credentials -> username: test, password: test123
        try{
            account = Acount.getInstance();
        } catch (AcountDAOException e) {
            System.err.println(e);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
        
        seePasswordButton.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            password = passwordInput.getText();
            passwordInput.clear();
            passwordInput.setPromptText(password);
        });
        seePasswordButton.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            passwordInput.setText(password);
            passwordInput.setPromptText("Password");
        });
    }    
    
    public void initLogin(Stage stage){
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
    }

    @FXML
    private void onTextTyped(KeyEvent event) {
        String text = nickNameInput.getText();
        if(text.contains(" ")) {
            nickNameErrorText.setText("Nickname cannot contain blankspaces");
        } else {
            nickNameErrorText.setText(" ");
        }
    }

    @FXML
    private void onLoginButtonPressed(ActionEvent event) throws IOException{
        String nickname = nickNameInput.getText();
        String password = passwordInput.getText();
        if(nickname.contains(" ")) {
            nickNameErrorText.setText("Nickname cannot contain blankspaces");
            return;
        }
        if(nickname.length() == 0) {
            nickNameErrorText.setText("Nickname field cannot be empty");
            return;
        }
        if(password.length() <= 6) {
            passwordErrorText.setText("Password must have length greater than 6");
            return;
        }
        try{
            boolean login = account.logInUserByCredentials(nickname, password);
            if(login) {
                onUserLogged();
            } else {
                passwordErrorText.setText("Credentials are incorrect, please try again");
            }
        } catch (AcountDAOException e) {
            passwordErrorText.setText("Error trying to log in");
        }
    }

    @FXML
    private void onSignUpButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SignUp.fxml"));
        //Stage stage = new Stage();
        BorderPane root = loader.load();
        // TODO: Add controller and call the init method similar to
        SignUpController signUpController = loader.<SignUpController>getController();
        signUpController.initSignUp(primaryStage);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sign Up");
    }
    
    private void handleOnActionButtonBack(ActionEvent event) {
            primaryStage.setScene(primaryScene);
            primaryStage.setTitle(primaryTitle); 
    }
    
    private void onUserLogged() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Main.fxml"));
        Stage stage = new Stage();
        BorderPane root = loader.load();
        
        MainController mainController = loader.<MainController>getController();
        mainController.initMain(primaryStage);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Main Page");
    }

    @FXML
    private void onKeyPressedOnNickName(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code != null && code.equals(KeyCode.ENTER)) {
            passwordInput.requestFocus();
        }
    }

    @FXML
    private void onKeyPressedOnPassword(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code != null && code.equals(KeyCode.ENTER)) {
            loginButton.requestFocus();
        }
    }

    @FXML
    private void onKeyPressedOnLogin(KeyEvent event) {
        try{
            onLoginButtonPressed(new ActionEvent());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}