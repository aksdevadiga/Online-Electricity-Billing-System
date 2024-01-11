package com.example.ebsys;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.*;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;

public class logincontroller implements Initializable {

    //@FXML
    //private Button createAccountButton;

    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordd;

    @FXML
    private Hyperlink createAccountLink;

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1234;

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    public void cancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void loginButtonOnAction(ActionEvent event) {
        String enteredusername = usernameTextField.getText();
        String enteredPassword = passwordd.getText();

        if (isInputValid(enteredusername, enteredPassword)) {
            sendLoginRequestToServer(enteredusername, enteredPassword);
        }
    }


    public void createAccountButtonAction(ActionEvent event) {
        try {
            // Load the sign-up page using FXMLLoader
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
            Parent signUpPage = loader.load();

            // Create a new stage for the sign-up page
            Stage signUpStage = new Stage();
            signUpStage.setScene(new Scene(signUpPage));

            // Show the sign-up stage
            signUpStage.show();

            // Close the login page (optional)
            Stage loginStage = (Stage) createAccountLink.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean isInputValid(String usernameTextField, String passwordd) {
        if (usernameTextField.isBlank() || passwordd.isBlank()) {
            loginMessageLabel.setText("Please Enter Email and Password");
            return false;
        }
        return true;
    }

    public void sendLoginRequestToServer(String usernameTextField, String passwordd) {
        try (Socket clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            // Send login request to the server
            out.println("LOGIN " + usernameTextField + " " + passwordd);

            // Receive the server response
            String response = in.readLine();
            if (response.equals("LOGIN_SUCCESS")) {
                Platform.runLater(() -> {
                    loginMessageLabel.setText("Login successful");
                    loadHomePage();  // Call this method to load the homepage
                });
            } else if (response.equals("LOGIN_FAILURE")) {
                Platform.runLater(() -> loginMessageLabel.setText("Invalid Login. Please Try again."));
            } else {
                Platform.runLater(() -> loginMessageLabel.setText("Unknown response from the server."));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
            Parent homePage = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(homePage));

            //stage.show();

            // Close the login page (optional)
            Stage loginStage = (Stage) cancelButton.getScene().getWindow();
            loginStage.close();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading homepage.fxml: " + e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error during FXML loading: " + e.getMessage());
        }
    }

}

