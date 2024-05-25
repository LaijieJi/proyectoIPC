/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Charge;

/**
 * FXML Controller class
 *
 * @author laijie
 */
public class YearComparisonController implements Initializable {

    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;

    private Acount account;

    @FXML
    private Button goBackButton;
    @FXML
    private VBox wrapper;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Charge> listOfCharges = new ArrayList<>();
        try {
            account = Acount.getInstance();
            listOfCharges = account.getUserCharges();
        } catch (AcountDAOException e) {
            System.err.println(e);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        BarChart chart = new BarChart<String, Number>(xAxis, yAxis);
        chart.setTitle("Month Comparison");
        chart.setBarGap(1.0);
        chart.setAnimated(false);
        chart.setLegendSide(Side.RIGHT);

        double[] months = new double[13];

        LocalDate now = LocalDate.now();
        int thisYear = now.getYear();
        for (Charge charge : listOfCharges) {
            LocalDate aux = charge.getDate();
            if (aux.getYear() == thisYear) {
                months[aux.getMonthValue()] += charge.getCost() * charge.getUnits();
            }
        }

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("January");
        series1.getData().add(new XYChart.Data("January", months[1]));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("February");
        series2.getData().add(new XYChart.Data("February", months[2]));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("March");
        series3.getData().add(new XYChart.Data("March", months[3]));

        XYChart.Series series4 = new XYChart.Series();
        series4.setName("April");
        series4.getData().add(new XYChart.Data("April", months[4]));

        XYChart.Series series5 = new XYChart.Series();
        series5.setName("May");
        series5.getData().add(new XYChart.Data("May", months[5]));

        XYChart.Series series6 = new XYChart.Series();
        series6.setName("June");
        series6.getData().add(new XYChart.Data("June", months[6]));

        XYChart.Series series7 = new XYChart.Series();
        series7.setName("July");
        series7.getData().add(new XYChart.Data("July", months[7]));

        XYChart.Series series8 = new XYChart.Series();
        series8.setName("August");
        series8.getData().add(new XYChart.Data("August", months[8]));

        XYChart.Series series9 = new XYChart.Series();
        series9.setName("September");
        series9.getData().add(new XYChart.Data("September", months[9]));

        XYChart.Series series10 = new XYChart.Series();
        series10.setName("October");
        series10.getData().add(new XYChart.Data("October", months[10]));

        XYChart.Series series11 = new XYChart.Series();
        series11.setName("November");
        series11.getData().add(new XYChart.Data("November", months[11]));

        XYChart.Series series12 = new XYChart.Series();
        series12.setName("December");
        series12.getData().add(new XYChart.Data("December", months[12]));

        chart.getData().addAll(series1, series2, series3, series4, series5, series6, series7, series8, series9, series10, series11, series12);

        wrapper.getChildren().add(chart);

    }

    @FXML
    private void onGoBackButtonPressed(ActionEvent event) {
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle);
        primaryStage.show();
    }

    public void initYearComparisonPage(Stage stage) {
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
        primaryStage.setResizable(false);
    }

}
