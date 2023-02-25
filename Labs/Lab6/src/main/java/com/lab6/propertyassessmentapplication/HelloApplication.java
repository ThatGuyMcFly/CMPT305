package com.lab6.propertyassessmentapplication;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.text.NumberFormat;

import java.io.IOException;

public class HelloApplication extends Application {
    private TableView<PropertyAssessment> table;
    private ObservableList<PropertyAssessment> taxPayers;

    private TextField accountNumber;
    private TextField address;
    private TextField assessedValue;
    private TextField assessmentClass;
    private TextField neighbourhood;
    private TextField latitudeLongitude;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Table View Sample");
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(vBox, 600, 500);
        stage.setScene(scene);

        final Label label = new Label("Tax Amount");
        label.setFont(new Font("Arial", 16));

        // configureTable();

        accountNumber = new TextField();
        address = new TextField();
        assessedValue = new TextField();
        assessmentClass = new TextField();
        neighbourhood = new TextField();
        latitudeLongitude = new TextField();

        HBox hBox = new HBox(10);


        vBox.getChildren().addAll(label, table, hBox);
        stage.show();
    }

    private void configureTable() {
        table = new TableView<>();
        taxPayers = FXCollections.observableArrayList();
        table.setItems(taxPayers);

        TableColumn<PropertyAssessment, String> accountCol = new TableColumn<>("Account");
        accountCol.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        accountCol.prefWidthProperty().bind(table.widthProperty().multiply(0.16));
        table.getColumns().add(accountCol);

        TableColumn<PropertyAssessment, Address> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressCol.prefWidthProperty().bind(table.widthProperty().multiply(0.16));
        table.getColumns().add(addressCol);

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        TableColumn<PropertyAssessment, Integer> assessedValueCol = new TableColumn<>("Assessed Value");
        assessedValueCol.setCellValueFactory(new PropertyValueFactory<>("assessedValue"));

        assessedValueCol.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Integer value, boolean empty) {
                super.updateItem(value, empty);
                currencyFormat.setMaximumFractionDigits(0);
                setText(empty ? "" : currencyFormat.format(value));
            }
        });

        assessedValueCol.prefWidthProperty().bind(table.widthProperty().multiply(0.16));
        table.getColumns().add(assessedValueCol);

        TableColumn<PropertyAssessment, String[]> assessmentClassCol = new TableColumn<>("Assessment Class");


    }

    public static void main(String[] args) {
        launch();
    }
}