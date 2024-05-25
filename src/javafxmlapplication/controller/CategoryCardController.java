/*
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;

/**
 * FXML Controller class
 *
 * @author ICATFOR
 */
public class CategoryCardController implements Initializable {

    private Category category;
    
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;
    
    private Acount account;
    
    @FXML
    private HBox cell;
    @FXML
    private Circle colorCircle;
    @FXML
    private Label title;
    @FXML
    private Label description;
    

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
    
    public void setCategory(Category category) {
        this.category = category;
        if (category != null) {
            title.setText(category.getName());
            String s[] = category.getDescription().split("/");
            if (s.length > 1) description.setText(s[1]);
            else description.setText("");
        }
    }
}
