
import java.io.*;
import java.net.*;
import java.sql.*;

class S2 {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server is listening on port 1234...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Connection connection;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;

        try {
            String url = "jdbc:mysql://localhost:3306/ebsys";
            String user = "ebs";
            String password = "ebspass";
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);

            String clientMessage;
            while((clientMessage = in.readLine()) != null) {
                System.out.println("Received from client: " + clientMessage);
                if (clientMessage.startsWith("LOGIN")) {
                    String[] parts = clientMessage.split(" ");
                    if (parts.length == 3) {
                        String name = parts[1];
                        String password = parts[2];
                        this.authenticateUser(name, password, out);
                    } else {
                        out.println("INVALID_LOGIN_FORMAT");
                    }}
                else if (clientMessage.startsWith("REGISTER")) {
                    // Split the message to extract user registration data

                    String[] parts = clientMessage.split(" ");
                    if (parts.length == 3) {
                        String username = parts[1];
                        String password = parts[2];
                        this.registerUser( username, password, out);
                    } else {
                        out.println("INVALID_SIGNUP_FORMAT");
                    }

                } else {
                    out.println("UNKNOWN_REQUEST");
                }
            }

            this.clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }}



    private void authenticateUser(String username, String password, PrintWriter out) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("SELECT username FROM userdetails WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                out.println("LOGIN_SUCCESS");
            } else {
                out.println("LOGIN_FAILURE");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void registerUser(String username, String password, PrintWriter out) {
        try {
            System.out.println("Received registration request for username: " + username);

            // Check if the email already exists in the database
            PreparedStatement checkStatement = connection.prepareStatement("SELECT username FROM userdetails WHERE username = ?");
            checkStatement.setString(1, username);

            ResultSet existingUsers = checkStatement.executeQuery();

            if (existingUsers.next()) {
                // User with the same email already exists
                System.out.println("Signup failed: Username is already in use.");
                out.println("SIGNUP_FAILURE");
            } else {
                // No existing user with the same email, proceed with the insertion
                PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO userdetails (username, password) VALUES (?, ?)");
                insertStatement.setString(1, username);
                insertStatement.setString(2, password);

                int rowsAffected = insertStatement.executeUpdate();

                if (rowsAffected > 0) {
                   // System.out.println("Signup successful for username: " + username);
                    out.println("SIGNUP_SUCCESS");
                } else {
                   // System.out.println("Signup failed: Rows affected = 0");
                    out.println("SIGNUP_FAILURE");
                }

                insertStatement.close();
            }

            existingUsers.close();
            checkStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }}}
