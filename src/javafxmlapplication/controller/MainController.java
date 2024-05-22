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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
    @FXML
    private Button logOutButton;

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
    
    public void initMain(Stage stage) {
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
    }

    @FXML
    private void editProfile(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ProfileSettings.fxml"));
            BorderPane root = loader.load();
            
            ProfileSettingsController profSetController = loader.<ProfileSettingsController>getController();
            profSetController.initProfileSettingsPage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Profile Settings");
        } catch (IOException ioe) {
            System.err.println("Unable to load that page: " + ioe);
        }
    }
   @FXML
    private void onLogOutPressed(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Log out");
        alert.setHeaderText(null);
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Report.fxml"));
            BorderPane root = loader.load();
            
            ReportController reportController = loader.<ReportController>getController();
            reportController.initReportPage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Generate Report");
        } catch (IOException ioe) {
            System.err.println("Unable to load that page: " + ioe);
        }
    }


    @FXML
    private void onCompareExpensePressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ExpenseAccount.fxml"));
            //Stage stage = new Stage();
            BorderPane root = loader.load();
            // TODO: Add controller and call the init method similar to
            ExpenseAccountController expenseAccountController = loader.<ExpenseAccountController>getController();
            expenseAccountController.initExpenseAccountPage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Expense Account");
        } catch (IOException ioe) {
            System.err.println("Unable to load that page: " + ioe);
        }
    }

    @FXML
    private void onManageCategoryPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ManageCategories.fxml"));
            BorderPane root = loader.load();
            
            ManageCategoriesController manageCategoriesController = loader.<ManageCategoriesController>getController();
            manageCategoriesController.initManageCategoriesPage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Manage categories");
        } catch (IOException ioe) {
            System.err.println("Unable to load that page: " + ioe);
        }
    }
    
}
