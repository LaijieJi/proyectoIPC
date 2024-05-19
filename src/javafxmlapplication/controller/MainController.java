/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author ICATFOR
 */
public class MainController implements Initializable {
    
    private Stage primaryStage;
    
    private Scene primaryScene;
    
    private String primaryTitle;

    @FXML
    private Button editButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button addButton;
    
    private Acount account;
    @FXML
    private Text nameLabel;
    @FXML
    private Text mailLabel;
    @FXML
    private ImageView profilePicture;
    @FXML
    private Text usernameLabel;
    @FXML
    private Button generateReportButton;
    @FXML
    private Button manageCategoryButton;
    @FXML
    private Button compareExpenseButton;
    @FXML
    private ListView<?> expenseList;

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
    private void editProfile(ActionEvent event) {
    }

    @FXML
    private void logoutAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Log out");
        alert.setContentText("Are you sure you want to log out?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            System.out.println("Yes");
        }else{
            System.out.println("No");
        }
    }

    @FXML
    private void addExpense(ActionEvent event) {
        
    }

    @FXML
    private void onGenerateReportPressed(ActionEvent event) {
    }

    @FXML
    private void onManagecategoryPressed(ActionEvent event) {
    }

    @FXML
    private void onCompareExpensePressed(ActionEvent event) {
    }
    
}
