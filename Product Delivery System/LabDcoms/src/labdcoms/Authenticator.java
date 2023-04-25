/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labdcoms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author tahmi
 */
public class Authenticator {

    public Authenticator(String loginUsername, String loginPassword)throws SQLException {
        boolean authenticated = false;
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/dcomstut","tahmid","tahmid");
        System.out.println("Authenticating...");
        Statement stmt = con.createStatement();
        String sqlCommand = "select * from demo";
        ResultSet loginData = stmt.executeQuery(sqlCommand);
        
        while(loginData.next()){
            String username = loginData.getString("username");
            String password = loginData.getString("password");
            System.out.println(username);
            System.out.println(password);
            if(loginPassword.equals(password) && loginUsername.equals(username)){
                authenticated = true;
                break;
            }
        }
        if(authenticated){
            System.out.println("Successfully logged in...");
        }
        else{
            System.out.println("Wrong username or password. Please try again");
        }
    }
    
}
