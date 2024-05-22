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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.BorderPane;
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
    @FXML
    private MenuItem restOfYear;
    @FXML
    private MenuItem sameMonth;

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
        
        observableCatList = FXCollections.observableList(catList);
        categorySelector.selectionModelProperty().addListener((ob, oldVal, newVal) -> {
            
            // TODO: Filter by Category
        });
        categorySelector.getSelectionModel().getSelectedItem();
        
        categorySelector.setItems(observableCatList);
        
        categorySelector.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category cat) {
                if(cat == null) return "";
                return cat.getName();
            }

            @Override
            public Category fromString(String string) {
                return null;
            }
        });
        
        restOfYear.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.out.println("Pressed");
                onRestOfYear(t);
            }
        });
        
        sameMonth.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.out.println("Pressed");
                onSameMonth(t);
            }
        });
        
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

    @FXML
    private void onSameMonth(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SameMonthLastYear.fxml"));
            //Stage stage = new Stage();
            BorderPane root = loader.load();
            // TODO: Add controller and call the init method similar to
            SameMonthLastYearController sameMonthController = loader.<SameMonthLastYearController>getController();
            sameMonthController.initSameMonthPage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Compare with same month from last year");
        } catch (IOException ioe) {
            System.err.println("Unable to load that page: " + ioe);
        }
    }

    @FXML
    private void onRestOfYear(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/YearComparison.fxml"));
            //Stage stage = new Stage();
            BorderPane root = loader.load();
            // TODO: Add controller and call the init method similar to
            YearComparisonController yearComparisonController = loader.<YearComparisonController>getController();
            yearComparisonController.initYearComparisonPage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Compare with rest of year");
        } catch (IOException ioe) {
            System.err.println("Unable to load that page: " + ioe);
        }
    }
    
}
