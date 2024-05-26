/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;

/**
 * FXML Controller class
 *
 * @author laijie
 */
public class ManageCategoriesController implements Initializable {
    
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;
    
    private Acount account;

    @FXML
    private Button backButton;
    @FXML
    private Button deleteCategoryButton;
    @FXML
    private Button newCategoryButton;
    @FXML
    private ListView<Category> categoryList;
    
    List<Category> categories;
    ObservableList<Category> list;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            account = Acount.getInstance();
            categories = account.getUserCategories();
        } catch (AcountDAOException e) {
            System.err.println(e);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
        
        list = FXCollections.observableList(categories);
        
        categoryList.setItems(list);
        
        categoryList.setCellFactory(param -> new CategoryCardListCell());
        
        deleteCategoryButton.disableProperty().bind(
                categoryList.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
    }    

    public void initManageCategoriesPage(Stage stage){
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
    private void onAddButtonPressed(ActionEvent event) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ModifyCategory.fxml"));
            Pane root = loader.load();
            
            ModifyCategoryController modifyCategories = loader.<ModifyCategoryController>getController();
            modifyCategories.initFields(null,null);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Modify category");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            try {
                categories = account.getUserCategories();
                list = FXCollections.observableList(categories);
                categoryList.setItems(list);
            } catch (Exception e) {
                System.err.println(e);
            }
            
            categoryList.refresh();
            categoryList.getSelectionModel().selectLast();
        } catch (IOException ioe) {
            System.err.println("Unable to load that page: " + ioe);
        }
    }

    @FXML
    private void onDeleteCategoryButtonPressed(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete category");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this category?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            try {
                Category c = categoryList.getSelectionModel().getSelectedItem();
                account.removeCategory(c);
                list.remove(c);
                categoryList.getSelectionModel().clearSelection();
            } catch (AcountDAOException ex) {
                System.err.println(ex);
            }
        }
    }
}
