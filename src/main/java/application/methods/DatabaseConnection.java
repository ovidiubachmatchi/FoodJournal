package application.methods;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){

        final String server="localhost:3306/";
        final String db_name= "controllers";
        final String userName="root";
        final String password="";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/foodjournal",userName,password);
        }
        catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }
}
