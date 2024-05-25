/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;

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
    private ComboBox<String> categorySelection;
    @FXML
    private DatePicker dateSelection;
    @FXML
    private TextField descriptionField;
    @FXML
    private Button addInvoiceButton;
    @FXML
    private Text invoiceText;
    @FXML
    private Text deleteInvoice;
    
    private Image invoice;
    
    List<Category> categories;
    @FXML
    private Button backButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            account = Acount.getInstance();
            categories = account.getUserCategories();
            List<String> l = categories.stream().map(Category::getName).collect(Collectors.toList());
            ObservableList<String> categoriesl = FXCollections.observableList(l);
            categorySelection.setItems(categoriesl);
        } catch (AcountDAOException e) {
            System.err.println(e);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
        saveButton.disableProperty().bind(Bindings.or(
                Bindings.or(nameField.textProperty().isEmpty(),costField.textProperty().isEmpty()),
                Bindings.or(unitField.textProperty().isEmpty(),categorySelection.getSelectionModel().selectedItemProperty().isNull())));
    }    

    public void initUpdateExpense(Stage stage){
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
        nameField.setPromptText("Write a name for the expense here");
        costField.setText("0.0");
        unitField.setText("1");
        descriptionField.setPromptText("Write a description for the expense here");
        categorySelection.getSelectionModel().clearSelection();
        dateSelection.setValue(LocalDate.now());
    }
    
    public void initExpense(Charge charge){
        nameField.setText(charge.getName());
        costField.setText(Double.toString(charge.getCost()));
        unitField.setText(Integer.toString(charge.getUnits()));
        descriptionField.setText(charge.getDescription());
        categorySelection.setValue(charge.getCategory().getName());
        dateSelection.setValue(charge.getDate());
    }
    
    @FXML
    private void cancelAction(ActionEvent event) {
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle);
    }

    @FXML
    private void saveAction(ActionEvent event) {
        Double cost = Double.valueOf(costField.getText());
        Integer units = Integer.valueOf(unitField.getText());
        LocalDate date = dateSelection.getValue();
        Category category = categories.get(categorySelection.getSelectionModel().getSelectedIndex());
        try{
            account.registerCharge(nameField.getText(), descriptionField.getText(), cost, units, invoice, date, category);
        }catch (AcountDAOException e){
            System.out.println(e);
        }
    }

    @FXML
    private void onAddInvoicePressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if(selectedFile == null) {
            return;
        }
        invoice = new Image(selectedFile.toURI().toString());
        invoiceText.textProperty().setValue(selectedFile.getName());
        deleteInvoice.textProperty().setValue(" Delete");
    }

    @FXML
    private void deleteInvoiceAction(MouseEvent event) {
        invoice = null;
    }

    @FXML
    private void onBackButtonPressed(ActionEvent event) {
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle);
        primaryStage.show();
    }
    
}
