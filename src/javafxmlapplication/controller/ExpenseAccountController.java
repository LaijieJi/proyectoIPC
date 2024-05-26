/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;

/**
 * FXML Controller class
 *
 * @author laijie
 */
public class ExpenseAccountController implements Initializable {
    
    List<Category> categoryList = null;
    ObservableList<Category> observableCatList = null;
    List<Charge> chargeList = null;
    ObservableList<Charge> observableChargeList = null;
    
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
    private ListView<Charge> expenseList;
    @FXML
    private MenuItem restOfYear;
    @FXML
    private MenuItem sameMonth;
    @FXML
    private Button clearButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            account = Acount.getInstance();
        } catch (AcountDAOException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = error.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
            error.getDialogPane().getStyleClass().add("alert");
            error.setTitle("Exception Dialog");
            error.setHeaderText(null);
            error.setContentText("An error has occurred while loading your account");
            
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionText = sw.toString();
            
            Label label = new Label("Exception:");
            
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea,Priority.ALWAYS);
            GridPane.setHgrow(textArea,Priority.ALWAYS);
            
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0 ,1);
            
            error.getDialogPane().setExpandableContent(expContent);
            error.showAndWait();
        } catch (IOException ioe) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = error.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
            error.getDialogPane().getStyleClass().add("alert");
            error.setTitle("Exception Dialog");
            error.setHeaderText(null);
            error.setContentText("An error has occurred while loading your account");
            
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ioe.printStackTrace(pw);
            String exceptionText = sw.toString();
            
            Label label = new Label("Exception:");
            
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea,Priority.ALWAYS);
            GridPane.setHgrow(textArea,Priority.ALWAYS);
            
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0 ,1);
            
            error.getDialogPane().setExpandableContent(expContent);
            error.showAndWait();
        }
        
        try{
            categoryList = account.getUserCategories();
            chargeList = account.getUserCharges();
        } catch (Exception e) {
            Alert error = new Alert(AlertType.ERROR);
            DialogPane dialogPane = error.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
            error.getDialogPane().getStyleClass().add("alert");
            error.setTitle("Exception Dialog");
            error.setHeaderText(null);
            error.setContentText("An error has occurred while loading your account's information");
            
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionText = sw.toString();
            
            Label label = new Label("Exception:");
            
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea,Priority.ALWAYS);
            GridPane.setHgrow(textArea,Priority.ALWAYS);
            
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0 ,1);
            
            error.getDialogPane().setExpandableContent(expContent);
            error.showAndWait();
        }
        
        observableCatList = FXCollections.observableArrayList(categoryList);
        observableChargeList = FXCollections.observableArrayList(chargeList);
        
        categorySelector.getSelectionModel().getSelectedItem();
        
        categorySelector.setItems(observableCatList);
        expenseList.setItems(observableChargeList);
        expenseList.setCellFactory(param -> new ExpenseCardListCell(observableChargeList));
        
        categorySelector.getSelectionModel().selectedItemProperty().addListener((ob, oldVal, newVal) -> {
            try {
                List<Charge> auxList = new ArrayList<>();
                if(newVal == null) {
                    auxList = account.getUserCharges();
                } else {
                    List<Charge> aux = account.getUserCharges();
                    for(Charge auxCharge : aux) {
                        if(auxCharge.getCategory().equals(newVal)) {
                            auxList.add(auxCharge);
                        }
                    }
                }
                
                chargeList = auxList;
                observableChargeList = FXCollections.observableArrayList(chargeList);
                expenseList.setItems(observableChargeList);
            } catch (Exception e) {
                Alert error = new Alert(AlertType.ERROR);
                DialogPane dialogPane = error.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
                error.getDialogPane().getStyleClass().add("alert");
                error.setTitle("Exception Dialog");
                error.setHeaderText(null);
                error.setContentText("An error has occurred while loading your expenses");

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String exceptionText = sw.toString();

                Label label = new Label("Exception:");

                TextArea textArea = new TextArea(exceptionText);
                textArea.setEditable(false);
                textArea.setWrapText(true);

                textArea.setMaxWidth(Double.MAX_VALUE);
                textArea.setMaxHeight(Double.MAX_VALUE);
                GridPane.setVgrow(textArea,Priority.ALWAYS);
                GridPane.setHgrow(textArea,Priority.ALWAYS);

                GridPane expContent = new GridPane();
                expContent.setMaxWidth(Double.MAX_VALUE);
                expContent.add(label, 0, 0);
                expContent.add(textArea, 0 ,1);

                error.getDialogPane().setExpandableContent(expContent);
                error.showAndWait();
            }
        });
        
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
        
        categorySelector.setTooltip(new Tooltip("Select a category"));
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
        primaryStage.setResizable(true);
    }

    @FXML
    private void onSameMonth(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SameMonthLastYear.fxml"));
            //Stage stage = new Stage();
            BorderPane root = loader.load();
            
            SameMonthLastYearController sameMonthController = loader.<SameMonthLastYearController>getController();
            sameMonthController.initSameMonthPage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Compare with same month from last year");
        } catch (IOException ioe) {
            Alert error = new Alert(AlertType.ERROR);
            DialogPane dialogPane = error.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
            error.getDialogPane().getStyleClass().add("alert");
            error.setTitle("Exception Dialog");
            error.setHeaderText(null);
            error.setContentText("Unable to load the page");
            
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ioe.printStackTrace(pw);
            String exceptionText = sw.toString();
            
            Label label = new Label("Exception:");
            
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea,Priority.ALWAYS);
            GridPane.setHgrow(textArea,Priority.ALWAYS);
            
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0 ,1);
            
            error.getDialogPane().setExpandableContent(expContent);
            error.showAndWait();
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
            Alert error = new Alert(AlertType.ERROR);
            DialogPane dialogPane = error.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
            error.getDialogPane().getStyleClass().add("alert");
            error.setTitle("Exception Dialog");
            error.setHeaderText(null);
            error.setContentText("Unable to load the page");
            
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ioe.printStackTrace(pw);
            String exceptionText = sw.toString();
            
            Label label = new Label("Exception:");
            
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea,Priority.ALWAYS);
            GridPane.setHgrow(textArea,Priority.ALWAYS);
            
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0 ,1);
            
            error.getDialogPane().setExpandableContent(expContent);
            error.showAndWait();
        }
    }

    @FXML
    private void removeFilter(ActionEvent event) {
        try {
            chargeList = account.getUserCharges();
        } catch (Exception e) {
            Alert error = new Alert(AlertType.ERROR);
            DialogPane dialogPane = error.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
            error.getDialogPane().getStyleClass().add("alert");
            error.setTitle("Exception Dialog");
            error.setHeaderText(null);
            error.setContentText("An error has occurred while loading your expenses");
            
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionText = sw.toString();
            
            Label label = new Label("Exception:");
            
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea,Priority.ALWAYS);
            GridPane.setHgrow(textArea,Priority.ALWAYS);
            
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0 ,1);
            
            error.getDialogPane().setExpandableContent(expContent);
            error.showAndWait();
        }
        observableChargeList = FXCollections.observableList(chargeList);
        expenseList.setItems(observableChargeList);
    }
    
    public void editChargeFromList() {
        
    }
    
}
