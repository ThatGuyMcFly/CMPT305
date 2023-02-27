package com.example.lab6_2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;



public class PropertyAssessmentApplication extends Application {

    private HBox mainHBox;
    private VBox tableVBox;
    private VBox selectionVBox;
    private ObservableList<PropertyAssessment> propertyAssessments;

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Property Assessments");
        mainHBox = new HBox(10);
        mainHBox.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(mainHBox, 1000, 1000);
        primaryStage.setScene(scene);

        primaryStage.setMaximized(true);

        configureTable();

        configureSourceSelect();

        mainHBox.getChildren().addAll(selectionVBox, tableVBox);
        primaryStage.show();
    }

    private void configureSourceSelect() {
        selectionVBox = new VBox(10);

        final Source CSV = Source.CSV;
        final Source API = Source.API;

        final ObservableList<String> dataSources = FXCollections.observableArrayList(
                CSV.getSource(),
                API.getSource()
        );

        ComboBox<String> sourceSelect = new ComboBox<>(dataSources);

        sourceSelect.prefWidthProperty().bind(selectionVBox.widthProperty());

        Button getDataButton = new Button("Read Data");

        getDataButton.setOnAction(event -> {
            propertyAssessments.clear();

            String source = sourceSelect.getValue();
            List<PropertyAssessment> propertyAssessmentList = new ArrayList<>();
            if(source.equals(CSV.getSource())) {
                CsvPropertyAssessmentDAO propertyAssessmentDAO = new CsvPropertyAssessmentDAO();
                propertyAssessmentList = propertyAssessmentDAO.getAssessments();
            } else if(source.equals(API.getSource())) {
                ApiPropertyAssessmentDAO propertyAssessmentDAO = new ApiPropertyAssessmentDAO();
                propertyAssessmentList = propertyAssessmentDAO.getAssessments();
            }

            if (propertyAssessmentList.size() > 0) {
                propertyAssessments.addAll(propertyAssessmentList);
            }
        });

        getDataButton.prefWidthProperty().bind(selectionVBox.widthProperty());

        Label selectionBoxLabel = new Label("Select Data Source");

        selectionVBox.getChildren().addAll(selectionBoxLabel, sourceSelect, getDataButton);
        selectionVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(0.1));
    }

    private void configureTable() {
        tableVBox = new VBox(10);
        propertyAssessments = FXCollections.observableArrayList();

        TableView<PropertyAssessment> table = new TableView<>();
        table.setItems(propertyAssessments);

        TableColumn<PropertyAssessment, String> accountCol = new TableColumn<>("Account");
        accountCol.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        accountCol.prefWidthProperty().bind(table.widthProperty().multiply(0.16));
        table.getColumns().add(accountCol);

        TableColumn<PropertyAssessment, Address> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        addressCol.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Address address, boolean empty) {
                super.updateItem(address, empty);

                if (!empty){
                    StringBuilder addressString = new StringBuilder();

                    if(!address.getSuite().isEmpty()) {
                        addressString.append(address.getSuite()).append(" ");
                    }

                    if(!address.getHouseNumber().isEmpty()) {
                        addressString.append(address.getHouseNumber()).append(" ");
                    }

                    addressString.append(address.getStreet());

                    setText(addressString.toString());
                } else {
                    setText("");
                }

            }
        });

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
                if(!empty) {
                    StringBuilder assessmentClassString = new StringBuilder("[");
                    for (int i = 0; i < assessmentClassList.size(); i++) {
                        if(i > 0) {
                            assessmentClassString.append(" ");
                        }

                        assessmentClassString.append(assessmentClassList.get(i).getAssessmentClassName()).append(" ");
                        assessmentClassString.append(assessmentClassList.get(i).getAssessmentClassPercentage());
                    }

                    assessmentClassString.append("]");

                    setText(assessmentClassString.toString());
                } else {
                    setText("");
                }
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

        Label tableLabel = new Label("Edmonton Property Assessments (2022)");

        table.prefWidthProperty().bind(tableVBox.widthProperty());
        table.prefHeightProperty().bind(tableVBox.heightProperty());

        tableVBox.getChildren().addAll(tableLabel, table);
        tableVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(0.9));
    }

    public static void main(String[] args) {
        launch();
    }
}