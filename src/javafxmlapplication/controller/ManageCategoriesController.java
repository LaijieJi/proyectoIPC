/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

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
    private ListView<?> categoryList;

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
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../view/ModifyCategory.fxml"));
        AnchorPane root = (AnchorPane) myLoader.load();
        
        ModifyCategoryController modifyCategory = myLoader.getController();
        modifyCategory.initFields(null,null);
                
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add category");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void onDeleteCategoryButtonPressed(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete category");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this category?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            
        }
    }
    public class CategoryCell extends ListCell<Task>{
        public CategoryCell(){
            try{
                loadFXML();
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        
        private void loadFXML() throws IOException{
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("..\view\"));
            //loader.setController(this);
            //loader.setRoot(this);
            //loader.load();
        }
        
        @Override
        protected void updateItem(Task item, boolean empty){
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }else{
                //set
            }
        }
    }
}
