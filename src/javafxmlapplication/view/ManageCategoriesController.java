/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author laijie
 */
public class ManageCategoriesController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private Button newExpenseButton;
    @FXML
    private ListView<?> expenseList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onBackButtonPressed(ActionEvent event) {
    }

    @FXML
    private void onAddButtonPressed(ActionEvent event) {
    }
    
}
