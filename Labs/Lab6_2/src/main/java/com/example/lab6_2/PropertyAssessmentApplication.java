package com.example.lab6_2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

public class PropertyAssessmentApplication extends Application {

    private TableView<PropertyAssessment> table;

    ObservableList<PropertyAssessment> propertyAssessments;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Property Assessments");
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(vBox, 600, 500);
        primaryStage.setScene(scene);

        primaryStage.setMaximized(true);

        configureTable();

        HBox hBox = new HBox(10);

        Button csvButton = new Button("CSV");
        csvButton.setOnAction(event -> {
            propertyAssessments.clear();

            CsvPropertyAssessmentDAO csvPropertyAssessmentDAO = new CsvPropertyAssessmentDAO();
            List<PropertyAssessment> propertyAssessmentList = csvPropertyAssessmentDAO.getAssessments();

            propertyAssessments.addAll(propertyAssessmentList);
//            for(PropertyAssessment propertyAssessment : propertyAssessmentList) {
//                propertyAssessments.add(propertyAssessment);
//            }
        });

        Button apiBUtton = new Button("API");
        apiBUtton.setOnAction(event -> {
            propertyAssessments.clear();

            ApiPropertyAssessmentDAO apiPropertyAssessmentDAO = new ApiPropertyAssessmentDAO();
            List<PropertyAssessment> propertyAssessmentList = apiPropertyAssessmentDAO.getAssessments();

            propertyAssessments.addAll(propertyAssessmentList);
        });

        hBox.getChildren().addAll(csvButton, apiBUtton);

        vBox.getChildren().addAll(table, hBox);
        primaryStage.show();
    }

    private void configureTable() {
        table = new TableView<>();
        propertyAssessments = FXCollections.observableArrayList();
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
//                    String addressString = address.getSuite() + " " + address.getHouseNumber() + " " + address.getStreet();
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
    }

    public static void main(String[] args) {
        launch();
    }
}