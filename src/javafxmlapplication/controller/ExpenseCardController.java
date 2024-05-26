package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
        // Edit logic here
    }

    private void deleteCharge(Charge deletingCharge) {
        try {
            account.removeCharge(deletingCharge);
            
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public void setDeleteAction(Runnable deleteAction) {
        this.deleteAction = deleteAction;
    }
    
}
