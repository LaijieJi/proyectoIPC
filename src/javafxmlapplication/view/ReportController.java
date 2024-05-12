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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author laijie
 */
public class ReportController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private TextField locationField;
    @FXML
    private Button selectLocationButton;
    @FXML
    private Button saveButton;

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
    private void onSelectLocationButtonPressed(ActionEvent event) {
    }

    @FXML
    private void onSaveReportButtonPressed(ActionEvent event) {
    }
    
}
