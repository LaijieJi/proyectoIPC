/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Acount;
import model.AcountDAOException;
import model.Category;

/**
 * FXML Controller class
 *
 * @author laijie
 */
public class ExpenseAccountController implements Initializable {
    
    List<Category> catList = null;
    ObservableList<Category> observableCatList = null;
    
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;
    
    private Acount account;

    @FXML
    private Button backButton;
    @FXML
    private MenuButton compareOptions;
    @FXML
    private ChoiceBox<Category> categorySelector;
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
        try{
            catList = account.getUserCategories();    
        } catch (Exception e) {
            System.err.println(e);
        }
        
        categorySelector.setItems(observableCatList);
        
        categorySelector.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category cat) {
                return cat.getName();
            }

            @Override
            public Category fromString(String string) {
                return null;
            }
        });
        
        observableCatList = FXCollections.observableList(catList);
        categorySelector.selectionModelProperty().addListener((ob, oldVal, newVal) -> {
            for(Category cat : observableCatList) {
                if(newVal.equals(cat)) {
                    // TODO: Filter by this cat
                }
            }
        });
        categorySelector.getSelectionModel().getSelectedItem();
        
    }    
    
    public void initExpenseAccountPage(Stage stage){
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
    }

    @FXML
    private void onBackButtonPressed(ActionEvent event) {
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle);
        primaryStage.show();
    }
    
}
