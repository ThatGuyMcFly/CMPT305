package com.example.lab6_2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

public class PropertyAssessmentApplication extends Application {

    private TableView<PropertyAssessment> table;
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Property Assessments");
        VBox vBox = new VBox(10);
        //vBox.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(vBox, 600, 500);
        stage.setScene(scene);

        stage.setMaximized(true);

        configureTable();

        vBox.getChildren().addAll(table);
        stage.show();
    }

    private void configureTable() {
        table = new TableView<>();
        ObservableList<PropertyAssessment> propertyAssessments = FXCollections.observableArrayList();
        table.setItems(propertyAssessments);

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

        TableColumn<PropertyAssessment, List<AssessmentClass>> assessmentClassCol = new TableColumn<>("Assessment Class");
        assessmentClassCol.setCellValueFactory(new PropertyValueFactory<>("assessmentClassList"));
        assessmentClassCol.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(List<AssessmentClass> assessmentClassList, boolean empty) {
                super.updateItem(assessmentClassList, empty);
                StringBuilder assessmentClassString = new StringBuilder("[");
                for (int i = 0; i < assessmentClassList.size(); i++) {
                    if(i > 0) {
                        assessmentClassString.append(" ");
                    }

                    assessmentClassString.append(assessmentClassList.get(i).getAssessmentClassName());
                    assessmentClassString.append(assessmentClassList.get(i).getAssessmentClassPercentage());
                }

                assessmentClassString.append("]");

                setText(empty ? "" : assessmentClassString.toString());
            }
        });
        assessmentClassCol.prefWidthProperty().bind(table.widthProperty().multiply(0.16));
        table.getColumns().add(assessmentClassCol);

        TableColumn<PropertyAssessment, String> neighbourhoodCol = new TableColumn<>("Neighbourhood");
        neighbourhoodCol.setCellValueFactory(new PropertyValueFactory<>("neighbourhood"));
        table.getColumns().add(neighbourhoodCol);
        neighbourhoodCol.prefWidthProperty().bind(table.widthProperty().multiply(0.16));

        TableColumn<PropertyAssessment, Location> locationCol = new TableColumn<>("(Latitude, Longitude)");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        locationCol.setCellFactory(tc -> new TableCell<>() {
            protected void updateItem(Location location, boolean empty) {
                super.updateItem(location, empty);

                setText(empty ? "": "(" + location.getLatitude() + ", " + location.getLongitude() + ")");
            }
        });
        locationCol.prefWidthProperty().bind(table.widthProperty().multiply(0.16));
        table.getColumns().add(locationCol);
    }

    public static void main(String[] args) {
        launch();
    }
}