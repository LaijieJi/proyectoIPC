package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Acount;
import model.AcountDAOException;
import model.Charge;

public class ExpenseCardController implements Initializable {
    
    private Acount account;

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
    @FXML
    private HBox cell;

    private Runnable deleteAction;
    
    private ContextMenu contextualMenu;

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
        delete.setOnAction(e -> {
            deleteCharge(charge);
            if(deleteAction != null) {
                deleteAction.run();
            }
        });
        
        contextualMenu.getItems().addAll(edit, delete);
        threeDotButton.setContextMenu(contextualMenu);
        
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
        if (charge != null) {
            title.setText(charge.getName());
            description.setText(charge.getDescription());
            costLabel.setText(String.valueOf(charge.getCost()));
            String[] s = charge.getCategory().getDescription().split("/");
            colorCircle.setFill(Color.valueOf(s[0]));
         }
    }

    @FXML
    private void onButtonPressed() {
        double sceneX = threeDotButton.localToScene(0, 0).getX() + threeDotButton.getScene().getX() + threeDotButton.getScene().getWindow().getX();
        double sceneY = threeDotButton.localToScene(0, 0).getY() + threeDotButton.getScene().getY() + threeDotButton.getScene().getWindow().getY();
        this.contextualMenu.show(threeDotButton, sceneX, sceneY);
    }

    private void editCharge(Charge editingCharge) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/UpdateExpense.fxml"));
            BorderPane root = loader.load();
            
            UpdateExpenseController updateExpenseController = loader.<UpdateExpenseController>getController();
            updateExpenseController.initExpense(editingCharge.getName(), editingCharge.getCost(), editingCharge.getUnits(), editingCharge.getDescription(), editingCharge.getCategory(), editingCharge.getDate());

            
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../view/Main.fxml"));
            BorderPane root1 = loader1.load();

            MainController mainController = loader1.<MainController>getController();
            
            try {
                account.removeCharge(editingCharge);
                mainController.observableDataList.remove(editingCharge);
            } catch (AcountDAOException ex) {
                System.err.println(ex);
            }
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Add Expense");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            
            
            try{
                mainController.categoryList = account.getUserCategories();
                mainController.dataList = account.getUserCharges();
                mainController.observableDataList = FXCollections.observableArrayList(mainController.dataList);
                mainController.expenseList.setItems(mainController.observableDataList);
            } catch (Exception e) {
                System.err.println(e);
            }
            mainController.expenseList.refresh();
            mainController.expenseList.getSelectionModel().selectLast();
        } catch (IOException ioe) {
            System.err.println("Unable to load that page: " + ioe);
        }
    }

    private void deleteCharge(Charge deletingCharge) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Main.fxml"));
        try {
            BorderPane root = loader.load();
        } catch (IOException ex) {
            System.err.println(ex);
        }

        MainController mainController = loader.<MainController>getController();
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete charge");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this charge?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            try {
                account.removeCharge(deletingCharge);
                mainController.observableDataList.remove(deletingCharge);
                mainController.expenseList.getSelectionModel().clearSelection();
            } catch (AcountDAOException ex) {
                System.err.println(ex);
            }
        }
    }
    
    public void setDeleteAction(Runnable deleteAction) {
        this.deleteAction = deleteAction;
    }
    
}
