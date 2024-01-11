package com.example.ebsys;
import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {
    public  Connection databaseLink;
    public Connection getConnection(){
        String databasename="ebsys";
        String databaseUser="ebs";
        String databasePassword="ebspass";
        String url="jdbc:mysql://localhost:3306/"+databasename;
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink=DriverManager.getConnection(url,databaseUser,databasePassword);
        }catch (Exception e){
            e.printStackTrace();
        }
        return databaseLink;

    }
}


