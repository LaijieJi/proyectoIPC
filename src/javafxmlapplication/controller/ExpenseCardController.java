package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.VBox;
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
    private Label costLabel;
    @FXML
    private HBox cell;
    @FXML
    private VBox mainVBox;

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
}
