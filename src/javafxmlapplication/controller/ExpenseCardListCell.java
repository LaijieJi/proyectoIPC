package javafxmlapplication.controller;

import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
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
                    e.printStackTrace();
                }
            }
            cell.prefWidthProperty().bind( getListView().widthProperty().subtract( 20 ) );
            controller.setCharge(item);
            setText(null);
            setGraphic(cell);
        }
    }
}
