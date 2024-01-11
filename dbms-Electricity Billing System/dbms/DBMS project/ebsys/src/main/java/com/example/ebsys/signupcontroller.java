package com.example.ebsys;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

import java.util.ResourceBundle;

public class signupcontroller {

    @FXML
    private Label user;

    @FXML
    private TextField username;

    @FXML
    private Label pass;

    @FXML
    private TextField password;

    @FXML
    private Button create;

    @FXML
    private Button back;

    @FXML
    private Label registrationMessageLabel;

        public signupcontroller() {
        }

        public void initialize(URL url, ResourceBundle resourceBundle) {
        }




        public void createButtonOnAction(ActionEvent event) {


          this.registerUser();

        }

        public void registerUser() {
            try {
                Socket clientSocket = new Socket("localhost", 1234);

                try {
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        try {
                            String susername = username.getText();
                               String spassword = password.getText();
                            String registrationRequest = "REGISTER " + susername + " "  + spassword;

                                    out.println(registrationRequest);
                                    String response = in.readLine();
                                    if (response.equals("SIGNUP_SUCCESS")) {
                                        this.registrationMessageLabel.setText("User has been registered successfully!");
                                    } else if (response.equals("SIGNUP_FAILURE")) {
                                        this.registrationMessageLabel.setText("Registration failed. Email is already in use.");
                                    } else {
                                        this.registrationMessageLabel.setText("Unknown response from the server.");
                                    }



                        } catch (Throwable var13) {
                            try {
                                in.close();
                            } catch (Throwable var12) {
                                var13.addSuppressed(var12);
                            }

                            throw var13;
                        }

                        in.close();
                    } catch (Throwable var14) {
                        try {
                            out.close();
                        } catch (Throwable var11) {
                            var14.addSuppressed(var11);
                        }

                        throw var14;
                    }

                    out.close();
                } catch (Throwable var15) {
                    try {
                        clientSocket.close();
                    } catch (Throwable var10) {
                        var15.addSuppressed(var10);
                    }

                    throw var15;
                }

                clientSocket.close();
            } catch (IOException var16) {
                var16.printStackTrace();
            }

        }

        public void backToLogin() {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("login.fxml"));

            try {
                Parent root = (Parent)loader.load();
                Stage stage = (Stage)this.back.getScene().getWindow();
                stage.setScene(new Scene(root, 520.0, 464.0));
            } catch (Exception var4) {
                var4.printStackTrace();
            }

        }





    // Define your server details
//    private static final String SERVER_ADDRESS = "localhost";
//    private static final int SERVER_PORT = 1234;
//
//    // Get the database connection
//    //private DatabaseConnection databaseConnection = new DatabaseConnection();
//    //private Connection connection = databaseConnection.getConnection();
//
//    @FXML
//    private void createButtonAction(ActionEvent event) {
//        String susername = username.getText();
//        String spassword = password.getText();
//
//        System.out.println("Username: " + susername);
//        System.out.println("Password: " + spassword);
//
//        try {
//            // Insert user details into the database
//            String insertQuery = "INSERT INTO userdetails (username, password) VALUES (?, ?)";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
//                preparedStatement.setString(1, susername);
//                preparedStatement.setString(2, spassword);
//                preparedStatement.executeUpdate();
//            }
//
//            // Placeholder code for server interaction
//            try (Socket clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
//
//                // Send signup request to the server
//                out.println("SIGNUP_SUCCESS " + susername + " " + spassword);
//
//               // out.println(" " + susername + " " + spassword);
//
//                // Receive the server response
//                String response = in.readLine();
//                if (response.equals("SIGNUP_SUCCESS")) {
//                    System.out.println("Signup successful");
//                    // You can take appropriate action here (e.g., open the login page).
//                } else if (response.equals("SIGNUP_FAILURE")) {
//                    System.out.println("Signup failed. Please try again.");
//                } else {
//                    System.out.println("Unknown response from the server.");
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @FXML
//    private void backButtonAction(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
//            Parent root = loader.load();
//
//            // Close the current window
//            Stage stage = (Stage) back.getScene().getWindow();
//            stage.close();
//
//            // Show the login page
//            Stage loginStage = new Stage();
//            loginStage.setTitle("Login");
//            loginStage.setScene(new Scene(root));
//            loginStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
