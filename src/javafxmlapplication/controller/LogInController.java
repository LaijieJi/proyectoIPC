/*a
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.Bindings;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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
    
    private int logInOK;

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
    @FXML
    private BorderPane borderPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try{
            account = Acount.getInstance();
        } catch (AcountDAOException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = error.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
            error.getDialogPane().getStyleClass().add("alert");
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
            DialogPane dialogPane = error.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
            error.getDialogPane().getStyleClass().add("alert");
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
        
        /***loginButton config***/
        loginButton.setDisable(true);
        logInOK = 0;
        
        /***seePasswordButton config***/
        seePasswordButton.setDisable(true);
        seePasswordButton.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            password = passwordInput.getText();
            passwordInput.clear();
            passwordInput.setPromptText(password);
        });
        seePasswordButton.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            passwordInput.setText(password);
            passwordInput.setPromptText("");
        });
        
        BooleanBinding fieldsFilled = Bindings.createBooleanBinding(
            () -> !nickNameInput.getText().trim().isEmpty() && !passwordInput.getText().trim().isEmpty(),
            nickNameInput.textProperty(),
            passwordInput.textProperty()
        );

        loginButton.disableProperty().bind(fieldsFilled.not());
    }    
    
    public void initLogin(Stage stage){
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
    }

    @FXML
    private void onLoginButtonPressed(ActionEvent event) throws IOException{
        String nickname = nickNameInput.getText();
        String password = passwordInput.getText();
        
        try{
            boolean login = account.logInUserByCredentials(nickname, password);
            if(login) {
                onUserLogged();
            } else {
                passwordErrorText.setText("Credentials are incorrect, please try again");
            }
        } catch (AcountDAOException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = error.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
            error.getDialogPane().getStyleClass().add("alert");
            error.setTitle("Exception Dialog");
            error.setHeaderText(null);
            error.setContentText("Error trying to log in");
            
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
        }
    }
    
    @FXML
    private void onSignUpButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SignUp.fxml"));
        //Stage stage = new Stage();
        BorderPane root = loader.load();
        // TODO: Add controller and call the init method similar to
        SignUpController signUpController = loader.<SignUpController>getController();
        signUpController.initSignUpPage(primaryStage);
        
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
        primaryStage.setResizable(true);
    }

    
        @FXML
    private void onPasswordWritten(KeyEvent event) {
        seePasswordButton.setDisable(true);
        if(!passwordInput.getText().isEmpty()) seePasswordButton.setDisable(false);
    }
    
    /***ALL: Window navigation via ENTER key***/
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
            Alert error = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = error.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
            error.getDialogPane().getStyleClass().add("alert");
            error.setTitle("Exception Dialog");
            error.setHeaderText(null);
            error.setContentText("Error trying to log in");
            
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
        }
    }



}
