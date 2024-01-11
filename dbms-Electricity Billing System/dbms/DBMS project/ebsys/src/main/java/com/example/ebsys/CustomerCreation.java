package com.example.ebsys;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;

public class CustomerCreation {


    @FXML
    private TextField cname1,pno1, email1, state1, city1, add1, mno1;

    @FXML
    private Button next1, cancel2;

    @FXML
    private Label cusstomerlabel;

    public void next1ButtonAction(ActionEvent event){


        registeruser();

    }
    public void cancel2ButtonAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
            Parent root = loader.load();

            // Close the current window
            Stage stage = (Stage) cancel2.getScene().getWindow();
            stage.close();

            // Show the login page
            Stage homepageStage = new Stage();
            homepageStage.setTitle("homepage");
            homepageStage.setScene(new Scene(root));
            homepageStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void registeruser(){
        DatabaseConnection connectnow =new DatabaseConnection();
        Connection connectDB = connectnow.getConnection();

        String cname = cname1.getText();
        String meter = mno1.getText();
        String email = email1.getText();
        String phone = pno1.getText();
        String address = add1.getText();
        String city = city1.getText();

        String insertfield = "INSERT INTO customer(cname,meter_no,address,city,email,phno) VALUES ('";
        String insertvalue = cname + "','" + meter + "','" + address + "','" + city + "','" + email + "','" + phone + "')";
        String inserttocustomer = insertfield + insertvalue;


        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(inserttocustomer);
            cusstomerlabel.setText("User has been registered successfully!");
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
}