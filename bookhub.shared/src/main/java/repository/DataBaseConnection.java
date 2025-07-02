package repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
    private static DataBaseConnection instanceConnection;
    private Connection connection;
    private DataBaseConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bookhubdb",
                    "root",
                    "Aa@123123"
            );
        }catch (Exception e){
            System.err.println("Connection Failed: " + e.getMessage());
        }
    }

    public static DataBaseConnection getInstance(){
        if(instanceConnection == null){
            instanceConnection = new DataBaseConnection();
        }
        return instanceConnection;
    }

    public Connection getConnection(){
        return connection;
    }
}

