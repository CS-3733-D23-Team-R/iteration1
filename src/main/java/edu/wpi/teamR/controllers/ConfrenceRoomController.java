package edu.wpi.teamR.controllers;

import edu.wpi.teamR.mapdb.LocationName;
import edu.wpi.teamR.mapdb.MapDatabase;
import edu.wpi.teamR.requestdb.RequestDatabase;
import edu.wpi.teamR.requestdb.RoomRequest;
import io.github.palexdev.materialfx.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ConfrenceRoomController {
    @FXML MFXButton submitButton;
    @FXML MFXButton resetButton;
    @FXML MFXTextField nameField;
    @FXML DatePicker datePicker;
    @FXML TableView confrenceTable;
    @FXML TableColumn roomCol;
    @FXML TableColumn floorCol;
    @FXML TableColumn capacityCol;
    @FXML TableColumn featuresCol;
    @FXML TableColumn requestCol;

    @FXML MFXComboBox locationBox;
    @FXML MFXComboBox startTimeBox;
    @FXML MFXComboBox endTimeBox;


ArrayList<String> timeArray = new ArrayList<>();
    @FXML
    public void initialize() {
        fillArray();
        ObservableList<String> startTimeList = FXCollections.observableArrayList(timeArray);
        startTimeBox.setValue("Start Time");
        startTimeBox.setItems(startTimeList);
        endTimeBox.setValue("End Time");
        endTimeBox.setItems(startTimeList);

        submitButton.setOnMouseClicked(event -> submit());
        resetButton.setOnMouseClicked(event -> reset());
    }
    String start;
    String end;
    LocalDate date;
    String name;
    String location;

    ArrayList<LocationName> confList;

    void submit(){
        /*TODO
            save all info
            create a list of all of the rooms that are available in the window/match the search conditions
            add that list to the table/columns
            make it so there is a "Reserve" button in the last column that creates a new room request  w/ all relevant info when clicked
         */
        start = startTimeBox.getValue().toString();
        end = startTimeBox.getValue().toString();
        date =  datePicker.getValue();
        name = nameField.toString();
        location = locationBox.getValue().toString();
        try {
            MapDatabase mapDatabase = new MapDatabase();
            confList = mapDatabase.getLocationNamesByNodeType("CONF");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        for(LocationName confRoom: confList){
            if(isAvailable(confRoom)){
                confrenceTable.getItems().add(confRoom);
            }
        }


    }

    void reset(){
        startTimeBox.setValue("Start Time");
        endTimeBox.setValue("Start Time");
        locationBox.setValue("Select Location- Optional");
    }

    void fillArray(){
        for(int i = 1; i <= 12; i++){
            timeArray.add(i + ":00 AM");
            timeArray.add(i + ":15 AM");
            timeArray.add(i + ":30 AM");
            timeArray.add(i + ":45 AM");
        }
        for(int i = 1; i <= 12; i++){
            timeArray.add(i + ":00 PM");
            timeArray.add(i + ":15 PM");
            timeArray.add(i + ":30 PM");
            timeArray.add(i + ":45 PM");
        }

    }

    public Timestamp CurrentDateTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        return new Timestamp(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(),now.getMinute(),now.getSecond(),now.getNano());
    }

    public boolean isAvailable(LocationName conferenceRoom){
        /*TODO
            check time availability
            if there is a floor given, check if its on the floor
            if there is a room name given, check if it shares a name (.matches)
         */
        try {
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }





}
