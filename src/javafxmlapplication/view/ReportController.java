/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author laijie
 */
public class ReportController implements Initializable {
    
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;

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
    
    public void initReportPage(Stage stage){
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
    private void onSelectLocationButtonPressed(ActionEvent event) {
        
    }

    @FXML
    private void onSaveReportButtonPressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            fileChooser.setInitialFileName("report.txt"); // Specify default file name if needed
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                try {
                    // Create content for the file (for demonstration, let's just write some text)
                    String content = "Best novel ever -> Ruiz Zafón, C. (2001). La Sombra del Viento.";

                    // Write content to the file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(content);
                    }

                    System.out.println("File saved to: " + file.getAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // Handle exception (e.g., show error message)
                }
            }
    }
    
}
