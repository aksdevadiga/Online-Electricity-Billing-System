package com.example.ebsys;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import  javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class customerlistController implements Initializable{

@FXML
private TableView<customerSearchModel> customertableview ;
@FXML
private TableColumn<customerSearchModel, String> cname;
@FXML
private TableColumn<customerSearchModel, String> meter_no;
@FXML
private TableColumn<customerSearchModel, String> address;
@FXML
private TableColumn<customerSearchModel, String> city;
@FXML
private TableColumn<customerSearchModel, String> email;
@FXML
private TableColumn<customerSearchModel, Long> phno;
@FXML
        private TextField search;
//@FXML
//        private Button back;

ObservableList<customerSearchModel> customerSearchModelObservableList = FXCollections.observableArrayList();
@Override
public  void initialize(URL url, ResourceBundle resource){
    DatabaseConnection connectnow =new DatabaseConnection();
    Connection connectDB = connectnow.getConnection();
    String customerViewQuery="SELECT cname,meter_no,address,city,email,phno from customer";
    try{
        Statement statement=connectDB.createStatement();
        ResultSet queryOutput = statement.executeQuery(customerViewQuery);

        while(queryOutput.next()){
            String querycname=queryOutput.getString("cname");
            String querymeterno=queryOutput.getString("meter_no");
            String queryaddress=queryOutput.getString("address");
            String querycity=queryOutput.getString("city");
            String queryemail=queryOutput.getString("email");
         // Integer queryphno=queryOutput.getInt("phno");
            Long queryphno=queryOutput.getLong("phno");
            customerSearchModelObservableList.add(new customerSearchModel(querycname,querymeterno,queryaddress,querycity,queryemail,queryphno));



            
        }
        cname.setCellValueFactory(new PropertyValueFactory<>("cname"));
        meter_no.setCellValueFactory(new PropertyValueFactory<>("meter_no"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phno.setCellValueFactory(new PropertyValueFactory<>("phno"));

        customertableview.setItems(customerSearchModelObservableList);

        FilteredList<customerSearchModel> filteredData=new FilteredList<>(customerSearchModelObservableList,b->true);
        search.textProperty().addListener((observable,oldValue,newValue)->{
            filteredData.setPredicate(customerSearchModel -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;

                }
                String searchKeyword = newValue.toLowerCase();
                if (customerSearchModel.getCname().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;

                } else if (customerSearchModel.getAddress().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (customerSearchModel.getMeter_no().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (customerSearchModel.getEmail().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (customerSearchModel.getCity().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (customerSearchModel.getPhno().toString().indexOf(searchKeyword) > -1) {
                    return true;

                }
                else
                    return false;


            });
        });


        SortedList<customerSearchModel> sortedData =  new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(customertableview.comparatorProperty());
        customertableview.setItems(sortedData);

    }
    catch (SQLException e){
        Logger.getLogger(customerlistController.class.getName()).log(Level.SEVERE,null,e);
        e.printStackTrace();
    }
}
//    public void backToHome(ActionEvent event) {
//        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("homepage.fxml"));
//
//        try {
//            Parent root = (Parent) loader.load();
//            Stage stage = (Stage) this.back.getScene().getWindow();
//            stage.setScene(new Scene(root, 800.0, 500.0));
//        } catch (Exception var4) {
//            var4.printStackTrace();
//        }
//
//
//    }

}
