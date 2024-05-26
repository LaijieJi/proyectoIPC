/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
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
public class SameMonthLastYearController implements Initializable {
    
    private Stage primaryStage;
    private Scene primaryScene;
    private String primaryTitle;
    
    private Acount account;
    
    private double thisMonth = 0;
    private double thisMonthLastYear = 0;

    @FXML
    private Button goBackButton;
    
    private BarChart<String, Number> chart;
    
    @FXML
    private VBox wrapper;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Charge> listOfCharges = new ArrayList<>();
        try{
            account = Acount.getInstance();
            listOfCharges = account.getUserCharges();
        } catch (AcountDAOException e) {
            System.err.println(e);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        chart = new BarChart<String,Number>(xAxis,yAxis);
        chart.setTitle("Month Comparison");
        chart.setBarGap(1.0);
        chart.setAnimated(false);
        chart.setLegendSide(Side.RIGHT);
        
        LocalDate now = LocalDate.now();
        YearMonth currentYearMonth = YearMonth.of(now.getYear(), now.getMonthValue());
        
        // Calculate this month's total charges
        LocalDate startOfMonth = currentYearMonth.atDay(1);
        LocalDate endOfMonth = currentYearMonth.atEndOfMonth();
        for (Charge charge : listOfCharges) {
            if (!charge.getDate().isBefore(startOfMonth) && !charge.getDate().isAfter(endOfMonth)) {
                thisMonth += charge.getCost() * charge.getUnits();
            }
        }
        
        // Calculate last year's total charges for the same month
        YearMonth lastYearMonth = YearMonth.of(now.getYear() - 1, now.getMonthValue());
        LocalDate startOfLastYearMonth = lastYearMonth.atDay(1);
        LocalDate endOfLastYearMonth = lastYearMonth.atEndOfMonth();
        for (Charge charge : listOfCharges) {
            if (!charge.getDate().isBefore(startOfLastYearMonth) && !charge.getDate().isAfter(endOfLastYearMonth)) {
                thisMonthLastYear += charge.getCost() * charge.getUnits();
            }
        }
        
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("This year");
        series1.getData().add(new XYChart.Data("This year", thisMonth));
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Last year");
        series2.getData().add(new XYChart.Data("Last year", thisMonthLastYear));
        
        chart.getData().addAll(series1, series2);
        
        wrapper.getChildren().add(chart);
    }    

    @FXML
    private void onGoBackButtonPressed(ActionEvent event) {
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(primaryTitle);
        primaryStage.show();
    }
    
    public void initSameMonthPage(Stage stage){
        primaryStage = stage;
        primaryScene = primaryStage.getScene();
        primaryTitle = primaryStage.getTitle();
        primaryStage.setResizable(false);
    }
}
