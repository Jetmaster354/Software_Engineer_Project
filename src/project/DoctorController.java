package project;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import project.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class DoctorController {
    //Admin info
    @FXML private TextField AdminTxtFName, AdminTxtLName, AdminTxtSSN, AdminTxtRoom;
    @FXML private RadioButton AdminDocRB, AdminNurseRB;
    @FXML private ChoiceBox AdminNurseChoice;


    @FXML private TextArea patientName;
    @FXML private TextArea roomNumber;
    @FXML private TextArea patientInfo;
    @FXML private TextArea symptoms;
    @FXML private TextArea diagnosis;
    @FXML private TextArea medicine;

    @FXML private ChoiceBox docPrecriptionChoice;
    @FXML private ChoiceBox docChoice;

    @FXML private TableView<ObservableList<String>> recTable, docTable, nurseTable, patTable;
    @FXML private TableColumn<ObservableList<String>, String> column;

    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        try{
            fillDoctorChoice();
            fillPrescriptionChoice();
        }catch (SQLException | ClassNotFoundException e){
            throw e;
        }
    }

    //Admin Tab
    @FXML private void AddNurseNDoc(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
        try{
            if (AdminDocRB.isSelected()){
               docDAO.addDoc(AdminTxtFName.getText(), AdminTxtLName.getText(), Integer.parseInt(AdminTxtSSN.getText()));
            }
            else if(AdminNurseRB.isSelected()){
               nurseDAO.addNurse(AdminTxtFName.getText(), AdminTxtLName.getText(), AdminTxtSSN.getText());
            }
            else;
        }
        catch (SQLException e){
            System.out.println("Error occured while making the doctor or nurse object" + e);
        }
    }

    @FXML private void RemoveNurseNDoc(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
        try{
            if (AdminDocRB.isSelected()){
                docDAO.removeDoc(AdminTxtFName.getText(), AdminTxtLName.getText(), Integer.parseInt(AdminTxtSSN.getText()));
            }
            else if(AdminNurseRB.isSelected()){
                nurseDAO.removeNurse(AdminTxtFName.getText(), AdminTxtLName.getText(), AdminTxtSSN.getText());
            }
            else;
        }
        catch (SQLException e){
            System.out.println("Error occured while making the doctor or nurse object" + e);
        }
    }

    @FXML private void btnAddRoom(ActionEvent actionEvent) throws  SQLException, ClassNotFoundException{
        try{
            roomDAO.addRoom(Integer.parseInt(AdminTxtRoom.getText()), ((Person)AdminNurseChoice.getSelectionModel().getSelectedItem()).getSSN());
        }
        catch (SQLException e){
            System.out.println(("Error while making room" + e));
        }
    }

    @FXML private void btnRemoveRoom(ActionEvent actionEvent) throws  SQLException, ClassNotFoundException{
        try{
            roomDAO.removeRoom(Integer.parseInt(AdminTxtRoom.getText()), ((Person)AdminNurseChoice.getSelectionModel().getSelectedItem()).getSSN());
        }
        catch (SQLException e){
            System.out.println(("Error while making room" + e));
        }
    }



    //Receptionist Tab *********************************************************************************************
    @FXML private void updateTable(TableView<ObservableList<String>> table, ResultSet rs)throws SQLException {
        table.getColumns().clear();
        table.getItems().clear();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            final int j = i;
            column = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
            table.getColumns().add(column);
        }

        while (rs.next()) {
            //Iterate Row
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                //Iterate Column
                row.add(rs.getString(i));
            }
            data.add(row);

        }
        table.setItems(data);
    }

    @FXML
    private void allPatients(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
        try{
            ResultSet rs = patientDAO.searchPatients();
            updateTable(recTable, rs);
        }catch (SQLException e){
            System.out.println("Error occurred while getting employees information from DB.\n" + e);
            throw e;
        }
    }
    @FXML
    private void allDoctors(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
        try{
            ResultSet rs = docDAO.searchDoctors();
            updateTable(recTable, rs);
        }catch (SQLException e){
            System.out.println("Error occurred while getting employees information from DB.\n" + e);
            throw e;
        }
    }
    @FXML
    private void allNurses(ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
        try{
            ResultSet rs = nurseDAO.searchNurse();
            updateTable(recTable, rs);
        }catch (SQLException e){
            System.out.println("Error occurred while getting employees information from DB.\n" + e);
            throw e;
        }
    }

//    @FXML protected void tableClick(MouseEvent event) throws SQLException, ClassNotFoundException {
//        String text = table.getColumns().get(0).getText();
//        if(event.getClickCount() > 1 && !((text.equals("Num_Movies") || (text.equals("directorName")|| (text.equals("actorName")))))) {
//            ObservableList<String> row = table.getSelectionModel().getSelectedItem();
//            movieTab(row.get(0).toString());
//            tabPane.getSelectionModel().select(1);
//        }
//    }

    // DOCTOR TAB ****************************************************************************************
    // @FXML private void docTable (ActionEvent actionEvent) throws SQLException, ClassNotFoundException{
    //     try{
    //         ResultSet rs = docDAO.searchPatients();
    //         updateTable(docTable, rs);
    //     }catch (SQLException e){
    //         System.out.println("Error occurred while getting employees information from DB.\n" + e);
    //         throw e;
    //     }
    // }
    
    @FXML
    private void fillDoctorChoice() throws SQLException, ClassNotFoundException{
        ArrayList<String> names = new ArrayList<String>();

        try{
            ResultSet rs = docDAO.searchDoctors();
            while (rs.next()) { 
                names.add(rs.getString("lastName"));
            }
            ObservableList<String> obNames = FXCollections.observableArrayList(names);
            docChoice.setItems(obNames);
        }catch (SQLException e){
            System.out.println("Error occurred while getting employees information from DB.\n" + e);
            throw e;
        }
    }

    @FXML
    private void fillPrescriptionChoice() throws SQLException, ClassNotFoundException{
        try{
            ObservableList<String> prescriptionList = apptDAO.getPrescriptionList();
            docPrecriptionChoice.setItems(prescriptionList);
        }catch (SQLException e){
            System.out.println("Error occurred while getting employees information from DB.\n" + e);
            throw e;
        }
    }
}