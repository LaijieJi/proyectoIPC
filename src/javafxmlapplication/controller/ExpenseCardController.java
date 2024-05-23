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
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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
    }

    public void loadData(Charge charge) {
        // TODO: Initialize data for this card
    }

    @Override
    protected void updateItem(Charge item, boolean empty) {
        super.updateItem(item, empty);
        // This is mandatory
        if (item == null || empty) {

        } else {

        }
    }

    @FXML
    private void onButtonPressed(ActionEvent event) {
        
    }

}
