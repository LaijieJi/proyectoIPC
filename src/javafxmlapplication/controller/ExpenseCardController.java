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
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Charge;

/**
 * FXML Controller class
 *
 * @author laijie
 */
public class ExpenseCardController extends ListCell<Charge> implements Initializable {

    private Acount account;

    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;
    
    private Charge charge;

    @FXML
    private Circle colorCircle;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Button threeDotButton;
    @FXML
    private Label costLabel;
    
    private ContextMenu contextualMenu;
    @FXML
    private HBox cell;

    public ExpenseCardController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/expenseCard.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*title.setText(constructorCharge.getName());
        description.setText(constructorCharge.getDescription());
        costLabel.setText("" + constructorCharge.getCost());
        this.charge = constructorCharge;*/
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            account = Acount.getInstance();
        } catch (AcountDAOException e) {
            System.err.println(e);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
        
        contextualMenu = new ContextMenu();
        
        MenuItem edit = new MenuItem("Edit");
        MenuItem delete = new MenuItem("Delete");
        
        edit.setOnAction(e -> editCharge(charge));
        delete.setOnAction(e -> deleteCharge(charge));
        
        contextualMenu.getItems().addAll(edit, delete);
        threeDotButton.setContextMenu(contextualMenu);
        
    }

    @Override
    protected void updateItem(Charge item, boolean empty) {
        super.updateItem(item, empty);
        // This is mandatory
        if (item == null || empty) {
            title.setText(null);
            description.setText(null);
            costLabel.setText(null);
            setGraphic(null);
        } else {
            title.setText(item.getName());
            description.setText(item.getDescription());
            costLabel.setText("" + item.getCost());
            setGraphic(cell);
        }
    }

    @FXML
    private void onButtonPressed(ActionEvent event) {
        this.contextualMenu.show(threeDotButton, threeDotButton.getLayoutX(), threeDotButton.getLayoutY() + threeDotButton.getHeight());
    }
    
    private void editCharge(Charge editingCharge) {
        
    }
    
    private void deleteCharge(Charge deletingCharge) {
        
    }

}
