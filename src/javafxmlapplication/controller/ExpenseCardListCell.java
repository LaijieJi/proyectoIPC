package javafxmlapplication.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import model.Charge;

public class ExpenseCardListCell extends ListCell<Charge> {

    private FXMLLoader loader;
    private ExpenseCardController controller;
    private HBox cell;
    
    private ObservableList<Charge> observableList;

    public ExpenseCardListCell(ObservableList<Charge> observableList) {
        this.observableList = observableList;
    }

    @Override
    protected void updateItem(Charge item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("../view/expenseCard.fxml"));
                try {
                    cell = loader.load();
                    controller = loader.getController();
                } catch (IOException e) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    DialogPane dialogPane = error.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("../styles/stylesheet.css").toExternalForm());
                    error.getDialogPane().getStyleClass().add("alert");
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
            cell.prefWidthProperty().bind( getListView().widthProperty().subtract( 20 ) );
            controller.setCharge(item);
            setText(null);
            setGraphic(cell);
        }
    }
}
