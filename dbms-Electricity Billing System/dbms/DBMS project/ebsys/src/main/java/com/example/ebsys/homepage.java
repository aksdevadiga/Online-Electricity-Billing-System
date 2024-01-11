package com.example.ebsys;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class homepage implements Initializable {
    @FXML
    private ImageView menu;
    @FXML
    private AnchorPane pane1,pane2;
    @FXML
    private Button addCustomerButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        exit.setOnMouseClicked(event ->{
//            System.exit(0);
//        });

        pane1.setVisible(false);

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        
        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.5),pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        menu.setOnMouseClicked(event-> {
            pane1.setVisible(true);

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),pane2);
            translateTransition1.setByX(+600);
            translateTransition1.play();
        });

        pane1.setOnMouseClicked(event ->{
            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 ->{
                pane1.setVisible(false);
            });


        });
        TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),pane2);
        translateTransition.setByX(-600);
        translateTransition.play();



        addCustomerButton.setOnAction(event -> handleAddCustomerButtonClick());

    }

    @FXML
    private void handleAddCustomerButtonClick() {
        // Load and show the customer creation page
        loadAndShowCustomerCreationPage();
    }

    private void loadAndShowCustomerCreationPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customercreation.fxml"));
            Parent customerCreationPage = loader.load();

            // Assuming you have a method to show the new page
            showPage(customerCreationPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleCustomerDetailsButtonClick() {
        // Load and show the customer list page
        loadAndShowCustomerListPage();
    }

    private void loadAndShowCustomerListPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customerlist.fxml"));
            Parent customerListPage = loader.load();
            showPage(customerListPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPage(Parent page) {
        // Add logic to display the new page
        // You might want to use a Stage or replace the contents of a container
        // For example:
        Stage stage = new Stage();
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.show();
    }

}
