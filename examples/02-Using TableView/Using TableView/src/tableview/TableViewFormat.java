package tableview;

// Adapted from:
// https://docs.oracle.com/javafx/2/ui_controls/table-view.htm
// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html
// Demonstrates how to format the values in a table view

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

import java.text.NumberFormat;

public class TableViewFormat extends Application {

    private TableView<Person> table;
    private ObservableList<Person> taxPayers;

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField taxField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Table View Sample");
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(vBox, 600, 500);
        primaryStage.setScene(scene);

        final Label label = new Label("Tax Amount");
        label.setFont(new Font("Arial", 16));

        configureTable();

        firstNameField = new TextField();
        lastNameField = new TextField();
        taxField = new TextField();
        HBox hBox = new HBox(10);

        Button addBtn = new Button("Add");
        addBtn.setOnAction(event -> {
            Person person = new Person(firstNameField.getText(), lastNameField.getText(),
                    Integer.parseInt(taxField.getText()));
            taxPayers.add(person);

            firstNameField.clear();
            lastNameField.clear();
            taxField.clear();
        });

        hBox.getChildren().addAll(firstNameField, lastNameField, taxField, addBtn);
        vBox.getChildren().addAll(label, table, hBox);
        primaryStage.show();
    }

    private void configureTable() {
        table = new TableView<>();
        taxPayers = FXCollections.observableArrayList();
        table.setItems(taxPayers);

        TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
        table.getColumns().add(firstNameCol);

        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
        table.getColumns().add(lastNameCol);

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        TableColumn<Person, Integer> taxCol = new TableColumn<>("Tax Owed");
        taxCol.setCellValueFactory(new PropertyValueFactory<>("taxOwed"));

        taxCol.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Integer value, boolean empty) {
                super.updateItem(value, empty);
                currencyFormat.setMaximumFractionDigits(0);
                setText(empty ? "" : currencyFormat.format(value));
            }
        });

        taxCol.prefWidthProperty().bind(table.widthProperty().multiply(0.4));
        table.getColumns().add(taxCol);
    }
}