/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;
import model.User;

/**
 * FXML Controller class
 *
 * @author ICATFOR
 */
public class MainController implements Initializable {
    
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;
        
    private Acount account;
    private User user;
    
    List<Charge> dataList;
    List<Category> categoryList;
    ObservableList<Charge> observableDataList;

    @FXML
    private Button editButton;
    @FXML
    private Button addButton;
    @FXML
    private Text nameLabel;
    @FXML
    private Text mailLabel;
    @FXML
    private ImageView profilePicture;
    @FXML
    private Text usernameLabel;
    @FXML
    private Button generateReportButton;
    @FXML
    private Button manageCategoryButton;
    @FXML
    private Button compareExpenseButton;
    @FXML
    public ListView<Charge> expenseList;
    @FXML
    private Button logOutButton;
    @FXML
    private Button deleteButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            account = Acount.getInstance();
            user = account.getLoggedUser();
        } catch (AcountDAOException e) {
            Alert error = new Alert(AlertType.ERROR);
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
            Alert error = new Alert(AlertType.ERROR);
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
        
        deleteButton.disableProperty().bind(
                expenseList.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
        
        editButton.disableProperty().bind(
                expenseList.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
        
        try{
            categoryList = account.getUserCategories();
            dataList = account.getUserCharges();
        } catch (Exception e) {
            Alert error = new Alert(AlertType.ERROR);
            DialogPane dialogPane = error.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
            error.getDialogPane().getStyleClass().add("alert");
            error.setTitle("Exception Dialog");
            error.setHeaderText(null);
            error.setContentText("An error has occurred while loading your account's infromation");
            
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
        
        observableDataList = FXCollections.observableArrayList(dataList);
        
        expenseList.setItems(observableDataList);
        
        expenseList.setCellFactory(param -> new ExpenseCardListCell(observableDataList));
        
        usernameLabel.setText(user.getNickName());
        nameLabel.setText(user.getSurname() + ", " + user.getName());
        mailLabel.setText(user.getEmail());
        profilePicture.setImage(user.getImage());  
        
    }    
    
    public void initMain(Stage stage) {
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
        
    }

    @FXML
    private void editProfile(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ProfileSettings.fxml"));
            BorderPane root = loader.load();
            
            ProfileSettingsController profSetController = loader.<ProfileSettingsController>getController();
            profSetController.initProfileSettingsPage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Profile Settings");
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
    private void onLogOutPressed(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("alert");
        alert.setTitle("Log out");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            if(account.logOutUser()) {
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/LogIn.fxml"));
                    //Stage stage = new Stage();
                    BorderPane root = loader.load();

                    LogInController logInController = loader.<LogInController>getController();
                    logInController.initLogin(primaryStage);

                    Scene scene = new Scene(root);
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("Log in");
                } catch (Exception e) {
                    Alert error = new Alert(AlertType.ERROR);
                    error.setTitle("Exception Dialog");
                    error.setHeaderText(null);
                    error.setContentText("Unable to load the page");

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
            }
        } 
    }

    @FXML
    private void addExpense(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/UpdateExpense.fxml"));
            BorderPane root = loader.load();
            
            UpdateExpenseController updateExpenseController = loader.<UpdateExpenseController>getController();
            updateExpenseController.initExpense(null, 0.0, 1, null, null, null);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Add Expense");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            try{
                categoryList = account.getUserCategories();
                dataList = account.getUserCharges();
                observableDataList = FXCollections.observableArrayList(dataList);
                expenseList.setItems(observableDataList);
            } catch (Exception e) {
                Alert error = new Alert(AlertType.ERROR);
                DialogPane dialogPane = error.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
                error.getDialogPane().getStyleClass().add("alert");
                error.setTitle("Exception Dialog");
                error.setHeaderText(null);
                error.setContentText("An error has occurred while adding the charge");

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
            expenseList.refresh();
            expenseList.getSelectionModel().selectLast();
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
    private void onGenerateReportPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Report.fxml"));
            BorderPane root = loader.load();
            
            ReportController reportController = loader.<ReportController>getController();
            reportController.initReportPage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Generate Report");
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
    private void onCompareExpensePressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ExpenseAccount.fxml"));
            //Stage stage = new Stage();
            BorderPane root = loader.load();
            // TODO: Add controller and call the init method similar to
            ExpenseAccountController expenseAccountController = loader.<ExpenseAccountController>getController();
            expenseAccountController.initExpenseAccountPage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Expense Account");
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
    private void onManageCategoryPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ManageCategories.fxml"));
            BorderPane root = loader.load();
            
            ManageCategoriesController manageCategoriesController = loader.<ManageCategoriesController>getController();
            manageCategoriesController.initManageCategoriesPage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Manage categories");
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
    private void onEdit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/UpdateExpense.fxml"));
            BorderPane root = loader.load();
            Charge editingCharge = expenseList.getSelectionModel().getSelectedItem();
            UpdateExpenseController updateExpenseController = loader.<UpdateExpenseController>getController();
            updateExpenseController.initExpense(editingCharge.getName(), editingCharge.getCost(),
                                               editingCharge.getUnits(), editingCharge.getDescription(), 
                                               editingCharge.getCategory(), editingCharge.getDate());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Add Expense");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            try{
                categoryList = account.getUserCategories();
                dataList = account.getUserCharges();
                observableDataList = FXCollections.observableArrayList(dataList);
                expenseList.setItems(observableDataList);
            } catch (Exception e) {
                Alert error = new Alert(AlertType.ERROR);
                DialogPane dialogPane1 = error.getDialogPane();
                dialogPane1.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
                error.getDialogPane().getStyleClass().add("alert");
                error.setTitle("Exception Dialog");
                error.setHeaderText(null);
                error.setContentText("An error has occurred while trying to remove and expense");

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
            expenseList.refresh();
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
    private void onDelete(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("alert");
        alert.setTitle("Delete charge");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this charge?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            try {
                account.removeCharge(expenseList.getSelectionModel().getSelectedItem());
                observableDataList.remove(expenseList.getSelectionModel().getSelectedItem());
                expenseList.getSelectionModel().clearSelection();
            } catch (AcountDAOException ex) {
                Alert error = new Alert(AlertType.ERROR);
                DialogPane dialogPane1 = error.getDialogPane();
                dialogPane1.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
                error.getDialogPane().getStyleClass().add("alert");
                error.setTitle("Exception Dialog");
                error.setHeaderText(null);
                error.setContentText("An error has occurred while trying to remove and expense");

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
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
    }
}
