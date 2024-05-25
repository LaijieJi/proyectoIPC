/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import model.Category;

/**
 *
 * @author ICATFOR
 */
public class CategoryCardListCell extends ListCell<Category>{
    
    private FXMLLoader loader;
    private CategoryCardController controller;
    private HBox cell;

    @Override
    protected void updateItem(Category item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("../view/categoryCard.fxml"));
                try {
                    cell = loader.load();
                    controller = loader.getController();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            controller.setCategory(item);
            setText(null);
            setGraphic(cell);
        }
    }
}
