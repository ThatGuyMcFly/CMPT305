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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.List;
import java.util.Set;

public class PropertyAssessmentApplication extends Application {

    private ObservableList<PropertyAssessment> propertyAssessments;
    PropertyAssessmentDAO propertyAssessmentDAO;

    private TextField accountNumberTextField;
    private TextField addressTextField;
    private TextField neighbourhoodTextField;
    private TextField minValueTextField;
    private TextField maxValueTextField;
    ComboBox<String> sourceSelect;
    ObservableList<String> assessmentClasses;

    ComboBox<String> assessmentClassSelect;
    Set<String> assessmentClassSet;

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Property Assessments");
        HBox mainHBox = new HBox(10);
        mainHBox.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(mainHBox, 1000, 1000);
        primaryStage.setScene(scene);

        primaryStage.setMaximized(true);

        VBox tableVBox =  configureTable();
        tableVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(0.88));

        VBox selectionVBox = configureDataSelection();
        selectionVBox.prefWidthProperty().bind(mainHBox.widthProperty().multiply(0.12));

        mainHBox.getChildren().addAll(selectionVBox, tableVBox);
        primaryStage.show();
    }

    private void loadData() {
        final Source CSV = Source.CSV;
        final Source API = Source.API;

        List<PropertyAssessment> propertyAssessmentList;

        propertyAssessments.clear();
        assessmentClasses.clear();
        String source = sourceSelect.getValue();

        if(source.equals(CSV.getSource())) {
            propertyAssessmentDAO = new CsvPropertyAssessmentDAO();
        } else if(source.equals(API.getSource())) {
            propertyAssessmentDAO = new ApiPropertyAssessmentDAO();
        }

        propertyAssessmentList = propertyAssessmentDAO.getAssessments();
        assessmentClassSet = propertyAssessmentDAO.getAssessmentClasses();
        assessmentClasses.addAll(assessmentClassSet);

        if (propertyAssessmentList.size() > 0) {
            propertyAssessments.addAll(propertyAssessmentList);
        }
    }

    private void search() {
        List<PropertyAssessment> propertyAssessmentList;

        if (propertyAssessmentDAO != null) {
            propertyAssessments.clear();
            propertyAssessmentList = propertyAssessmentDAO.getByAssessmentClass(assessmentClassSelect.getValue());

            if (propertyAssessmentList.size() > 0) {
                propertyAssessments.addAll(propertyAssessmentList);
            }
        }
    }

    private void reset(){

    }

    private VBox createDataSelectVBox() {
        VBox dataSourceSelectVBox = new VBox(10);

        Source CSV = Source.CSV;
        Source API = Source.API;

        ObservableList<String> dataSources = FXCollections.observableArrayList(
                CSV.getSource(),
                API.getSource()
        );

        sourceSelect = new ComboBox<>(dataSources);

        sourceSelect.prefWidthProperty().bind(dataSourceSelectVBox.widthProperty());

        Button getDataButton = new Button("Read Data");

        getDataButton.setOnAction( event -> loadData());

        getDataButton.prefWidthProperty().bind(dataSourceSelectVBox.widthProperty());

        Label selectionBoxLabel = new Label("Select Data Source");
        selectionBoxLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        dataSourceSelectVBox.getChildren().addAll(selectionBoxLabel, sourceSelect, getDataButton);

        return dataSourceSelectVBox;
    }

    private VBox createAssessmentValueRangeVBox() {
        Label assessedValueRangeLabel = new Label("Assessed Value Range:");

        minValueTextField = new TextField();
        minValueTextField.setPromptText("Min Value");

        maxValueTextField = new TextField();
        maxValueTextField.setPromptText("Max Value");

        HBox minMaxHBox = new HBox(10);

        minMaxHBox.getChildren().addAll(minValueTextField, maxValueTextField);

        VBox assessedValueRangeVBox = new VBox(10);

        assessedValueRangeVBox.getChildren().addAll(assessedValueRangeLabel, minMaxHBox);

        return assessedValueRangeVBox;
    }

    /**
     * Creates a VBox with the specified label and Control and matches
     * the control objects width to the new VBox
     *
     * @param label A string to give the label
     * @param control the control object in the VBoc
     * @return the new VBox with the label and control object
     */
    private VBox createControlVBox(String label, Control control) {
        Label vBoxLabel = new Label(label);
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(vBoxLabel, control);

        control.prefWidthProperty().bind(vBox.widthProperty());

        return vBox;
    }

    private VBox createAssessmentClassSelectVBox() {
        assessmentClasses = FXCollections.observableArrayList();
        assessmentClassSelect= new ComboBox<>(assessmentClasses);
        return createControlVBox("Assessment Class:", assessmentClassSelect);
    }

    private VBox createTextFieldVbox(String label, TextField textField) {
        textField = new TextField();
        return createControlVBox(label, textField);
    }

    private HBox createButtonHBox () {
        HBox buttonHBox = new HBox(10);

        Button searchButton = new Button("Search");

        searchButton.setOnAction(event -> search());

        Button resetButton = new Button("Reset");

        resetButton.setOnAction(event -> reset());

        searchButton.prefWidthProperty().bind(buttonHBox.widthProperty().multiply(0.5));
        resetButton.prefWidthProperty().bind(buttonHBox.widthProperty().multiply(0.5));

        buttonHBox.getChildren().addAll(searchButton, resetButton);

        return buttonHBox;
    }

    private VBox createPropertyFindVBox() {
        VBox propertyFindVBox = new VBox(10);

        VBox accountNumberVBox = createTextFieldVbox("Account Number:", accountNumberTextField);

        VBox addressVBox = createTextFieldVbox("Address (#suite #house street):", addressTextField);

        VBox neighbourhoodVBox = createTextFieldVbox("Neighbourhood:", neighbourhoodTextField );

        VBox assessmentClassVBox = createAssessmentClassSelectVBox();

        VBox assessedValueRangeVBox = createAssessmentValueRangeVBox();

        Label findPropertyLabel = new Label("Find Property Assessment");
        findPropertyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        propertyFindVBox.getChildren().addAll(findPropertyLabel, accountNumberVBox, addressVBox, neighbourhoodVBox, assessmentClassVBox, assessedValueRangeVBox);

        return propertyFindVBox;
    }

    private VBox configureDataSelection() {
        VBox selectionVBox = new VBox(10);

        VBox dataSourceSelectVBox = createDataSelectVBox();

        VBox propertyFindVBox = createPropertyFindVBox();

        HBox buttonHBox = createButtonHBox();

        buttonHBox.prefWidthProperty().bind(selectionVBox.widthProperty());

        selectionVBox.getChildren().addAll(dataSourceSelectVBox, propertyFindVBox, buttonHBox);

        return selectionVBox;
    }

    private VBox configureTable() {
        VBox tableVBox = new VBox(10);
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
        tableLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        table.prefWidthProperty().bind(tableVBox.widthProperty());
        table.prefHeightProperty().bind(tableVBox.heightProperty());

        tableVBox.getChildren().addAll(tableLabel, table);

        return tableVBox;
    }

    public static void main(String[] args) {
        launch();
    }
}