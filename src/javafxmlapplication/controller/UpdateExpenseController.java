/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;

/**
 * FXML Controller class
 *
 * @author ICATFOR
 */
public class UpdateExpenseController implements Initializable {
    
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;

    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    
    private Acount account;
    @FXML
    private TextField nameField;
    @FXML
    private TextField costField;
    @FXML
    private TextField unitField;
    @FXML
    private ComboBox<Category> categorySelection;
    @FXML
    private DatePicker dateSelection;
    @FXML
    private TextField descriptionField;
    @FXML
    private Button addInvoiceButton;

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

    public void initUpdateExpense(Stage stage){
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
    }
    
    @FXML
    private void cancelAction(ActionEvent event) {
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle);
    }

    @FXML
    private void saveAction(ActionEvent event) {
        Double cost = Double.parseDouble(costField.getText());
        Integer units = Integer.parseInt(unitField.getText());
        Image scanImage = null;
        LocalDate date = dateSelection.getValue();
        Category category = null;
        try{
            account.registerCharge(nameField.getText(), descriptionField.getText(), cost, units, scanImage, date, category);
        }catch (AcountDAOException e){
            System.out.println(e);
        }
    }

    @FXML
    private void onAddInvoicePressed(ActionEvent event) {
    }
    
}
